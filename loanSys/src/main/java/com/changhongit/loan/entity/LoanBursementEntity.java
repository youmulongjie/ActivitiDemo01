/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.entity<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <p>
 * 描述：借款费用信息行表Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日下午3:45:23
 */
@Entity
@Table(name = "loan_Loanbursement")
public class LoanBursementEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_Loanbursement_s")
	@SequenceGenerator(name = "loan_Loanbursement_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_Loanbursement_s")
	private Long id;

	/**
	 * 付款方式
	 */
	@Column(name = "PAYMENTMETHOD")
	private String paymentMethod;
	/**
	 * 收款人
	 */
	@Column(name = "PAYEE")
	private String payee;
	/**
	 * 收款银行
	 */
	@Column(name = "CASHBANK")
	private String cashBank;
	/**
	 * 收款银行账户
	 */
	@Column(name = "BANKACCOUNT")
	private String bankAccount;
	/**
	 * 付款用途说明
	 */
	@Column(name = "PAYMENTEXPLAIN")
	private String paymentExplain;
	/**
	 * 借款金额
	 */
	@Column(name = "LOANMONEY")
	private Double loanMoney;
	/**
	 * 备注
	 */
	@Column(name = "REMARKS")
	private String remarks;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the payee
	 */
	public String getPayee() {
		return payee;
	}

	/**
	 * @param payee
	 *            the payee to set
	 */
	public void setPayee(String payee) {
		this.payee = payee;
	}

	/**
	 * @return the cashBank
	 */
	public String getCashBank() {
		return cashBank;
	}

	/**
	 * @param cashBank
	 *            the cashBank to set
	 */
	public void setCashBank(String cashBank) {
		this.cashBank = cashBank;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount
	 *            the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return the paymentExplain
	 */
	public String getPaymentExplain() {
		return paymentExplain;
	}

	/**
	 * @param paymentExplain
	 *            the paymentExplain to set
	 */
	public void setPaymentExplain(String paymentExplain) {
		this.paymentExplain = paymentExplain;
	}

	/**
	 * @return the loanMoney
	 */
	public Double getLoanMoney() {
		return loanMoney;
	}

	/**
	 * @param loanMoney
	 *            the loanMoney to set
	 */
	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
