/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.test<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.test;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changhongit.loan.bean.ApprovalComment;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.service.WorkflowService;

/**
 * <p>
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月13日下午2:08:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WorkflowServiceTest {
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

	@Autowired
	private LoanMainService loanMainService;
	@Autowired
	private WorkflowService workflowService;

	@Test
	public void test1() {
		LoanMainEntity entity = loanMainService.getEntityById(
				LoanMainEntity.class, 56L);
		System.out.println(entity.getProcinstId());

		Task task = taskService.createTaskQuery()
				.processInstanceId(entity.getProcinstId()).singleResult();
		System.out.println(task.getId() + ";" + task.getName());
	}
	
	@Test
	public void tes2() {
		LoanMainEntity entity = loanMainService.getEntityById(
				LoanMainEntity.class, 166L);
		System.out.println(entity);
	}

	// 删除 部署资源
	@Test
	public void test2() {
		List<Deployment> deployments = repositoryService
				.createDeploymentQuery().deploymentNameLike("loanSys%").list();
		System.out.println("size = " + deployments.size());
		for (Deployment d : deployments) {
			String deploymentId = d.getId();
			System.out.println(deploymentId+"," + d.getName());
			repositoryService.deleteDeployment(deploymentId, true);
		}
	}
	
	@Test
	public void queryHistoryApprovalCommentByProcId(){
		List<ApprovalComment> approvalComments = new ArrayList<ApprovalComment>();
		approvalComments = workflowService
				.queryHistoryApprovalCommentByProcId("2565016", "王龙杰");
		for (int i = 0, j = approvalComments.size(); i < j; i++) {
			System.out.println(approvalComments.get(i));
		}
	}
}
