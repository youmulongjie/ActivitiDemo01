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
 * 描述：发送驳回|补正提醒邮件 线程类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年8月17日下午2:18:10
 */
public class SendRejectMailThread extends Thread {
	/**
	 * 邮件人
	 */
	private String assignee;
	private LoanMainEntity loanMainEntity;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 操作：（驳回|补正）
	 */
	private String operator;
	/**
	 * 操作人
	 */
	private String operatorName;

	/**
	 * @param assignee
	 * @param loanMainEntity
	 */
	public SendRejectMailThread(String assignee, LoanMainEntity loanMainEntity,
			String reason, String operator, String operatorName) {
		super();
		this.assignee = assignee;
		this.loanMainEntity = loanMainEntity;
		this.reason = reason;
		this.operator = operator;
		this.operatorName = operatorName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			RemindMailTools.sendRejectMail11(assignee, loanMainEntity, reason,
					operator, operatorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
