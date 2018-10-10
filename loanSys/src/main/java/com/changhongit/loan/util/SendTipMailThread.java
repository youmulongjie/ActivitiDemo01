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
 * 描述：发送审批提醒邮件 线程类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年8月17日下午2:03:18
 */
public class SendTipMailThread extends Thread {
	private String assignee;
	private LoanMainEntity loanMainEntity;

	/**
	 * @param assignee
	 * @param loanMainEntity
	 */
	public SendTipMailThread(String assignee, LoanMainEntity loanMainEntity) {
		super();
		this.assignee = assignee;
		this.loanMainEntity = loanMainEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			RemindMailTools.sendTipMail(assignee, loanMainEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
