/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.job<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.job;

import java.math.BigDecimal;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.changhongit.loan.service.LoanMainService;

/**
 * <p>
 * 描述：处理创建发票失败Job
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年9月11日上午11:07:18
 */
public class HandleFailureInvoicesJob extends QuartzJobBean {
	private static Logger logger = LoggerFactory
			.getLogger(HandleFailureInvoicesJob.class);

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
		long begin = System.currentTimeMillis();
		logger.info("执行【定时任务类：处理创建发票失败】开始............");
		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();

		ApplicationContext applicationContext = (ApplicationContext) dataMap
				.get("applicationContext");
		LoanMainService loanMainService = applicationContext
				.getBean(LoanMainService.class);
		List<BigDecimal> failureMainIdList = loanMainService
				.getFailureInvoiceList();
		if (null != failureMainIdList && failureMainIdList.size() > 0) {
			logger.info("本次查询出{}条创建发票失败记录.", failureMainIdList.size());
			for (int i = 0, j = failureMainIdList.size(); i < j; i++) {
				try {
					loanMainService.createInvoice(failureMainIdList.get(i)
							.longValue());
					Thread.sleep(3000);
				} catch (Exception e) {
					logger.error("处理创建发票失败-异常", e);
				}
			}
		}

		long end = System.currentTimeMillis();
		logger.info("执行【定时任务类：处理创建发票失败】结束，本次用时：" + (end - begin)
				+ "毫秒............");
	}
}
