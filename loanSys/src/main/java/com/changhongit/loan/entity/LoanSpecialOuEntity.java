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
 * 
 * <p>
 * 描述：专用OU Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月18日上午9:29:01
 */
@Entity
@Table(name = "loan_ou_config")
public class LoanSpecialOuEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_ou_config_s")
	@SequenceGenerator(name = "loan_ou_config_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_ou_config_s")
	private Long id;

	/**
	 * OU简称
	 */
	@Column(name = "ou")
	private String ou;
	/**
	 * ou_id
	 */
	@Column(name = "ou_id")
	private Long ouId;
	/**
	 * 对应借款申请中选择的OU
	 */
	@Column(name = "ou_map")
	private String ouMap;

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
	 * @return the ou
	 */
	public String getOu() {
		return ou;
	}

	/**
	 * @param ou
	 *            the ou to set
	 */
	public void setOu(String ou) {
		this.ou = ou;
	}

	/**
	 * @return the ouId
	 */
	public Long getOuId() {
		return ouId;
	}

	/**
	 * @param ouId
	 *            the ouId to set
	 */
	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	/**
	 * @return the ouMap
	 */
	public String getOuMap() {
		return ouMap;
	}

	/**
	 * @param ouMap
	 *            the ouMap to set
	 */
	public void setOuMap(String ouMap) {
		this.ouMap = ouMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanSpecialOuEntity [id=" + id + ", ou=" + ou + ", ouId="
				+ ouId + ", ouMap=" + ouMap + "]";
	}

}
