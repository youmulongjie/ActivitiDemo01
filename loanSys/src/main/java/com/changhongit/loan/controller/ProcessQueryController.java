/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.changhongit.loan.bean.Page;
import com.changhongit.loan.bean.QueryBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.LoanBursementEntity;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.enums.ApprovalStatus;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.service.WorkflowService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：流程查询Controller（待办页面、审批中页面、草稿箱页面）
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月13日上午9:57:17
 */
@Controller
public class ProcessQueryController extends BaseController {
	public static final Logger logger = LoggerFactory
			.getLogger(ProcessQueryController.class);
	@Autowired
	private LoanMainService loanMainService;
	@Autowired
	private WorkflowService workflowService;

	// [审批中页面]Start
	/**
	 * 
	 * <p>
	 * 描述：跳转到审批中页面
	 * </p>
	 * 
	 * @Date 2018年6月11日下午1:32:58 <br>
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/progress", method = RequestMethod.GET)
	public String toProgress(ModelMap modelMap, HttpServletRequest request) {
		// TODO 跳转到 审批中页面
		setHandlerWorkSet(modelMap);
		return "personalWork/progress";
	}

	/**
	 * <p>
	 * 描述：设置 页面查询选项中 当前处理环节 的下拉列表选项
	 * </p>
	 * 
	 * @Date 2018年6月28日上午9:51:28 <br>
	 * @param modelMap
	 */
	private void setHandlerWorkSet(ModelMap modelMap) {
		modelMap.put("handleWorkSet", InitUtil.bpmnHanderWorkNameSet);
	}

	/**
	 * 
	 * <p>
	 * 描述：加载 审批中列表
	 * </p>
	 * 
	 * @Date 2018年6月11日下午2:26:06 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "/pageProgress", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMainEntity> pageProgress(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			QueryBean queryBean, HttpServletRequest request) {
		// TODO 加载 审批中列表
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		return loanMainService.pageProgressEntitys(currentPage,
				sessionUser.getUsername(), queryBean);
	}

	/**
	 * 
	 * <p>
	 * 描述：加载审批详情
	 * </p>
	 * 
	 * @Date 2018年6月11日下午3:06:21 <br>
	 * @param id
	 *            查看的主界面ID
	 * @param flag
	 *            从哪个页面进入标识(todo:待办；progress:申请中)
	 * @return
	 */
	@RequestMapping(value = "/processDetail", method = RequestMethod.GET)
	public String processDetail(@RequestParam("id") Long id,
			@RequestParam("flag") String flag, HttpServletRequest request,
			ModelMap modelMap) {
		// TODO 加载审批详情
		logger.info("查询ID={}的申请记录", id);
		modelMap.put("flag", flag);

		LoanMainEntity entity = loanMainService.getEntityById(
				LoanMainEntity.class, id);
		modelMap.put("entity", entity);

		List<LoanBursementEntity> bursementEntities = entity
				.getBursementEntities();
		logger.info("查询ID={}的申请付款信息行表个数：{}", id, bursementEntities.size());
		modelMap.put("bursementEntities", bursementEntities);

		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		List<ApprovalComment> approvalComments = new ArrayList<ApprovalComment>();

		// 如果是正式提交（且没有撤销）的数据，则查询 流转历史记录（草稿箱中是数据，因为没有开启工作流，所以不查询）
		if (entity.getDraftsFlag() == 1
				&& !entity.getProcStatus().equals(ApprovalStatus.已撤销.name())) {
			approvalComments = workflowService
					.queryHistoryApprovalCommentByProcId(
							entity.getProcinstId(), sessionUser.getUsername());

			// 特殊处理步骤1:财务会计 环节处理人
			ApprovalComment approvalComment = null;
			for (int i = 0, j = approvalComments.size(); i < j; i++) {
				approvalComment = approvalComments.get(i);
				// 财务会计 环节，处理人为null（说明还没有财务认领该流程，特殊处理）
				if (approvalComment.getTaskName().equals(
						IConstant.FN_ACCOUNTING_LINK)
						&& StringUtils.isEmpty(approvalComment.getApprovor())) {
					approvalComment.setApprovor(InitUtil.fnAccountingMap
							.get(entity.getLoantype()));
				}
			}

			// 特殊处理步骤2:判断是否审批结束
			Boolean isEnd = loanMainService.isEnd(id);
			logger.info("isEnd=", isEnd);
			if (isEnd) {
				// 如果审批已经结束，添加页面展示标识
				ApprovalComment comment = new ApprovalComment();
				comment.setApprovor("结束(ˉ(∞)ˉ)结束");
				comment.setTaskName("结束(ˉ(∞)ˉ)结束");
				comment.setStatus("结束(ˉ(∞)ˉ)结束");
				comment.setApprovorDate("结束(ˉ(∞)ˉ)结束");
				comment.setDesc("结束(ˉ(∞)ˉ)结束");
				approvalComments.add(comment);
			}
			logger.info("查询ID={}的流程流转历史格式：{}", id, approvalComments.size());
		}
		modelMap.put("approvalComments", approvalComments);

		// 平台部门集合（转平台部长 使用）
		Set<String> platforms = InitUtil.platformMinisterMap.keySet();
		modelMap.put("platforms", platforms);

		return "personalWork/processDetail";
	}

