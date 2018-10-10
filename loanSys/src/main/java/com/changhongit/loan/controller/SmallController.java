/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhongit.loan.bean.ApprovalComment;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.constant.ProcessTemplate;
import com.changhongit.loan.entity.LoanBursementEntity;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.enums.ApprovalStatus;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.service.WorkflowService;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.loan.util.MoneyToCNFormatUtil;

/**
 * <p>
 * 描述：功能综合Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月26日下午2:16:26
 */
@Controller
public class SmallController {
	public static final Logger logger = LoggerFactory
			.getLogger(SmallController.class);

	@Autowired
	private LoanMainService loanMainService;
	@Autowired
	private WorkflowService workflowService;

	/**
	 * 
	 * <p>
	 * 描述：同意 按钮事件
	 * </p>
	 * 
	 * @Date 2018年6月13日下午3:59:15 <br>
	 * @param mainId
	 *            主记录ID
	 * @param comment
	 *            审批意见
	 * @param flag
	 *            操作标识（1：同意；2：转复核）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agree", method = RequestMethod.POST)
	@ResponseBody
	public Message agree(Long mainId, String comment, Integer flag,
			HttpServletRequest request) {
		// TODO 同意
		logger.info("同意按钮 事件......");
		logger.info("mainId:{},comment={},操作标识(flag 1：同意；2：转复核)={}",
				new Object[] { mainId, comment, flag });
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		try {
			LoanMainEntity entity = workflowService.completeTask(mainId,
					sessionUser, comment, flag);
			return Message.success(entity);
		} catch (Exception e) {
			logger.error("异常", e);
			return Message.failure("异常：" + e.getMessage());
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：打印 按钮事件
	 * </p>
	 * 
	 * @Date 2018年6月26日下午2:19:12 <br>
	 * @param mainId
	 * @return
	 */
	@RequestMapping(value = "print", method = RequestMethod.GET)
	public String print(@RequestParam("mainId") Long mainId, ModelMap modelMap,
			HttpServletRequest request) {
		// TODO 打印
		logger.info("进入打印...");
		LoanMainEntity entity = loanMainService.getEntityById(
				LoanMainEntity.class, mainId);
		modelMap.put("entity", entity);

		// 大写金额
		Double money = entity.getTotalMoney();
		BigDecimal numberOfMoney = new BigDecimal(money.toString());
		modelMap.put("totalMoneyBig",
				MoneyToCNFormatUtil.number2CNMontrayUnit(numberOfMoney));
		logger.info("mainId:{},借款人:{},借款类型:{},借款金额:{}", mainId,
				entity.getPerson(), entity.getLoantype(), money);

		List<LoanBursementEntity> bursementEntities = entity
				.getBursementEntities();
		modelMap.put("bursementEntities", bursementEntities);

		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);

		List<ApprovalComment> approvalComments = new ArrayList<ApprovalComment>();
		if (entity.getDraftsFlag() == 1
				&& !entity.getProcStatus().equals(ApprovalStatus.已撤销.name())) {
			approvalComments = workflowService
					.queryHistoryApprovalCommentByProcId(
							entity.getProcinstId(), sessionUser.getUsername());
			logger.info("查询ID={}的流程流转历史格式：{}", mainId, approvalComments.size());
		}
		modelMap.put("approvalComments", approvalComments);

