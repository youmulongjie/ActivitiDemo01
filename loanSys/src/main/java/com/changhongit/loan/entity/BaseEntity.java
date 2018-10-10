/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.entity<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 描述：Entity 的基类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:39:14
 */
@MappedSuperclass
public abstract class BaseEntity {
	/**
	 * 状态（1：有效；0：无效）
	 */
	@Column(name = "status")
	protected Integer status = 1;

	// update时不更新
	@Column(name = "create_by", updatable = false)
	protected String createBy;

	// update时不更新
	@Column(name = "create_date", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	protected Date createDate = new Date();

	// insert时不插入
	@Column(name = "lastupdate_By", insertable = false)
	protected String lastupdateBy;

	// insert时不插入
	@Column(name = "lastupdate_Date", insertable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	protected Date lastupdateDate = new Date();

	@Column(name = "attribute1")
	protected String attribute1;

	@Column(name = "attribute2")
	protected String attribute2;

	@Column(name = "attribute3")
	protected String attribute3;

	@Column(name = "attribute4")
	protected String attribute4;

	@Column(name = "attribute5")
	protected String attribute5;

	@Column(name = "number_attribute1")
	protected Long numberAttribute1;

	@Column(name = "number_attribute2")
	protected Long numberAttribute2;

	@Column(name = "number_attribute3")
	protected Long numberAttribute3;

	@Column(name = "number_attribute4")
	protected Long numberAttribute4;

	@Column(name = "number_attribute5")
	protected Long numberAttribute5;

	/**
	 * @return the attribute1
	 */
	public String getAttribute1() {
		return attribute1;
	}

	/**
	 * @param attribute1
	 *            the attribute1 to set
	 */
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	/**
	 * @return the attribute2
	 */
	public String getAttribute2() {
		return attribute2;
	}

	/**
	 * @param attribute2
	 *            the attribute2 to set
	 */
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	/**
	 * @return the attribute3
	 */
	public String getAttribute3() {
		return attribute3;
	}

	/**
	 * @param attribute3
	 *            the attribute3 to set
	 */
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	/**
	 * @return the attribute4
	 */
	public String getAttribute4() {
		return attribute4;
	}

	/**
	 * @param attribute4
	 *            the attribute4 to set
	 */
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	/**
	 * @return the attribute5
	 */
	public String getAttribute5() {
		return attribute5;
	}

	/**
	 * @param attribute5
	 *            the attribute5 to set
	 */
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	/**
	 * @return the numberAttribute1
	 */
	public Long getNumberAttribute1() {
		return numberAttribute1;
	}

	/**
	 * @param numberAttribute1
	 *            the numberAttribute1 to set
	 */
	public void setNumberAttribute1(Long numberAttribute1) {
		this.numberAttribute1 = numberAttribute1;
	}

	/**
	 * @return the numberAttribute2
	 */
	public Long getNumberAttribute2() {
		return numberAttribute2;
	}

	/**
	 * @param numberAttribute2
	 *            the numberAttribute2 to set
	 */
	public void setNumberAttribute2(Long numberAttribute2) {
		this.numberAttribute2 = numberAttribute2;
	}

	/**
	 * @return the numberAttribute3
	 */
	public Long getNumberAttribute3() {
		return numberAttribute3;
	}

	/**
	 * @param numberAttribute3
	 *            the numberAttribute3 to set
	 */
	public void setNumberAttribute3(Long numberAttribute3) {
		this.numberAttribute3 = numberAttribute3;
	}

	/**
	 * @return the numberAttribute4
	 */
	public Long getNumberAttribute4() {
		return numberAttribute4;
	}

	/**
	 * @param numberAttribute4
	 *            the numberAttribute4 to set
	 */
	public void setNumberAttribute4(Long numberAttribute4) {
		this.numberAttribute4 = numberAttribute4;
	}

	/**
	 * @return the numberAttribute5
	 */
	public Long getNumberAttribute5() {
		return numberAttribute5;
	}

	/**
	 * @param numberAttribute5
	 *            the numberAttribute5 to set
	 */
	public void setNumberAttribute5(Long numberAttribute5) {
		this.numberAttribute5 = numberAttribute5;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastupdateBy
	 */
	public String getLastupdateBy() {
		return lastupdateBy;
	}

	/**
	 * @param lastupdateBy
	 *            the lastupdateBy to set
	 */
	public void setLastupdateBy(String lastupdateBy) {
		this.lastupdateBy = lastupdateBy;
	}

	/**
	 * @return the lastupdateDate
	 */
	public Date getLastupdateDate() {
		return lastupdateDate;
	}

	/**
	 * @param lastupdateDate
	 *            the lastupdateDate to set
	 */
	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}

}
