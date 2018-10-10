/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.entity<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 描述：组织库Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:31:35
 */
@Entity
@Table(name = "loan_organization")
public class LoanOrganizationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 组织名称
	 */
	@Id
	@Column(name = "Organization_Name")
	private String organizationName;

	/**
	 * 组织ID
	 */
	@Column(name = "organization_Id")
	private Long organizationId;
	/**
	 * 组织类型
	 */
	@Column(name = "organization_Type")
	private String organizationType;
	/**
	 * 组织类型名称
	 */
	@Column(name = "organization_Type_Name")
	private String organizationTypeName;
	/**
	 * 
	 */
	@Column(name = "location_Id")
	private Long locationId;
	/**
	 * 开始日期
	 */
	@Column(name = "date_From")
	private Date dateFrom;
	/**
	 * 结束日期
	 */
	@Column(name = "date_To")
	private Date dateTo;
	/**
	 * 创建时间
	 */
	@Column(name = "create_Date")
	private Date createDate;
	/**
	 * 创建者
	 */
	@Column(name = "create_By")
	private String createBy;

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName
	 *            the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the organizationType
	 */
	public String getOrganizationType() {
		return organizationType;
	}

	/**
	 * @param organizationType
	 *            the organizationType to set
	 */
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	/**
	 * @return the organizationTypeName
	 */
	public String getOrganizationTypeName() {
		return organizationTypeName;
	}

	/**
	 * @param organizationTypeName
	 *            the organizationTypeName to set
	 */
	public void setOrganizationTypeName(String organizationTypeName) {
		this.organizationTypeName = organizationTypeName;
	}

	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom
	 *            the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo
	 *            the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanOrganizationEntity [organizationName=" + organizationName
				+ ", organizationId=" + organizationId + ", organizationType="
				+ organizationType + ", organizationTypeName="
				+ organizationTypeName + "]";
	}
	

}