		return "personalWork/print";
	}

	/**
	 * 
	 * <p>
	 * 描述：撤销 按钮事件
	 * </p>
	 * 
	 * @Date 2018年7月2日下午3:28:19 <br>
	 * @param mainId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "revoke", method = RequestMethod.POST)
	public Message revoke(Long mainId, HttpServletRequest request) {
		// TODO 撤销
		logger.info("撤销申请...");
		Message message = null;
		try {
			LoanMainEntity entity = loanMainService.getEntityById(
					LoanMainEntity.class, mainId);

			User sessionUser = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);

			// 验证登录人是否有操作权限
			boolean securityVerification = loanMainService
					.securityVerification(entity.getCurrentApprover(),
							sessionUser.getUsername());
			if (!securityVerification) {
				message = Message
						.failure(ProcessTemplate.verificationTemplate(
								entity.getCurrentApprover(),
								sessionUser.getUsername()));
				return message;
			}

			logger.info(
					"撤销人(sessionUser):{}, 申请ID号(mainId):{}, 撤销的实例ID号(procinstId)",
					new Object[] { sessionUser.getUsername(), mainId,
							entity.getProcinstId() });

			List<ApprovalComment> approvalComments = workflowService
					.queryHistoryApprovalCommentByProcId(
							entity.getProcinstId(), sessionUser.getUsername());
			if (null == approvalComments) {
				message = Message.failure("异常");
			} else {
				if (approvalComments.size() < 2) {
					message = Message.success("撤销成功");
				} else if (approvalComments.size() == 2) {
					// 审批状态
					String status = approvalComments.get(1).getStatus();
					if (StringUtils.isEmpty(status) || !status.equals("同意")) {
						// 可以撤销
						loanMainService.revokeApply(entity,
								sessionUser.getUsername());
						message = Message.success("撤销成功");
					} else {
						message = Message.failure("该单已到流程中，不可撤销！");
					}
				} else {
					// 当前任务审批人 等于 申请人，并且 当前任务环节 等于 填写申请单
					if (entity.getCurrentApprover().equals(entity.getPerson())
							&& entity.getCurrentWork().equals(
									IConstant.CREATE_APPLY_LINK)) {
						// 可以撤销
						loanMainService.revokeApply(entity,
								sessionUser.getUsername());
						message = Message.success("撤销成功");
					} else {
						message = Message.failure("该单已到流程中，不可撤销！");
					}
				}
			}
		} catch (Exception e) {
			message = Message.failure("该单已到流程中，不可撤销！", e);
		}
		logger.info(message.toString());
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：催办 按钮事件
	 * </p>
	 * 
	 * @Date 2018年7月2日下午4:36:17 <br>
	 * @param mainId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "urge", method = RequestMethod.POST)
	public Message urge(Long mainId) {
		// TODO 催办
		logger.info("催办(mainId):{}...", mainId);
		Message message = null;
		try {
			loanMainService.urge(mainId);
			message = Message.success("催办成功！");
		} catch (Exception e) {
			message = Message.failure("催办失败", e);
		}
		logger.info(message.toString());
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：驳回或补正 按钮事件
	 * </p>
	 * 
	 * @Date 2018年7月3日下午1:01:26 <br>
	 * @param mainId
	 *            记录ID
	 * @param comment
	 *            意见
	 * @param operator
	 *            操作（驳回|补正）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rejectOrCorrections", method = RequestMethod.POST)
	public Message rejectOrCorrections(Long mainId, String comment,
			String operator, HttpServletRequest request) {
		// TODO 驳回或补正
		Message message = null;
		try {
			User sessionUser = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			workflowService.rejectOrCorrections(mainId, comment, operator,
					sessionUser.getUsername());
			message = Message.success("操作成功！");
		} catch (Exception e) {
			logger.error("操作失败", e);
			message = Message.failure("操作失败", e);
		}
		return message;
	}

	/**
	 * @deprecated <p>
	 *             描述：提交补正 按钮事件
	 *             </p>
	 * 
	 * @Date 2018年7月3日下午3:52:27 <br>
	 * @param mainId
	 *            记录ID
	 * @param comment
	 *            意见
	 * @param loanExplain
	 *            借款用途及说明
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "submitCorrections", method = RequestMethod.POST)
	public Message submitCorrections(Long mainId, String loanExplain,
			String comment) {
		// TODO 提交补正
		Message message = null;
		try {
			workflowService.submitCorrections(mainId, loanExplain, comment);
			message = Message.success("操作成功！");
		} catch (Exception e) {
			logger.error("操作失败", e);
			message = Message.failure("操作失败", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：提交补正|驳回 按钮事件
	 * </p>
	 * 
	 * @Date 2018年7月30日下午2:23:38 <br>
	 * @param loanMainEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "submitCorrections2", method = RequestMethod.POST)
	public Message submitCorrections2(LoanMainEntity loanMainEntity,
			String approveContent, HttpServletRequest request) {
		// TODO 提交补正
		Message message = null;
		try {
			User sessionUser = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			workflowService.submitCorrections2(loanMainEntity, approveContent,
					sessionUser);
			message = Message.success("操作成功！");
		} catch (Exception e) {
			logger.error("操作失败", e);
			message = Message.failure("操作失败", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：删除 草稿
	 * </p>
	 * 
	 * @Date 2018年7月2日上午10:59:59 <br>
	 * @param mainId
	 *            要删除的记录ID
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteDraft", method = RequestMethod.POST)
	public Message deleteDraft(Long mainId, HttpServletRequest request) {
		// TODO 删除草稿
		logger.info("删除草稿箱(mainId):{}", mainId);
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);

			List<Long> ids = new ArrayList<Long>();
			ids.add(mainId);
			loanMainService.deleteEntityByIds(LoanMainEntity.class, ids,
					user.getUsername());

			message = Message.success("删除草稿成功！");
		} catch (Exception e) {
			message = Message.failure("删除草稿失败", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：生成并更新凭证号
	 * </p>
	 * 
	 * @Date 2018年6月25日上午10:05:41 <br>
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createPZNumber", method = RequestMethod.POST)
	public Message createPZNumber(@RequestParam("mainId") Long mainId,
			HttpServletRequest request) {
		// TODO 生成凭证号
		logger.info("生成并更新凭证号......");
		Message message = null;
		User user = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		LoanMainEntity mainEntity = loanMainService.getEntityById(
				LoanMainEntity.class, mainId);
		try {
			// 生成凭证号
			String pzNumber = HrInfoUtil.createPzNum(user, mainEntity);

			// 更新凭证号
			mainEntity.setPzNumber(pzNumber);
			mainEntity.setLastupdateBy(user.getUsername());
			mainEntity.setLastupdateDate(new Date());
			loanMainService.updatePZNumber(mainEntity);

			message = Message.success("凭证号生成成功", pzNumber);
			logger.info("mainId={}的记录，凭证号={}生成并更新成功！", mainId, pzNumber);
		} catch (Exception e) {
			logger.error("凭证号生成失败！", e);
			message = Message.failure("凭证号生成失败！", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取分公司会计
	 * </p>
	 * 
	 * @Date 2018年7月6日下午12:48:26 <br>
	 * @param branch
	 *            分公司名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getBranchAccounting", method = RequestMethod.POST)
	public Message getBranchAccounting(@RequestParam("branch") String branch) {
		Message message = null;
		try {
			String branchAccounting = InitUtil.branchAccountingMap.get(branch);
			if (StringUtils.isEmpty(branchAccounting)) {
				message = Message.failure("没有获取到【" + branch + "】的会计，请先维护信息！");
			} else {
				message = Message.success("获取成功", branchAccounting);
			}
		} catch (Exception e) {
			message = Message.failure("获取分公司会计出错：", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：转分公司会计 按钮事件
	 * </p>
	 * 
	 * @Date 2018年7月6日下午4:38:36 <br>
	 * @param mainId
	 *            记录ID
	 * @param branch
	 *            分公司
	 * @param branchAccounting
	 *            分公司会计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "turnBranchAccounting", method = RequestMethod.POST)
	public Message turnBranchAccounting(Long mainId, String branch,
			String branchAccounting) {
		// TODO 转分公司会计
		logger.info("转分公司会计...");
		logger.info(
				"记录ID(mainId):{}, 分公司名称(branch):{}, 分公司会计(branchAccounting):{}",
				mainId, branch, branchAccounting);
		Message message = null;
		try {
			workflowService.turnBranchAccounting(mainId, branch,
					branchAccounting);
			message = Message.success("操作成功");
		} catch (Exception e) {
			message = Message.failure("操作失败：", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 平台部长
	 * </p>
	 * 
	 * @Date 2018年7月10日下午4:32:59 <br>
	 * @param platformName
	 *            平台部门名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPlatformMinister", method = RequestMethod.POST)
	public Message getPlatformMinister(
			@RequestParam("platformName") String platformName) {
		Message message = null;
		try {
			String minister = InitUtil.platformMinisterMap.get(platformName);
			if (StringUtils.isEmpty(minister)) {
				message = Message.failure("没有获取到【" + platformName
						+ "】的部长，请先维护信息！");
			} else {
				message = Message.success("获取成功", minister);
			}
		} catch (Exception e) {
			message = Message.failure("获取平台部长出错：", e);
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：转平台部长
	 * </p>
	 * 
	 * @Date 2018年7月10日下午4:53:20 <br>
	 * @param mainId
	 *            记录ID
	 * @param platformName
	 *            平台名称
	 * @param ministerName
	 *            部长名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "trunPlatformMinister", method = RequestMethod.POST)
	public Message trunPlatformMinister(Long mainId, String platformName,
			String ministerName, HttpServletRequest request) {
		// TODO 转平台部长
		logger.info("转平台部长...");
		logger.info(
				"记录ID(mainId):{}, 平台名称(platformName):{}, 部长名称(ministerName):{}",
				mainId, platformName, ministerName);
		Message message = null;
		try {
			User sessionUser = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			workflowService.turnPlatformMinister(mainId, platformName,
					ministerName, sessionUser.getUsername());
			message = Message.success("操作成功");
		} catch (Exception e) {
			message = Message.failure("异常：" + e.getMessage());
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：生成预付款发票
	 * </p>
	 * 
	 * @Date 2018年7月19日上午11:29:23 <br>
	 * @param mainId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createInvoice", method = RequestMethod.POST)
	public Message createInvoice(Long mainId) {
		// TODO 转平台部长
		logger.info("生成预付款发票...");
		logger.info("记录ID(mainId):{}", mainId);
		Message message = null;
		try {
			message = loanMainService.createInvoice(mainId);
		} catch (Exception e) {
			message = Message.failure("异常：" + e.getMessage());
		}
		return message;
	}

}
