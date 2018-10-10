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
 * 描述：分公司出纳配置 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月22日下午4:43:11
 */
@Entity
@Table(name = "loan_BranchCashier")
public class LoanBranchCashierEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_BranchCashier_s")
	@SequenceGenerator(name = "loan_BranchCashier_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_BranchCashier_s")
	private Long id;

	/**
	 * 分公司名称
	 */
	@Column(name = "branch")
	private String branch;

	/**
	 * 出纳名称
	 */
	@Column(name = "cashier")
	private String cashier;

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
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the cashier
	 */
	public String getCashier() {
		return cashier;
	}

	/**
	 * @param cashier
	 *            the cashier to set
	 */
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanBranchCashierEntity [id=" + id + ", branch=" + branch
				+ ", cashier=" + cashier + "]";
	}

}
