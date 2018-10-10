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

import com.changhongit.loan.bean.ConfigBean;
import com.changhongit.loan.entity.LoanOrganizationEntity;
import com.changhongit.loan.service.LoanOrganizationService;

/**
 * <p>
 * 描述：初始化 测试类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:54:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class InitTest {

	@Autowired
	private LoanOrganizationService loanOrganizationService;
	
	@Autowired
	private ConfigBean configBean;
	
	@Test
	public void test(){
		System.out.println(configBean.isProfie());
		System.out.println(configBean.getTestEmailReceive());
	}

	// 初始化 库存组织 数据
	@Test
	public void saveLoanOrganizationEntitys() {
		loanOrganizationService.initLoanOrganizationEntitys();
	}

	// 查找 所有组织信息
	@Test
	public void getCuxHrOrganizationRecList() {
		List<LoanOrganizationEntity> list = loanOrganizationService
				.getAllLoanOrganizationEntitys();
		System.out.println(list.size());
		for (LoanOrganizationEntity entity : list) {
			System.out.println(entity.getOrganizationName() + ":"
					+ entity.getOrganizationId());
		}
	}
}
