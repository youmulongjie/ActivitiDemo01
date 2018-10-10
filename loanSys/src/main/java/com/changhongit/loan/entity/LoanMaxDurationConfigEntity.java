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
 * 描述：最长清欠期限配置 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月21日下午1:26:32
 */
@Entity
@Table(name = "loan_maxduration_config")
public class LoanMaxDurationConfigEntity extends BaseEntity implements
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_MaxDuration_Config_s")
	@SequenceGenerator(name = "loan_MaxDuration_Config_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_MaxDuration_Config_s")
	private Long id;

	/**
	 * 借款类型
	 */
	@Column(name = "LOANTYPE")
	private String loanType;

	/**
	 * 还款类型
	 */
	@Column(name = "REPAYMENTTYPE")
	private String repaymentType;

	/**
	 * 最长清欠期限
	 */
	@Column(name = "MAXDURATION")
	private String maxDuration;

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
	 * @return the repaymentType
	 */
	public String getRepaymentType() {
		return repaymentType;
	}

	/**
	 * @param repaymentType
	 *            the repaymentType to set
	 */
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	/**
	 * @return the maxDuration
	 */
	public String getMaxDuration() {
		return maxDuration;
	}

	/**
	 * @param maxDuration
	 *            the maxDuration to set
	 */
	public void setMaxDuration(String maxDuration) {
		this.maxDuration = maxDuration;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanMaxDurationConfigEntity [id=" + id + ", loanType="
				+ loanType + ", repaymentType=" + repaymentType
				+ ", maxDuration=" + maxDuration + "]";
	}

	
}
