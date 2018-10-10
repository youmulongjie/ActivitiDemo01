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
 * 描述：借款单对应发票类型 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月22日上午11:16:18
 */
@Entity
@Table(name = "loan_LoanToInvoice")
public class LoanToInvoiceEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_LoanToInvoice_s")
	@SequenceGenerator(name = "loan_LoanToInvoice_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_LoanToInvoice_s")
	private Long id;

	/**
	 * 借款类型
	 */
	@Column(name = "loanType")
	private String loanType;

	/**
	 * 发票类型
	 */
	@Column(name = "accounting")
	private String accounting;

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
	 * @return the accounting
	 */
	public String getAccounting() {
		return accounting;
	}

	/**
	 * @param accounting
	 *            the accounting to set
	 */
	public void setAccounting(String accounting) {
		this.accounting = accounting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanToInvoiceEntity [id=" + id + ", loanType=" + loanType
				+ ", accounting=" + accounting + "]";
	}

}
