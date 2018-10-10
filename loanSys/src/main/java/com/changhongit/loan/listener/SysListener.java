/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.listener<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.changhongit.loan.entity.LoanPlatformMinisterEntity;
import com.changhongit.loan.enums.BranchEnum;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.service.LoanOrganizationService;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：项目启动时，初始化Map缓存
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月14日下午2:53:29
 */
public class SysListener implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(SysListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("Tomcat容器【启动】......");
		ServletContext context = sce.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(context);

		LoanOrganizationService loanOrganizationService = ctx
				.getBean(LoanOrganizationService.class);
		loanOrganizationService.updateLoanOrganizationEntitys();
		logger.info("初始化容器时，初始化组织库信息.");

		try {
			ManagerService managerService = ctx.getBean(ManagerService.class);
			// 将固定审批人 信息保存在Map缓存中
			FixedApproverEnum[] enums = FixedApproverEnum.values();
			for (FixedApproverEnum e : enums) {
				InitUtil.fixedApproverMap.put(e.name(), managerService
						.findByPosition(e.name()).getName());
			}
			logger.info("初始化容器时，固定审批人信息：{}", InitUtil.fixedApproverMap);

			// 每次变更时，将借款单对应发票类型信息 保存在Map缓存中
			LoanTypeEnum[] enums2 = LoanTypeEnum.values();
			for (LoanTypeEnum e : enums2) {
				InitUtil.invoiceMap.put(e.getType(), managerService
						.findInvoiceByLoanType(e.getType()).getAccounting());
			}
			logger.info("初始化容器时，借款单对应发票类型信息：{}", InitUtil.invoiceMap);

			// 将借款类型对应会计信息 保存在Map缓存中
			for (LoanTypeEnum e : enums2) {
				InitUtil.fnAccountingMap.put(e.getType(), managerService
						.findAccountingByLoanType(e.getType()).getAccounting());
			}
			logger.info("初始化容器时，借款类型对应会计信息：{}", InitUtil.fnAccountingMap);

			// 将分公司出纳配置信息 保存在Map缓存中
			BranchEnum[] enums3 = BranchEnum.values();
			for (BranchEnum e : enums3) {
				InitUtil.branchCashierMap.put(e.name(), managerService
						.findBranchCashierByBranchName(e.name()).getCashier());
			}
			logger.info("初始化容器时，分公司出纳配置信息：{}", InitUtil.branchCashierMap);

			// 将分公司会计配置信息 保存在Map缓存中
			for (BranchEnum e : enums3) {
				InitUtil.branchAccountingMap.put(e.name(), managerService
						.findBranchAccountingByBranchName(e.name())
						.getAccounting());
			}
			logger.info("初始化容器时，分公司会计配置信息：{}", InitUtil.branchAccountingMap);

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
			logger.info("初始化容器时，平台部长配置信息：{}", InitUtil.platformMinisterMap);
		} catch (Exception e) {
			logger.error("初始化容器出错：", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("Tomcat容器【关闭】......");
	}

}
