/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

import java.util.List;

import org.activiti.engine.task.Task;

import com.changhongit.loan.bean.ApprovalComment;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.entity.LoanMainEntity;

/**
 * <p>
 * 描述：Activiti 工作流 Service层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月7日上午11:14:02
 */
public interface WorkflowService {
	/**
	 * 
	 * <p>
	 * 描述：启动审批流程
	 * </p>
	 * 
	 * @Date 2018年6月7日上午11:15:32 <br>
	 * @param loanMainEntity
	 *            借款主表Entity对象
	 * @param sessionUser
	 *            session中User对象
	 * @return
	 * @throws Exception
	 */
	LoanMainEntity startProcess(LoanMainEntity loanMainEntity, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：根据流程实例ID查找 历史审批记录
	 * </p>
	 * 
	 * @Date 2018年6月12日上午10:32:48 <br>
	 * @param procId
	 * @param sessionUserName
	 * @return
	 */
	List<ApprovalComment> queryHistoryApprovalCommentByProcId(String procId,
			String sessionUserName);

	/**
	 * 
	 * <p>
	 * 描述：完成当前任务（推动到下一个任务）
	 * </p>
	 * 
	 * @Date 2018年6月13日下午2:21:02 <br>
	 * @param mainId
	 *            主记录ID
	 * @param sessionUser
	 *            session中User对象
	 * @param comment
	 *            审批意见
	 * @param flag
	 *            操作标识（1：同意；2：转复核）
	 * @return
	 * @throws Exception
	 */
	LoanMainEntity completeTask(Long mainId, User sessionUser, String comment,
			int flag) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：认领任务（多人候选任务时调用）
	 * </p>
	 * 
	 * @Date 2018年6月21日上午10:17:41 <br>
	 * @param procinstId
	 *            实例ID
	 * @param assignee
	 *            认领人
	 */
	void claim(String procinstId, String assignee);

	/**
	 * 
	 * <p>
	 * 描述：删除 流程
	 * </p>
	 * 
	 * @Date 2018年7月2日下午3:30:34 <br>
	 * @param procinstId
	 *            流程实例ID
	 * @param reason
	 *            删除原因
	 */
	void deleteProcinstById(String procinstId, String reason);

	/**
	 * 
	 * <p>
	 * 描述：获取当前任务
	 * </p>
	 * 
	 * @Date 2018年7月2日下午4:10:28 <br>
	 * @param procinstId
	 *            流程实例ID
	 * @return
	 */
	Task getCurrentTask(String procinstId);

	/**
	 * 
	 * <p>
	 * 描述：驳回或者补正
	 * </p>
	 * 
	 * @Date 2018年7月3日下午1:13:21 <br>
	 * @param mainId
	 *            记录ID
	 * @param msg
	 *            意见
	 * @param operator
	 *            操作（驳回|补正）
	 * @param operatorName
	 *            操作人
	 */
	void rejectOrCorrections(Long mainId, String msg, String operator,
			String operatorName) throws Exception;

	/**
	 * @deprecated
	 * <p>
	 * 描述：提交补正
	 * </p>
	 * 
	 * @Date 2018年7月3日下午3:11:34 <br>
	 * @param mainId
	 *            记录ID
	 * @param loanExplain
	 *            借款用途及说明
	 * @param comment
	 *            审批意见
	 * @throws Exception
	 */
	void submitCorrections(Long mainId, String loanExplain, String comment)
			throws Exception;
	
	/**
	 * 
	 * <p>
	 * 描述：提交补正|驳回
	 * </p>
	 *
	 * @Date 2018年7月30日下午2:31:14 <br>
	 * @param loanMainEntity
	 * @throws Exception
	 */
	void submitCorrections2(LoanMainEntity loanMainEntity, String comment, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：转分公司会计
	 * </p>
	 * 
	 * @Date 2018年7月6日下午4:48:43 <br>
	 * @param mainId
	 *            记录ID
	 * @param branch
	 *            分公司名称
	 * @param branchAccounting
	 *            分公司会计
	 * @throws Exception
	 */
	void turnBranchAccounting(Long mainId, String branch,
			String branchAccounting) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：转 平台部长
	 * </p>
	 * 
	 * @Date 2018年7月10日下午4:42:04 <br>
	 * @param mainId
	 *            记录ID
	 * @param platformName
	 *            平台部门名称
	 * @param ministerName
	 *            平台部长
	 * @param operatorName 操作人
	 * @throws Exception
	 */
	void turnPlatformMinister(Long mainId, String platformName,
			String ministerName, String operatorName) throws Exception;
}
