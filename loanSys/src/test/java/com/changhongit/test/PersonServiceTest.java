/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.test<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.util.HrInfoUtil;

/**
 * <p>
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月5日下午1:35:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PersonServiceTest {
	@Autowired
	private LoanMainService loanMainService;

	/*
	 * @Test public void saveLoanMainEntity() { LoanMainEntity loanMainEntity =
	 * new LoanMainEntity(); loanMainEntity.setPerson("Andy");
	 * 
	 * List<LoanBursementEntity> bursementEntities = new
	 * ArrayList<LoanBursementEntity>(); LoanBursementEntity entity = new
	 * LoanBursementEntity(); entity.setPaymentMethod("test");
	 * entity.setLoanMoney(300d); bursementEntities.add(entity);
	 * loanMainEntity.setBursementEntities(bursementEntities);
	 * 
	 * loanMainService.save(loanMainEntity); }
	 */

	@Test
	public void applyNumber() {
		String applyNumber = loanMainService.applyNumber();
		System.out.println(applyNumber);
	}

	// 测试生成发票
	@Test
	public void createInvoice() {
		LoanMainEntity entity = loanMainService.getEntityById(
				LoanMainEntity.class, -100L);
		Message message = null;

		try {
			message = HrInfoUtil.createInvoice(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(message);
	}

	// 创建发票失败的 
	@Test
	public void getFailureInvoiceList() {
		List<BigDecimal> list = loanMainService.getFailureInvoiceList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).longValue() + "; " + list.get(i).getClass());
		}
	}

}
