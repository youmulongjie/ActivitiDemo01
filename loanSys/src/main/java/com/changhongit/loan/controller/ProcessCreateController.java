/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.datetime.JDateTime;

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

import com.changhongit.HRModule.hr.CuxHrEmployeePositionRec;
import com.changhongit.HRModule.hr.ResultCuxHrOrgLowHigRec;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.PersonalInfoBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.AttachmentEntity;
import com.changhongit.loan.entity.LoanBursementEntity;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.util.AttachUtil;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.orgServiceWS.client.SonOrgListBean;

/**
 * <p>
 * 描述：创建申请Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日下午2:05:15
 */
@Controller
public class ProcessCreateController extends BaseController {
	public static final Logger logger = LoggerFactory
			.getLogger(ProcessCreateController.class);

	@Autowired
	private LoanMainService loanMainService;

	/**
	 * 
	 * <p>
	 * 描述：跳转到 创建申请页面
	 * </p>
	 * 
	 * @Date 2018年5月24日下午2:07:23 <br>
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) {
		// TODO 跳转到 创建申请页面，初始化登陆者 职位信息及领导链信息
		logger.info("跳转到 创建申请页面......");

		User user = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		// 加载 借款人职位列表，默认显示主职位
		String erpNum = user.getErpNum();
		List<CuxHrEmployeePositionRec> positionRecs = HrInfoUtil
				.getPersonJobInfo(erpNum);
		modelMap.put("positionRecs", positionRecs);

		// 默认显示 主职位对应的部门（upDept\dept，如“分管信息高管\信息中心”）
		String dept = null;
		Long organizationId = null;
		String assignNum = null;
		for (CuxHrEmployeePositionRec bean : positionRecs) {
			if (bean.getPrimaryFlag().equals("Y")) {
				dept = bean.getOrganizationName();// 所在部门
				organizationId = bean.getOrganizationId();// 组织ID
				assignNum = bean.getAssignmentNumber();// 分配编号
			}
		}
		ResultCuxHrOrgLowHigRec bean = HrInfoUtil.getUpOrgStruc(organizationId);
		String upDept = bean.getOrganizationName();
		modelMap.put("upDept", upDept);// 上级部门
		modelMap.put("dept", dept);
		modelMap.put("assignNum", assignNum);
		logger.info("默认加载主职位信息..");
		logger.info(
				"所在部门(dept):{},组织ID(organizationId):{},分配编号(assignNum):{},上级部门(upDept):{}",
				new Object[] { dept, organizationId, assignNum, upDept });

		// 申请时间，当前时间
		JDateTime today = new JDateTime(new Date());
		today.setFormat("YYYY-MM-DD hh:mm:ss");
		String applyDate = today.toString();
		modelMap.put("applyDate", applyDate);

		// 当前日期
		JDateTime jDateTime = new JDateTime(new Date());
		jDateTime.setFormat("YYYY-MM-DD");
		modelMap.put("currentDate", jDateTime.toString());

		// 说明从 草稿箱跳入，需要给页面回显值
		LoanMainEntity loanMainEntity = new LoanMainEntity();
		List<LoanBursementEntity> bursementEntities = null;
		if (null != id) {
			loanMainEntity = loanMainService.getEntityById(
					LoanMainEntity.class, id);
			bursementEntities = loanMainEntity.getBursementEntities();

			// 从 保存记录中取值
			modelMap.put("upDept", loanMainEntity.getUpDept());// 上级部门
			modelMap.put("dept", loanMainEntity.getDept());
			modelMap.put("assignNum", loanMainEntity.getAssignNum());
		}
		modelMap.put("loanMainEntity", loanMainEntity);
		modelMap.put("bursementEntities", bursementEntities);
		return "personalWork/create";
	}

	/**
	 * 
	 * <p>
	 * 描述：借款人职位 change事件
	 * </p>
	 * 
	 * @Date 2018年5月28日上午10:00:13 <br>
	 * @param erpNum
	 *            借款人ERP编号
	 * @param position
	 *            职位
	 * @return
	 */
	@RequestMapping(value = "changePosition", method = RequestMethod.POST)
	@ResponseBody
	public Message changePosition(@RequestParam("erpNum") String erpNum,
			@RequestParam("position") String position) {
		logger.info("借款人职位 change事件......");
		logger.info("参数 ERP编号(erpNum):{},选择的职位(position):{}", erpNum, position);
		Message message = null;
		List<CuxHrEmployeePositionRec> positionRecs = HrInfoUtil
				.getPersonJobInfo(erpNum);
		String dept = null;
		Long organizationId = null;
		String assignNum = null;
		for (CuxHrEmployeePositionRec bean : positionRecs) {
			if (bean.getPositionName().equals(position)) {
				dept = bean.getOrganizationName();// 所在部门
				organizationId = bean.getOrganizationId();// 组织ID
				assignNum = bean.getAssignmentNumber();// 分配编号
				break;
			}
		}
		ResultCuxHrOrgLowHigRec bean = HrInfoUtil.getUpOrgStruc(organizationId);
		String upDept = bean.getOrganizationName();
		logger.info("change后职位信息..");
		logger.info(
				"所在部门(dept):{},组织ID(organizationId):{},分配编号(assignNum):{},上级部门(upDept):{}",
				new Object[] { dept, organizationId, assignNum, upDept });

		PersonalInfoBean infoBean = new PersonalInfoBean();
		infoBean.setUpDept(upDept);
		infoBean.setDept(dept);
		infoBean.setAssignNum(assignNum);

		message = Message.success().setData(infoBean);
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：加载事业部列表（借款所属部门 列表）
	 * </p>
	 * 
	 * @Date 2018年5月29日下午4:08:43 <br>
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getShiYeBuMap", method = RequestMethod.POST)
	public Message getShiYeBuMap() {
		logger.info("加载事业部列表......");
		Message message;
		try {
			// 借款所属部门
			Map<String, String> shiyeBuMap = InitUtil.getShiYeBuMap();
			message = Message.success(shiyeBuMap);
		} catch (Exception e) {
			logger.error("加载事业部列表失败！", e);
			message = Message.failure("加载事业部列表失败！", e);
		}

		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：加载业务部列表（借款所属业务部 列表 ）
	 * </p>
	 * 
	 * @Date 2018年5月28日上午10:25:32 <br>
	 * @param param
	 *            事业部名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getReplaceBusiness", method = RequestMethod.POST)
	public Message getReplaceBusiness(@RequestParam("param") String param) {
		logger.info("加载业务部列表......");
		logger.info("参数 事业部名称(param):{}", param);
		Message message = null;
		if (!StringUtils.isEmpty(param)) {
			try {
				SonOrgListBean bean = HrInfoUtil.getYeWuBus(param);

				// 以下写法，是为了 三层级联的数据源一致性（数据源都为map对象）
				List<String> list = bean.getSonList();
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0, j = list.size(); i < j; i++) {
					map.put(list.get(i), list.get(i));
				}

				message = Message.success(map);
			} catch (Exception e) {
				logger.error("加载业务部列失败！", e);
				message = Message.failure("加载业务部列失败！", e);
			}
		} else {
			message = Message.success();
		}

		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：加载产品线列表（借款所属产品线 列表 ）
	 * </p>
	 * 
	 * @Date 2018年5月30日下午3:35:24 <br>
	 * @param param
	 *            业务部名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getReplaceLine", method = RequestMethod.POST)
	public Message getReplaceLine(@RequestParam("param") String param) {
		logger.info("加载产品线列表......");
		logger.info("参数 业务部名称(param):{}", param);
		Message message = null;
		if (!StringUtils.isEmpty(param)) {
			try {
				SonOrgListBean bean = HrInfoUtil.getProduceLines(param);

				// 以下写法，是为了 三层级联的数据源一致性（数据源都为map对象）
				List<String> list = bean.getSonList();
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0, j = list.size(); i < j; i++) {
					map.put(list.get(i), list.get(i));
				}

				message = Message.success(map);
			} catch (Exception e) {
				logger.error("加载产品线列失败！", e);
				message = Message.failure("加载产品线列失败！", e);
			}
		} else {
			message = Message.success();
		}

		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：创建申请
	 * </p>
	 * 
	 * @Date 2018年6月7日上午10:17:39 <br>
	 * @param loanMainEntity
	 *            页面提交的借款主表Entity对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "createForm", method = RequestMethod.POST)
	public String createForm(LoanMainEntity loanMainEntity, String remake,
			HttpServletRequest request) {
		logger.info("创建申请......");
		User user = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		loanMainEntity.setCreateBy(user.getUsername());
		try {
			List<AttachmentEntity> attachmentEntitys = AttachUtil.uplaod(request,
					remake, user.getUsername());
			if (null == loanMainEntity.getMainId()) {
				loanMainService.createApply(loanMainEntity, attachmentEntitys,
						user);
			} else {
				loanMainService.updateApply(loanMainEntity, attachmentEntitys, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loanMainEntity.getDraftsFlag() == 1) {
			// 成功提交后，跳转到审批中页面
			return "redirect:/progress.do";
		} else {
			// 保存退出后，跳转到草稿箱页面
			return "redirect:/draft.do";
		}

	}

}
