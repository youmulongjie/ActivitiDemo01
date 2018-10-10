/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean.workflowparam<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean.workflowparam;

import java.io.Serializable;

/**
 * <p>
 * 描述：审批流程中 业务参数Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月8日下午5:25:31
 */
public class WorkFlowBusinessParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 借款类型
	 */
	private String loanType;
	/**
	 * 是否本部门借款（是：true；否：false）
	 */
	private boolean borrowingFlag;

	/**
	 * 是否有合同或预算（是：true；否：false）
	 */
	private boolean contractOrBudgetFlag;

	/**
	 * 借款总金额
	 */
	private double loanMoney;

	/**
	 * 代替借款所属部门（获取该部门领导链使用）
	 */
	private String replaceDept;

	/**
	 * @return the loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType
	 *            the loanType to set
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return the borrowingFlag
	 */
	public boolean isBorrowingFlag() {
		return borrowingFlag;
	}

	/**
	 * @param borrowingFlag
	 *            the borrowingFlag to set
	 */
	public void setBorrowingFlag(boolean borrowingFlag) {
		this.borrowingFlag = borrowingFlag;
	}

	/**
	 * @return the loanMoney
	 */
	public double getLoanMoney() {
		return loanMoney;
	}

	/**
	 * @param loanMoney
	 *            the loanMoney to set
	 */
	public void setLoanMoney(double loanMoney) {
		this.loanMoney = loanMoney;
	}

	/**
	 * @return the contractOrBudgetFlag
	 */
	public boolean isContractOrBudgetFlag() {
		return contractOrBudgetFlag;
	}

	/**
	 * @param contractOrBudgetFlag
	 *            the contractOrBudgetFlag to set
	 */
	public void setContractOrBudgetFlag(boolean contractOrBudgetFlag) {
		this.contractOrBudgetFlag = contractOrBudgetFlag;
	}

	/**
	 * @return the replaceDept
	 */
	public String getReplaceDept() {
		return replaceDept;
	}

	/**
	 * @param replaceDept
	 *            the replaceDept to set
	 */
	public void setReplaceDept(String replaceDept) {
		this.replaceDept = replaceDept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkFlowBusinessParam [loanType=" + loanType
				+ ", borrowingFlag=" + borrowingFlag
				+ ", contractOrBudgetFlag=" + contractOrBudgetFlag
				+ ", loanMoney=" + loanMoney + ", replaceDept=" + replaceDept
				+ "]";
	}

}