	// [审批中页面]End
	// .......................
	// [待办页面]Start
	/**
	 * 
	 * <p>
	 * 描述：跳转到待办页面
	 * </p>
	 * 
	 * @Date 2018年6月11日下午1:32:58 <br>
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String toTodo(ModelMap modelMap, HttpServletRequest request) {
		// TODO 跳转到 待办页面
		setHandlerWorkSet(modelMap);
		return "personalWork/todo";
	}

	/**
	 * 
	 * <p>
	 * 描述：加载 待办列表
	 * </p>
	 * 
	 * @Date 2018年6月13日上午10:16:40 <br>
	 * @param currentPage
	 * @param queryBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageToDo", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMainEntity> pageToDo(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			QueryBean queryBean, HttpServletRequest request) {
		// TODO 加载 待办列表
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		return loanMainService.pageToDoEntitys(currentPage,
				sessionUser.getUsername(), queryBean);
	}

	/**
	 * 
	 * <p>
	 * 描述：查询登陆者的 待办个数
	 * </p>
	 * 
	 * @Date 2018年6月13日上午11:12:37 <br>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/todoCount", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Integer todoCount(HttpServletRequest request) {
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		// TODO 查询登陆者的 待办个数
		return loanMainService.todoCount(sessionUser.getUsername());
	}

	// [待办页面]End
	// .......................
	// [流程结束页面]Start

	/**
	 * 
	 * <p>
	 * 描述：跳转到 流程结束页面
	 * </p>
	 * 
	 * @Date 2018年6月21日上午11:22:12 <br>
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/end", method = RequestMethod.GET)
	public String toEnd(ModelMap modelMap, HttpServletRequest request) {
		// TODO 跳转到 流程结束页面
		setHandlerWorkSet(modelMap);
		return "personalWork/end";
	}

	/**
	 * 
	 * <p>
	 * 描述：加载 流程结束 列表
	 * </p>
	 * 
	 * @Date 2018年6月21日上午11:28:31 <br>
	 * @param currentPage
	 * @param queryBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageEnd", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMainEntity> pageEnd(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			QueryBean queryBean, HttpServletRequest request) {
		// TODO 加载 流程结束列表
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		return loanMainService.pageEndEntitys(currentPage,
				sessionUser.getUsername(), queryBean);
	}

	// [流程结束页面]End
	// .......................
	// [草稿箱页面]Start
	/**
	 * <p>
	 * 描述：跳转到 草稿箱页面
	 * </p>
	 * 
	 * @Date 2018年6月28日下午3:27:25 <br>
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/draft", method = RequestMethod.GET)
	public String toDraft(ModelMap modelMap, HttpServletRequest request) {
		// TODO 跳转到 草稿箱页面
		setHandlerWorkSet(modelMap);
		return "personalWork/draft";
	}

	/**
	 * 
	 * <p>
	 * 描述：加载 草稿箱列表
	 * </p>
	 * 
	 * @Date 2018年6月28日下午3:27:19 <br>
	 * @param currentPage
	 * @param queryBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageDraft", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMainEntity> pageDraft(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			QueryBean queryBean, HttpServletRequest request) {
		// TODO 加载 草稿箱列表
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		return loanMainService.pageDraftEntitys(currentPage,
				sessionUser.getUsername(), queryBean);
	}

	// [草稿箱页面]End
	// .......................
	// [查询流程图]Start

	/**
	 * 
	 * <p>
	 * 描述：跳转到 查询流程图页面
	 * </p>
	 * 
	 * @Date 2018年7月20日上午9:28:07 <br>
	 * @return
	 */
	@RequestMapping(value = "/showBPMN", method = RequestMethod.GET)
	public String showBPMN(ModelMap modelMap) {
		// TODO 跳转到 查询流程图页面
		modelMap.put("bpmn", InitUtil.loanTypeToBPMNMap);
		return "personalWork/showBPMN";
	}

	// [查询流程图]End
	// .......................
	// [管理员查看申请单]Start

	/**
	 * 
	 * <p>
	 * 描述：跳转到 管理员查看申请单
	 * </p>
	 * 
	 * @Date 2018年9月10日上午11:22:12 <br>
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String toManage(ModelMap modelMap, HttpServletRequest request) {
		// TODO 跳转到 管理员查看申请单
		setHandlerWorkSet(modelMap);
		return "personalWork/manage";
	}

	/**
	 * 
	 * <p>
	 * 描述：加载 管理员查看申请单 列表
	 * </p>
	 * 
	 * @Date 2018年9月10日上午11:28:31 <br>
	 * @param currentPage
	 * @param queryBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pageManage", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMainEntity> pageManage(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			QueryBean queryBean, HttpServletRequest request) {
		// TODO 加载 管理员查看申请单列表
		logger.info("currentPage:{}", currentPage);
		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);
		return loanMainService.pageManageEntitys(currentPage, sessionUser,
				queryBean);
	}

	// [管理员查看申请单]End
}
