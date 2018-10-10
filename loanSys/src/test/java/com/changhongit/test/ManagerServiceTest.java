/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.test<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changhongit.bean.OperatingUnitsDataBean;
import com.changhongit.loan.bean.Page;
import com.changhongit.loan.entity.LoanSpecialOuEntity;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午2:00:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ManagerServiceTest {
	@Autowired
	private ManagerService managerService;

	// 测试分页
	@Test
	public void queryLoanSpecialOuEntitys() {
		int currentPage = 1;
		Page<LoanSpecialOuEntity> page = managerService.pageEntitys(
				LoanSpecialOuEntity.class, currentPage);
		System.out.println("共多少条记录：" + page.getTotalCount());
		System.out.println("共多少页记录：" + page.getTotalPage());
		System.out.println("当前页：" + (page.getCurrentPage()));
		List<LoanSpecialOuEntity> list = page.getList();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getId());
		}
	}

	// 初始化 借款专用OU配置
	@Test
	public void initOuList() throws Exception {
		String username = "SYSTEM";
		List<OperatingUnitsDataBean> list = InitUtil.getOulist();
		for (OperatingUnitsDataBean bean : list) {
			managerService.saveLoanSpecialOuEntity(bean.getOuName(), username);
		}
	}
}
