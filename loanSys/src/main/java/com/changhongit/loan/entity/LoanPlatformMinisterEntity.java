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
 * 描述：平台部长配置 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年7月19日下午1:43:11
 */
@Entity
@Table(name = "loan_platformminister")
public class LoanPlatformMinisterEntity extends BaseEntity implements
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_PLATFORMMINISTER_S")
	@SequenceGenerator(name = "LOAN_PLATFORMMINISTER_S", initialValue = 1, allocationSize = 1, sequenceName = "LOAN_PLATFORMMINISTER_S")
	private Long id;

	/**
	 * 平台部门名称
	 */
	@Column(name = "PLATFORM_NAME")
	private String platformName;

	/**
	 * 平台部长名称
	 */
	@Column(name = "MINISTER_NAME")
	private String ministerName;

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
	 * @return the platformName
	 */
	public String getPlatformName() {
		return platformName;
	}

	/**
	 * @param platformName
	 *            the platformName to set
	 */
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	/**
	 * @return the ministerName
	 */
	public String getMinisterName() {
		return ministerName;
	}

	/**
	 * @param ministerName
	 *            the ministerName to set
	 */
	public void setMinisterName(String ministerName) {
		this.ministerName = ministerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanPlatformMinisterEntity [id=" + id + ", platformName="
				+ platformName + ", ministerName=" + ministerName + "]";
	}

}
