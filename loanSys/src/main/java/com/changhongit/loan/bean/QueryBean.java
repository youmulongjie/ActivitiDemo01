/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean;

/**
 * <p>
 * 描述：查询Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月12日下午1:55:46
 */
public class QueryBean {
	/**
	 * 流程标题
	 */
	private String title;
	/**
	 * 申请人
	 */
	private String person;
	/**
	 * 当前审批环节
	 */
	private String currentWork;
	/**
	 * 当前审批人
	 */
	private String currentApprover;

	/**
	 * 查询开始时间
	 */
	private String beginDate;
	/**
	 * 查询结束时间
	 */
	private String endDate;
	/**
	 * 借款类型
	 */
	private String loantype;
	/**
	 * 状态
	 */
	private String procStatus;
	/**
	 * 申请单标号
	 */
	private String loanNumber;
	/**
	 * 凭证号
	 */
	private String pzNumber;
	/**
	 * 发票创建状态（（1：成功；0：失败））
	 */
	private String invoiceStatus;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the person
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * @return the currentWork
	 */
	public String getCurrentWork() {
		return currentWork;
	}

	/**
	 * @param currentWork
	 *            the currentWork to set
	 */
	public void setCurrentWork(String currentWork) {
		this.currentWork = currentWork;
	}

	/**
	 * @return the currentApprover
	 */
	public String getCurrentApprover() {
		return currentApprover;
	}

	/**
	 * @param currentApprover
	 *            the currentApprover to set
	 */
	public void setCurrentApprover(String currentApprover) {
		this.currentApprover = currentApprover;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the loantype
	 */
	public String getLoantype() {
		return loantype;
	}

	/**
	 * @param loantype
	 *            the loantype to set
	 */
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	/**
	 * @return the procStatus
	 */
	public String getProcStatus() {
		return procStatus;
	}

	/**
	 * @param procStatus
	 *            the procStatus to set
	 */
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	/**
	 * @return the loanNumber
	 */
	public String getLoanNumber() {
		return loanNumber;
	}

	/**
	 * @param loanNumber
	 *            the loanNumber to set
	 */
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	/**
	 * @return the pzNumber
	 */
	public String getPzNumber() {
		return pzNumber;
	}

	/**
	 * @param pzNumber
	 *            the pzNumber to set
	 */
	public void setPzNumber(String pzNumber) {
		this.pzNumber = pzNumber;
	}

	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

}
