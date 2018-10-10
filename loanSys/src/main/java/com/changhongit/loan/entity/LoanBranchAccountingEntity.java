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
 * 描述：分公司会计配置 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年7月5日下午4:43:11
 */
@Entity
@Table(name = "loan_branchaccounting")
public class LoanBranchAccountingEntity extends BaseEntity implements
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_BRANCHACCOUNTING_S")
	@SequenceGenerator(name = "LOAN_BRANCHACCOUNTING_S", initialValue = 1, allocationSize = 1, sequenceName = "LOAN_BRANCHACCOUNTING_S")
	private Long id;

	/**
	 * 分公司名称
	 */
	@Column(name = "branch")
	private String branch;

	/**
	 * 会计名称
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
		return "LoanBranchAccountingEntity [id=" + id + ", branch=" + branch
				+ ", accounting=" + accounting + "]";
	}

}
