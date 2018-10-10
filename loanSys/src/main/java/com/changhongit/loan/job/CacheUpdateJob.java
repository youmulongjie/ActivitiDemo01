/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.job<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.job;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.changhongit.loan.entity.LoanPlatformMinisterEntity;
import com.changhongit.loan.enums.BranchEnum;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.service.LoanOrganizationService;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：更新缓存Job
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午3:16:01
 */
public class CacheUpdateJob extends QuartzJobBean {
	private static Logger logger = LoggerFactory
			.getLogger(CacheUpdateJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		long begin = System.currentTimeMillis();
		logger.info("执行【定时任务类：更新缓存】开始............");
		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();

		ApplicationContext applicationContext = (ApplicationContext) dataMap
				.get("applicationContext");

		LoanOrganizationService loanOrganizationService = applicationContext
				.getBean(LoanOrganizationService.class);
		loanOrganizationService.updateLoanOrganizationEntitys();

		try {
			// 将固定审批人 信息保存在Map缓存中
			ManagerService managerService = applicationContext
					.getBean(ManagerService.class);
			FixedApproverEnum[] enums = FixedApproverEnum.values();
			for (FixedApproverEnum e : enums) {
				InitUtil.fixedApproverMap.put(e.name(), managerService
						.findByPosition(e.name()).getName());
			}
			logger.info("定时更新时，固定审批人信息：{}", InitUtil.fixedApproverMap);

			// 每次变更时，将借款单对应发票类型信息 保存在Map缓存中
			LoanTypeEnum[] enums2 = LoanTypeEnum.values();
			for (LoanTypeEnum e : enums2) {
				InitUtil.invoiceMap.put(e.getType(), managerService
						.findInvoiceByLoanType(e.getType()).getAccounting());
			}
			logger.info("定时更新时，借款单对应发票类型信息：{}", InitUtil.invoiceMap);

			// 将借款类型对应会计信息 保存在Map缓存中
			for (LoanTypeEnum e : enums2) {
				InitUtil.fnAccountingMap.put(e.getType(), managerService
						.findAccountingByLoanType(e.getType()).getAccounting());
			}
			logger.info("定时更新时，借款类型对应会计信息：{}", InitUtil.fnAccountingMap);

			// 将分公司出纳配置信息 保存在Map缓存中
			BranchEnum[] enums3 = BranchEnum.values();
			for (BranchEnum e : enums3) {
				InitUtil.branchCashierMap.put(e.name(), managerService
						.findBranchCashierByBranchName(e.name()).getCashier());
			}
			logger.info("定时更新时，分公司出纳配置信息：{}", InitUtil.branchCashierMap);

			// 将分公司会计配置信息 保存在Map缓存中
			for (BranchEnum e : enums3) {
				InitUtil.branchAccountingMap.put(e.name(), managerService
						.findBranchAccountingByBranchName(e.name())
						.getAccounting());
			}
			logger.info("定时更新时，分公司会计配置信息：{}", InitUtil.branchAccountingMap);

			// 将平台部长配置信息 保存在Map缓存中
			List<LoanPlatformMinisterEntity> entities = managerService
					.getAllEntitys(LoanPlatformMinisterEntity.class);
			if (null != entities && entities.size() > 0) {
				LoanPlatformMinisterEntity temp = null;
				for (int i = 0, j = entities.size(); i < j; i++) {
					temp = entities.get(i);
					InitUtil.platformMinisterMap.put(temp.getPlatformName(),
							temp.getMinisterName());
				}
			}
			logger.info("定时更新时，平台部长配置信息：{}", InitUtil.platformMinisterMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}

		long end = System.currentTimeMillis();
		logger.info("执行【定时任务类：更新组织库】结束，本次用时：" + (end - begin)
				+ "毫秒............");
	}

}
