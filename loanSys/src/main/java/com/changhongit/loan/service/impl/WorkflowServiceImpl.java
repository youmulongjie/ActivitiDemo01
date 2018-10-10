/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.changhongit.loan.bean.ApprovalComment;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.bean.workflowparam.WorkFlowApplyerParam;
import com.changhongit.loan.bean.workflowparam.WorkFlowBusinessParam;
import com.changhongit.loan.bean.workflowparam.WorkFlowLeaderParam;
import com.changhongit.loan.bean.workflowparam.WorkFlowParamUtil;
import com.changhongit.loan.bean.workflowparam.WorkFlowRelatedParam;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.constant.ProcessTemplate;
import com.changhongit.loan.dao.LoanMainDao;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.enums.ApprovalStatus;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.InvoiceStatusEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.service.WorkflowService;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.loan.util.SendFinishMailThread;
import com.changhongit.loan.util.SendRejectMailThread;
import com.changhongit.loan.util.SendTipMailThread;

/**
 * <p>
 * 描述：Activiti 工作流 Service层接口 实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月7日上午11:18:20
 */
@Service
@Transactional
public class WorkflowServiceImpl extends ProcessTemplate implements
		WorkflowService {
	private final Logger logger = LoggerFactory
			.getLogger(WorkflowServiceImpl.class);
	@Autowired
	private LoanMainDao loanMainDao;

	// 流程存储服务组件（管理流程部署）
	@Autowired
	private RepositoryService repositoryService;
	// 流程控制服务组件（管理流程在运行时产生的流程参数、事件、流程实例、以及执行流）
	@Autowired
	private RuntimeService runtimeService;
	// 任务服务组件（任务操作、任务数据管理）
	@Autowired
	private TaskService taskService;
	// 历史数据服务组件（提供对历史数据的查询、删除方法）
	@Autowired
	private HistoryService historyService;
	// 维护流程服务组件
	@Autowired
	private ManagementService managementService;
	// 用户组管理服务组件（管理流程的身份数据，公司有自己的人员组织管理，不使用这个）
	@Autowired
	private IdentityService identityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#startProcess(com.changhongit
	 * .loan.entity.LoanMainEntity, com.changhongit.loan.bean.User)
	 */
	@Override
	public LoanMainEntity startProcess(LoanMainEntity loanMainEntity,
			User sessionUser) throws Exception {
		// TODO 启动审批流程
		logger.info("【启动流程】方法【开始】...");
		// 流程业务键
		String businessKey = MessageFormat.format(businesskeyTemplate,
				loanMainEntity.getMainId());
		logger.info("设置流程业务键(businessKey):{}", businessKey);

		// 根据 业务键判断是否已存在流程实例
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		if (null != historicProcessInstance) {
			logger.info("已存在名称为【{}】的流程实例，启动工作流失败！", businessKey);
			throw new Exception("已存在名称为【" + businessKey + "】的流程实例！");
		}

		WorkFlowParamUtil flowParamUtil = new WorkFlowParamUtil(loanMainEntity,
				sessionUser);
		WorkFlowApplyerParam applyerParam = flowParamUtil.getApplyerParam();
		logger.info("申请人信息：{}", applyerParam.toString());
		WorkFlowLeaderParam leaderParam = flowParamUtil.getLeaderParam();
		logger.info("申请人领导链信息：{}", leaderParam.toString());
		WorkFlowBusinessParam businessParam = flowParamUtil.getBusinessParam();
		logger.info("申请业务数据信息：{}", businessParam.toString());
		WorkFlowRelatedParam relatedParam = flowParamUtil.getRelatedParam();
		logger.info("申请相关人员信息（财务、法务、固定人员等）：{}", relatedParam.toString());

		// 流程实例参数Map对象
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applyerParam", applyerParam);
		vars.put("leaderParam", leaderParam);
		vars.put("businessParam", businessParam);
		vars.put("relatedParam", relatedParam);
		// 默认 审批意见是同意
		vars.put("result", ApprovalStatus.同意.name());
		vars.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

		// BPMN流程图定义的id
		String processDefinitionKey = InitUtil.loanTypeToBPMNMap
				.get(loanMainEntity.getLoantype());
		logger.info("借款类型:{}, BPMN:{}", loanMainEntity.getLoantype(),
				processDefinitionKey);
		// 启动流程（根据流程文件定义的process节点的id）
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(
				processDefinitionKey, businessKey, vars);
		if (null == pi) {
			logger.info("ProcessInstance is null，启动工作流失败！");
			throw new Exception("启动工作流失败！");
		}
		logger.info("流程实例ID:{}", pi.getId());
		// 设置 流程实例名称
		String instanceName = MessageFormat.format(instanceNameTemplate,
				loanMainEntity.getPerson(), loanMainEntity.getMainId());
		runtimeService.setProcessInstanceName(pi.getId(), instanceName);
		logger.info("设置流程实例名称(instanceName):{}", instanceName);

		// 查询当前任务，给当前任务添加批注信息
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
				.singleResult();
		logger.info("当前任务(task):ID={},任务名称={}", task.getId(), task.getName());

		// 设置评论中userId
		Authentication.setAuthenticatedUserId(task.getAssignee());
		taskService.addComment(task.getId(), pi.getId(), "提交申请");
		taskService.addComment(task.getId(), pi.getId(), "event",
				ApprovalStatus.同意.name());
		Map<String, Object> taskVars = new HashMap<String, Object>(1);
		taskVars.put("result", ApprovalStatus.提交申请.name());
		// 完成当前任务，推进到下一任务
		taskService.complete(task.getId(), taskVars);
		logger.info("完成当前任务(task):ID={}", task.getId());

		// 查询当前任务，设置当前审批人
		task = taskService.createTaskQuery().processInstanceId(pi.getId())
				.singleResult();
		logger.info("当前任务(task):ID={},任务名称={}", task.getId(), task.getName());
		if (null != task) {
			// 设置当前审批人
			String assignee = task.getAssignee();
			loanMainEntity.setCurrentApprover(assignee);
			logger.info("设置当前任务(task):{}的审批人(assignee):{}", task.getId(),
					assignee);
			// 设置当前审批环节
			loanMainEntity.setCurrentWork(task.getName());
			// 设置此任务的历史审批人（任务的关联人，代办查询使用）
			loanMainEntity.setHistoryApprovers(sessionUser.getUsername() + ","
					+ assignee + ",");

			// 给审批人发邮件提醒
			// RemindMailTools.sendTipMail(assignee, loanMainEntity);
			SendTipMailThread sendTipMailThread = new SendTipMailThread(
					assignee, loanMainEntity);
			sendTipMailThread.start();
		}
		// 将流程实例ID 绑定到 借款主表Entity上
		loanMainEntity.setProcinstId(pi.getId());
		loanMainEntity.setTitle(instanceName);
		loanMainEntity.setProcStatus(ApprovalStatus.提交申请.name());

		loanMainEntity.setSuperiorApproveDate(new Date());
		loanMainEntity.setSuperiorApprover(loanMainEntity.getPerson());

		logger.info("【启动流程】方法【结束】：流程启动成功...");
		return loanMainEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.WorkflowService#
	 * queryHistoryApprovalCommentByProcId(java.lang.String)
	 */
	@Override
	public List<ApprovalComment> queryHistoryApprovalCommentByProcId(
			String procId, String sessionUserName) {
		// TODO 根据流程实例ID查找 历史审批记录

		// 多人任务时，默认谁先点击查看谁认领该任务
		claim(procId, sessionUserName);

		List<ApprovalComment> list = new ArrayList<ApprovalComment>();

		// 根据流程实例ID 查询历史任务表（act_hi_taskinst）
		List<HistoricTaskInstance> historicTaskInstances = historyService
				.createHistoricTaskInstanceQuery().processInstanceId(procId)
				.orderByHistoricTaskInstanceEndTime().asc().list();

		if (null != historicTaskInstances && historicTaskInstances.size() > 0) {
			ApprovalComment approvalComment = null;
			HistoricTaskInstance taskInstance = null;
			Comment comment = null;
			// 任务ID
			String taskId = null;
			for (int i = 0, j = historicTaskInstances.size(); i < j; i++) {
				taskInstance = historicTaskInstances.get(i);
				// skip 环节时，历史审批记录人为空，跳过不展示
				if (!StringUtils.isEmpty(taskInstance.getAssignee())) {
					approvalComment = new ApprovalComment();
					taskId = taskInstance.getId();

					approvalComment.setApprovor(taskInstance.getAssignee());
					approvalComment.setTaskName(taskInstance.getName());

					// 审批状态（通过、驳回、补正等）
					List<Comment> eventList = taskService.getTaskComments(
							taskId, "event");
					// 备注信息
					List<Comment> commentList = taskService.getTaskComments(
							taskId, "comment");

					// 每个任务只有一条 event和一条 comment
					CommentEntity commentEntity = null;
					if (null != commentList && commentList.size() > 0) {
						comment = commentList.get(0);
						commentEntity = (CommentEntity) comment;

						JDateTime approvorDate = new JDateTime(
								comment.getTime());
						approvalComment.setApprovorDate(approvorDate
								.toString("YYYY-MM-DD hh:mm:ss"));
						approvalComment.setDesc(commentEntity.getMessage());
					}

					if (null != eventList && eventList.size() > 0) {
						comment = eventList.get(0);
						commentEntity = (CommentEntity) comment;
						approvalComment.setStatus(commentEntity.getMessage());
					}

					list.add(approvalComment);
				}
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#completeTask(java.lang.Long,
	 * com.changhongit.loan.bean.User, java.lang.String, java.lang.Integer)
	 */
	@Override
	public LoanMainEntity completeTask(Long mainId, User sessionUser,
			String comment, int flag) throws Exception {
		// TODO 完成当前任务（推动到下一个任务）
		logger.info("【完成当前任务】方法【开始】...");
		LoanMainEntity loanMainEntity = loanMainDao.findById(mainId);

		// 流程实例ID
		String procinstId = loanMainEntity.getProcinstId();
		logger.info("mainID:{} 查询到对应的流程实例ID是：{}", mainId, procinstId);
		Task task = taskService.createTaskQuery().processInstanceId(procinstId)
				.singleResult();

		if (null != task) {
			logger.info("当前任务(task):ID={},任务名称={}", task.getId(),
					task.getName());
			if (task.getName().equals(IConstant.FN_ACCOUNTING_LINK)
					|| task.getName().equals(
							IConstant.FN_BRANCH_ACCOUNTING_LINK)) {
				// 财务会计|转分公司会计 环节名称，点击同意到下一部时必须存在凭证号
				if (StringUtils.isEmpty(loanMainEntity.getPzNumber())) {
					throw new Exception("凭证号为空，请先生成凭证号！");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (flag == 1) {
				// 同意 标示
				// 审批意见为空，则默认为“同意”
				comment = (comment == null || comment.equals("")) ? ApprovalStatus.同意
						.name() : comment;
				// 设置评论中userId
				Authentication
						.setAuthenticatedUserId(sessionUser.getUsername());
				taskService.addComment(task.getId(), procinstId, comment);
				taskService.addComment(task.getId(), procinstId, "event",
						ApprovalStatus.同意.name());

				map.put("result", ApprovalStatus.同意.name());
			} else if (flag == 2) {
				// 转复核 标识
				Authentication
						.setAuthenticatedUserId(sessionUser.getUsername());
				taskService.addComment(task.getId(), procinstId,
						ApprovalStatus.转复核.name());
				taskService.addComment(task.getId(), procinstId, "event",
						ApprovalStatus.转复核.name());

				map.put("result", ApprovalStatus.转复核.name());
			}

			// 完成当前任务，推进到下一任务
			taskService.complete(task.getId(), map);
			logger.info("完成当前任务(task):ID={}", task.getId());

			notifyApplyerFinish(loanMainEntity, flag);

			// 查看当前流程是否已经结束，如果结束要更新主表中的流程状态，并调用生成发票接口
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(procinstId).singleResult();
			if (pi == null) {
				logger.info("mainID:{} 流程已全部结束!", mainId);
				loanMainEntity.setSuperiorApprover(sessionUser.getUsername());
				loanMainEntity.setSuperiorApproveDate(new Date());

				loanMainEntity.setCurrentApprover("");
				loanMainEntity.setCurrentWork(IConstant.END_LINK);

				// 审批结束时间（借款时间），生成发票使用
				loanMainEntity.setShenpiEndDate(new Date());

				try {
					// 调用生成发票接口
					Message message = HrInfoUtil.createInvoice(loanMainEntity);
					if (message.getCode().equals("success")) {
						// 发票创建成功
						String invoiceNum = (String) message.getData();

						loanMainEntity.setProcStatus(InvoiceStatusEnum.发票创建成功
								.name());
						loanMainEntity
								.setInvoiceStatus(InvoiceStatusEnum.发票创建成功
										.getValue());
						loanMainEntity.setInvoiceNum(invoiceNum);
						loanMainEntity.setInvoiceFailure("");
					} else {
						// 发票创建失败
						loanMainEntity.setProcStatus(InvoiceStatusEnum.发票创建失败
								.name());
						loanMainEntity
								.setInvoiceStatus(InvoiceStatusEnum.发票创建失败
										.getValue());
						loanMainEntity.setInvoiceFailure(message.getResult());
						loanMainEntity.setInvoiceNum("");
					}
					logger.info("创建发票结果：{}", message.toString());
				} catch (Exception e) {
					logger.error("创建发票失败：", e);
					loanMainEntity.setProcStatus(InvoiceStatusEnum.发票创建失败
							.name());
					loanMainEntity.setInvoiceStatus(InvoiceStatusEnum.发票创建失败
							.getValue());
					loanMainEntity.setInvoiceFailure(e.getMessage());
					loanMainEntity.setInvoiceNum("");
				}
			} else {
				// 查询当前任务，设置当前审批人
				task = taskService.createTaskQuery()
						.processInstanceId(procinstId).singleResult();
				logger.info("当前任务(task):ID={},任务名称={}", task.getId(),
						task.getName());
				if (null != task) {
					// 财务会计环节，不设置审批人（没有审批人，有多候选人。多人任务时，默认谁先点击查看谁认领该任务）
					if (task.getName().equals(IConstant.FN_ACCOUNTING_LINK)) {
						logger.info("转到【财务会计】环节.");
						WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) taskService
								.getVariable(task.getId(), "relatedParam");
						String accounting = relatedParam.getAccounting();
						loanMainEntity.setCurrentApprover(accounting);
						logger.info("设置当前任务(task):{}的审批人(accounting):{}",
								task.getId(), accounting);
						// 设置当前审批环节
						loanMainEntity.setCurrentWork(task.getName());
						// 设置此任务的历史审批人（任务的关联人，代办查询使用）
						loanMainEntity.setHistoryApprovers(loanMainEntity
								.getHistoryApprovers() + accounting + ",");

						// 给审批人发邮件提醒
						// RemindMailTools.sendTipMail(accounting,
						// loanMainEntity);
						SendTipMailThread sendTipMailThread = new SendTipMailThread(
								accounting, loanMainEntity);
						sendTipMailThread.start();
					} else {
						// 设置当前审批人
						String assignee = task.getAssignee();
						loanMainEntity.setCurrentApprover(assignee);
						logger.info("设置当前任务(task):{}的审批人(assignee):{}",
								task.getId(), assignee);
						// 设置当前审批环节
						loanMainEntity.setCurrentWork(task.getName());
						// 设置此任务的历史审批人（任务的关联人，代办查询使用）
						loanMainEntity.setHistoryApprovers(loanMainEntity
								.getHistoryApprovers() + assignee + ",");

						// 给审批人发邮件提醒
						// RemindMailTools.sendTipMail(assignee,
						// loanMainEntity);
						SendTipMailThread sendTipMailThread = new SendTipMailThread(
								assignee, loanMainEntity);
						sendTipMailThread.start();
					}
				}
				loanMainEntity.setSuperiorApproveDate(new Date());
				loanMainEntity.setSuperiorApprover(sessionUser.getUsername());
				loanMainEntity.setProcStatus(ApprovalStatus.审批中.name());
			}
			logger.info("【完成当前任务】方法【结束】...");
		}

		loanMainDao.update(loanMainEntity);
		return loanMainEntity;
	}

	/**
	 * 
	 * <p>
	 * 描述：通知申请人
	 * </p>
	 * 
	 * @Date 2018年8月23日下午2:43:11 <br>
	 * @param entity
	 */
	private void notifyApplyerFinish(LoanMainEntity entity, int flag) {
		// 是否要通知，默认false
		boolean isSend = false;
		// 同意操作
		if (flag == 1) {
			// 当前审批节点，借款类型，金额
			String currentWork = entity.getCurrentWork();
			String loantype = entity.getLoantype();
			double totalMoney = entity.getTotalMoney();

			if (loantype.equals(LoanTypeEnum.AssetPurchase.getType())) {
				// 资产购置 类型
				if (currentWork.equals(FixedApproverEnum.财务总监.name())
						&& totalMoney < 20000) {
					isSend = true;
				}
				if (currentWork.equals(FixedApproverEnum.总裁.name())
						&& totalMoney >= 20000) {
					isSend = true;
				}
			} else if (loantype.equals(LoanTypeEnum.Individual.getType())) {
				// 个人因公特殊
				if (currentWork.equals(FixedApproverEnum.财务总监.name())
						&& totalMoney < 10000) {
					isSend = true;
				}
				if (currentWork.equals(FixedApproverEnum.总裁.name())
						&& totalMoney >= 10000) {
					isSend = true;
				}
			} else {
				// 剩余几种的类型
				if (currentWork.equals(FixedApproverEnum.财务总监.name())
						|| currentWork.equals(FixedApproverEnum.财务部长.name())) {
					isSend = true;
				}
			}
		}

		if (isSend) {
			// 邮件通知：您的借款申请已审批完成，请打印纸质单据，递交财务会计签字付款
			SendFinishMailThread finishMailThread = new SendFinishMailThread(
					entity.getPerson(), entity);
			finishMailThread.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.WorkflowService#claim(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void claim(String procinstId, String assignee) {
		// TODO 认领任务（多人候选任务时调用，目前在 财务会计 环节用到）
		Task task = taskService.createTaskQuery().processInstanceId(procinstId)
				.singleResult();
		if (null != task) {
			if (null != task.getName()
					&& task.getName().equals(IConstant.FN_ACCOUNTING_LINK)) {
				WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) taskService
						.getVariable(task.getId(), "relatedParam");
				String accounting = relatedParam.getAccounting();
				// 财务会计才能 认领该任务
				if (accounting.contains(assignee)) {
					logger.info("认领任务...");
					if (StringUtils.isEmpty(task.getAssignee())) {
						// 没人认领任务，则默认登陆者认领
						taskService.claim(task.getId(), assignee);
					} else {
						// 有人已经认领任务，则谁查看谁再重新认领
						taskService.setAssignee(task.getId(), assignee);
					}
					logger.info(
							"{}认领了当前任务（当前任务(task):ID={},任务名称={}）",
							new Object[] { assignee, task.getId(),
									task.getName() });
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#deleteProcinstById(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public void deleteProcinstById(String procinstId, String reason) {
		// TODO 删除 流程
		logger.info("删除流程实例(procinstId):{}.", procinstId);
		runtimeService.deleteProcessInstance(procinstId, reason);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#getCurrentTask(java.lang
	 * .String)
	 */
	@Override
	public Task getCurrentTask(String procinstId) {
		// TODO 获取当前任务
		return taskService.createTaskQuery().processInstanceId(procinstId)
				.singleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#reject(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void rejectOrCorrections(Long mainId, String msg, String operator,
			String operatorName) throws Exception {
		// TODO 驳回或者补正
		logger.info("【{}】方法【开始】...", operator);
		logger.info("操作(operator):{}, 记录ID(mainId):{}, 意见(msg):{}",
				new Object[] { operator, mainId, msg });

		LoanMainEntity entity = loanMainDao.findById(mainId);
		Task task = getCurrentTask(entity.getProcinstId());
		if (null != task) {
			// 设置评论中userId
			Authentication.setAuthenticatedUserId(operatorName);
			taskService.addComment(task.getId(), task.getProcessInstanceId(),
					msg);
			taskService.addComment(task.getId(), task.getProcessInstanceId(),
					"event", operator);

			// 完成当前任务，并指定下一个审批人
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", operator);
			// 从哪个环节 驳回/补正 标识
			map.put("leader", InitUtil.correctionsMap.get(task.getName()));

			taskService.complete(task.getId(), map);

			task = getCurrentTask(entity.getProcinstId());
			String assignee = null;
			if (null != task) {
				assignee = task.getAssignee();
				entity.setCurrentApprover(assignee);
				entity.setCurrentWork(task.getName());

				entity.setSuperiorApproveDate(new Date());
				entity.setSuperiorApprover(operatorName);
				entity.setProcStatus(operator);
				entity.setHistoryApprovers(entity.getHistoryApprovers()
						+ assignee + ",");

				entity.setProcStatus(operator);

				// 更新表记录
				loanMainDao.update(entity);
			}
			// 驳回或补正邮件 提醒
			// RemindMailTools.sendRejectMail(assignee, entity, msg, operator,
			// operatorName);
			SendRejectMailThread sendRejectMailThread = new SendRejectMailThread(
					assignee, entity, msg, operator, operatorName);
			sendRejectMailThread.start();
		}
		logger.info("【{}】方法【结束】...", operator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#submitCorrections(java.lang
	 * .Long, java.lang.String, java.lang.String)
	 */
	@Deprecated
	@Override
	public void submitCorrections(Long mainId, String loanExplain,
			String comment) throws Exception {
		// TODO 提交补正
		LoanMainEntity entity = loanMainDao.findById(mainId);
		Task task = getCurrentTask(entity.getProcinstId());
		if (null != task) {
			String superiorApprover = task.getAssignee();
			Execution exe = runtimeService.createExecutionQuery()
					.processInstanceId(entity.getProcinstId()).singleResult();
			// 驳回 或者 补正
			String result = (String) runtimeService.getVariable(exe.getId(),
					"result");
			// 从哪个环节 驳回 或者 补正的
			Integer leader = (Integer) runtimeService.getVariable(exe.getId(),
					"leader");

			// 设置评论中userId
			Authentication.setAuthenticatedUserId(superiorApprover);
			String message = null;

			Map<String, Object> map = new HashMap<String, Object>(2);
			if (result.equals(ApprovalStatus.驳回.name())) {
				map.put("result", ApprovalStatus.提交申请.name());
				comment = StringUtils.isEmpty(comment) ? "提交驳回申请" : comment;
				message = ApprovalStatus.提交驳回.name();
			} else if (result.equals(ApprovalStatus.补正.name())) {
				map.put("result", ApprovalStatus.提交补正.name());
				// 驳回 或者 补正到 哪个环节
				map.put("leader", leader);
				comment = StringUtils.isEmpty(comment) ? "提交补正申请" : comment;
				message = ApprovalStatus.提交补正.name();
			}

			taskService.addComment(task.getId(), entity.getProcinstId(),
					comment);
			taskService.addComment(task.getId(), entity.getProcinstId(),
					"event", message);

			// 完成当前任务
			taskService.complete(task.getId(), map);

			task = getCurrentTask(entity.getProcinstId());
			String assignee = null;
			if (null != task) {
				assignee = task.getAssignee();
				if (task.getName().equals(IConstant.FN_ACCOUNTING_LINK)) {
					logger.info("转到【财务会计】环节.");
					WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) taskService
							.getVariable(task.getId(), "relatedParam");
					String accounting = relatedParam.getAccounting();
					entity.setCurrentApprover(accounting);
				} else {
					entity.setCurrentApprover(assignee);
				}
				entity.setCurrentWork(task.getName());

				entity.setSuperiorApproveDate(new Date());
				entity.setSuperiorApprover(superiorApprover);
				entity.setProcStatus(message);
				entity.setHistoryApprovers(entity.getHistoryApprovers()
						+ assignee + ",");

				entity.setLoanExplain(loanExplain);

				// 更新表记录
				loanMainDao.update(entity);

				// 给审批人发邮件提醒
				// RemindMailTools.sendTipMail(assignee, entity);
				SendTipMailThread sendTipMailThread = new SendTipMailThread(
						assignee, entity);
				sendTipMailThread.start();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.WorkflowService#submitCorrections2(com.
	 * changhongit.loan.entity.LoanMainEntity, java.lang.String)
	 */
	@Override
	public void submitCorrections2(LoanMainEntity paramEntity, String comment,
			User sessionUser) throws Exception {
		// TODO 提交补正|驳回
		LoanMainEntity entity = loanMainDao.findById(paramEntity.getMainId());

		Execution exe = runtimeService.createExecutionQuery()
				.processInstanceId(entity.getProcinstId()).singleResult();
		// 驳回 或者 补正
		String result = (String) runtimeService.getVariable(exe.getId(),
				"result");
		// 从哪个环节 驳回 或者 补正的
		Integer leader = (Integer) runtimeService.getVariable(exe.getId(),
				"leader");

		logger.info("提交驳回 或者 补正：{}", result);
		logger.info("从哪个环节 驳回 或者 补正的：{}", leader);

		entity.setBursementEntities(paramEntity.getBursementEntities());
		entity.setLoanExplain(paramEntity.getLoanExplain());
		entity.setTotalMoney(paramEntity.getTotalMoney());
		entity.setOu(paramEntity.getOu());

		// 驳回环节，页面可填写内容
		if (result.equals(ApprovalStatus.驳回.name())) {
			String departmentLoan = paramEntity.getDepartmentLoan();
			entity.setDepartmentLoan(departmentLoan);
			if (!StringUtils.isEmpty(departmentLoan)
					&& departmentLoan.equals("否")) {
				entity.setReplaceDept(paramEntity.getReplaceDept());
				entity.setReplaceBusiness(paramEntity.getReplaceBusiness());
				entity.setReplaceLine(paramEntity.getReplaceLine());
			}
			String contractOrBudget = paramEntity.getContractOrBudget();
			if (!StringUtils.isEmpty(contractOrBudget)) {
				entity.setContractOrBudget(contractOrBudget);
			}
		}

		loanMainDao.update(entity);
		Task task = getCurrentTask(entity.getProcinstId());
		if (null != task) {
			String superiorApprover = task.getAssignee();

			// 设置评论中userId
			Authentication.setAuthenticatedUserId(superiorApprover);
			String message = null;

			Map<String, Object> map = new HashMap<String, Object>(2);
			if (result.equals(ApprovalStatus.驳回.name())) {
				// 驳回后，直接重新申请，所以此处为“提交申请”
				map.put("result", ApprovalStatus.提交申请.name());
				comment = StringUtils.isEmpty(comment) ? "提交驳回申请" : comment;
				message = ApprovalStatus.提交驳回.name();

				// 提交驳回时，申请参数改变可能导致领导链变化，因此重新获取一遍，并覆盖之前的
				WorkFlowParamUtil flowParamUtil = new WorkFlowParamUtil(entity,
						sessionUser);
				WorkFlowApplyerParam applyerParam = flowParamUtil
						.getApplyerParam();
				logger.info("提交驳回后-申请人信息：{}", applyerParam.toString());
				WorkFlowLeaderParam leaderParam = flowParamUtil
						.getLeaderParam();
				logger.info("提交驳回后-申请人领导链信息：{}", leaderParam.toString());
				WorkFlowBusinessParam businessParam = flowParamUtil
						.getBusinessParam();
				logger.info("提交驳回后-申请业务数据信息：{}", businessParam.toString());
				WorkFlowRelatedParam relatedParam = flowParamUtil
						.getRelatedParam();
				logger.info("提交驳回后-申请相关人员信息（财务、法务、固定人员等）：{}",
						relatedParam.toString());
				map.put("applyerParam", applyerParam);
				map.put("leaderParam", leaderParam);
				map.put("businessParam", businessParam);
				map.put("relatedParam", relatedParam);

			} else if (result.equals(ApprovalStatus.补正.name())) {
				// 补正后，直接提交补正，所以此处为“提交补正”
				map.put("result", ApprovalStatus.提交补正.name());
				// 驳回 或者 补正到 哪个环节
				map.put("leader", leader);
				comment = StringUtils.isEmpty(comment) ? "提交补正申请" : comment;
				message = ApprovalStatus.提交补正.name();
			}

			taskService.addComment(task.getId(), entity.getProcinstId(),
					comment);
			taskService.addComment(task.getId(), entity.getProcinstId(),
					"event", message);

			// 完成当前任务
			taskService.complete(task.getId(), map);

			task = getCurrentTask(entity.getProcinstId());
			String assignee = null;
			if (null != task) {
				assignee = task.getAssignee();
				if (task.getName().equals(IConstant.FN_ACCOUNTING_LINK)) {
					logger.info("转到【财务会计】环节.");
					WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) taskService
							.getVariable(task.getId(), "relatedParam");
					String accounting = relatedParam.getAccounting();
					entity.setCurrentApprover(accounting);
				} else {
					entity.setCurrentApprover(assignee);
				}
				entity.setCurrentWork(task.getName());

				entity.setSuperiorApproveDate(new Date());
				entity.setSuperiorApprover(superiorApprover);
				entity.setProcStatus(message);
				entity.setHistoryApprovers(entity.getHistoryApprovers()
						+ assignee + ",");

				// 更新表记录
				loanMainDao.update(entity);

				// 给审批人发邮件提醒
				// RemindMailTools.sendTipMail(assignee, entity);
				SendTipMailThread sendTipMailThread = new SendTipMailThread(
						assignee, entity);
				sendTipMailThread.start();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#turnBranchAccounting(java
	 * .lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public void turnBranchAccounting(Long mainId, String branch,
			String branchAccounting) throws Exception {
		// TODO 转分公司会计
		LoanMainEntity entity = loanMainDao.findById(mainId);
		Task task = getCurrentTask(entity.getProcinstId());
		if (null != task) {
			String superiorApprover = task.getAssignee();
			Execution exe = runtimeService.createExecutionQuery()
					.processInstanceId(entity.getProcinstId()).singleResult();

			// 获取 审批流程中 相关人员（财务、法务、固定人员等）参数Bean，并修改参数
			WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) runtimeService
					.getVariable(exe.getId(), "relatedParam");
			relatedParam.setBranch(branch);// 分公司
			relatedParam.setBranchAccounting(branchAccounting);// 分公司会计

			String cashier = InitUtil.branchCashierMap.get(branch);
			relatedParam.setCashier(cashier);// 出纳（转分公司会计，需同时将出纳 修改为分公司出纳）

			// 设置评论中userId
			Authentication.setAuthenticatedUserId(superiorApprover);

			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("result", ApprovalStatus.转分公司会计.name());
			map.put("relatedParam", relatedParam);

			logger.info("（转分公司会计后）申请相关人员信息（财务、法务、固定人员等）：{}",
					relatedParam.toString());

			taskService.addComment(task.getId(), entity.getProcinstId(),
					ApprovalStatus.转分公司会计.name());
			taskService.addComment(task.getId(), entity.getProcinstId(),
					"event", ApprovalStatus.转分公司会计.name());

			// 完成当前任务
			taskService.complete(task.getId(), map);

			task = getCurrentTask(entity.getProcinstId());
			if (null != task) {
				String assignee = task.getAssignee();
				entity.setCurrentApprover(assignee);
				entity.setCurrentWork(task.getName());

				entity.setSuperiorApproveDate(new Date());
				entity.setSuperiorApprover(superiorApprover);
				entity.setProcStatus(ApprovalStatus.审批中.name());
				entity.setHistoryApprovers(entity.getHistoryApprovers()
						+ assignee + ",");

				// 更新表记录
				loanMainDao.update(entity);

				// 给审批人发邮件提醒
				// RemindMailTools.sendTipMail(assignee, entity);
				SendTipMailThread sendTipMailThread = new SendTipMailThread(
						assignee, entity);
				sendTipMailThread.start();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.WorkflowService#turnPlatformMinister(java
	 * .lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void turnPlatformMinister(Long mainId, String platformName,
			String ministerName, String operatorName) throws Exception {
		// TODO 转平台部长
		LoanMainEntity entity = loanMainDao.findById(mainId);

		// 转平台部长 环节名称，必须先存在凭证号
		if (StringUtils.isEmpty(entity.getPzNumber())) {
			throw new Exception("凭证号为空，请先生成凭证号！");
		}
		Task task = getCurrentTask(entity.getProcinstId());
		if (null != task) {
			String superiorApprover = task.getAssignee();
			Execution exe = runtimeService.createExecutionQuery()
					.processInstanceId(entity.getProcinstId()).singleResult();

			// 获取 审批流程中 相关人员（财务、法务、固定人员等）参数Bean，并修改参数
			WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) runtimeService
					.getVariable(exe.getId(), "relatedParam");
			relatedParam.setPlatformName(platformName);// 转的平台部门（暂时没有用到）
			relatedParam.setMainPlatformMinister(ministerName);// 主责平台部长

			// 设置评论中userId
			Authentication.setAuthenticatedUserId(operatorName);

			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("result", ApprovalStatus.转平台部长.name());
			map.put("relatedParam", relatedParam);

			logger.info("（转平台部长后）申请相关人员信息（财务、法务、固定人员等）：{}",
					relatedParam.toString());

			taskService.addComment(task.getId(), entity.getProcinstId(), "转【"
					+ platformName + "】平台部长【" + ministerName + "】");
			taskService.addComment(task.getId(), entity.getProcinstId(),
					"event", ApprovalStatus.转平台部长.name());

			// 完成当前任务
			taskService.complete(task.getId(), map);

			task = getCurrentTask(entity.getProcinstId());
			if (null != task) {
				String assignee = task.getAssignee();
				entity.setCurrentApprover(assignee);
				entity.setCurrentWork(task.getName());

				entity.setSuperiorApproveDate(new Date());
				entity.setSuperiorApprover(superiorApprover);
				entity.setProcStatus(ApprovalStatus.审批中.name());
				entity.setHistoryApprovers(entity.getHistoryApprovers()
						+ assignee + ",");

				// 更新表记录
				loanMainDao.update(entity);

				// 给审批人发邮件提醒
				// RemindMailTools.sendTipMail(assignee, entity);
				SendTipMailThread sendTipMailThread = new SendTipMailThread(
						assignee, entity);
				sendTipMailThread.start();
			}
		}
	}

}
