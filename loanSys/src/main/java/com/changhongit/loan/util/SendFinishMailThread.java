/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import com.changhongit.loan.entity.LoanMainEntity;

/**
 * <p>
 * 描述：发送审批完成邮件 线程类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年8月23日下午3:10:07
 */
public class SendFinishMailThread extends Thread {
	private String applyer;
	private LoanMainEntity loanMainEntity;

	/**
	 * @param assignee
	 * @param loanMainEntity
	 */
	public SendFinishMailThread(String applyer, LoanMainEntity loanMainEntity) {
		super();
		this.applyer = applyer;
		this.loanMainEntity = loanMainEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			RemindMailTools.sendFinishMail(applyer, loanMainEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
