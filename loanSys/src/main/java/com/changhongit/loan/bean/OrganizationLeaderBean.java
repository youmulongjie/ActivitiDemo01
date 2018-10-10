/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean;

/**
 * <p>
 * 描述：部门领导 Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年7月12日下午5:05:50
 */
public class OrganizationLeaderBean {
	/**
	 * @param emailAddress
	 * @param lastName
	 * @param userName
	 * @param positionName
	 */
	public OrganizationLeaderBean(String emailAddress, String lastName,
			String userName, String positionName) {
		super();
		this.emailAddress = emailAddress;
		this.lastName = lastName;
		this.userName = userName;
		this.positionName = positionName;
	}

	/**
	 * 
	 */
	public OrganizationLeaderBean() {
		super();
	}

	/**
	 * 邮箱地址
	 */
	private String emailAddress;
	/**
	 * 中文名称
	 */
	private String lastName;
	/**
	 * ERP 名称
	 */
	private String userName;
	/**
	 * 职位
	 */
	private String positionName;
	/**
	 * 组织类型（事业部|本部|业务群）
	 */
	private String organizationTypeName;

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName
	 *            the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrganizationLeaderBean [emailAddress=" + emailAddress
				+ ", lastName=" + lastName + ", userName=" + userName
				+ ", positionName=" + positionName + ", organizationTypeName="
				+ organizationTypeName + "]";
	}

}
