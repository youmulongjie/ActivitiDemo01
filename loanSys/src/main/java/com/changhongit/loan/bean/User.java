package com.changhongit.loan.bean;

/**
 * 
 * <p>
 * 描述：用户类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日下午4:57:20
 */
public class User {
	/**
	 * @param erpNum
	 * @param username
	 * @param erpName
	 * @param worksite
	 * @param administrator
	 * @param fnAccounting
	 * @param cashier
	 * @param company
	 * @param ou
	 * @param ouFullName
	 * @param usernameJC
	 */
	public User(String erpNum, String username, String erpName,
			String worksite, boolean administrator, boolean fnAccounting,
			boolean cashier, String company, String ou, String ouFullName,
			String usernameJC) {
		super();
		this.erpNum = erpNum;
		this.username = username;
		this.erpName = erpName;
		this.worksite = worksite;
		this.administrator = administrator;
		this.fnAccounting = fnAccounting;
		this.cashier = cashier;
		this.company = company;
		this.ou = ou;
		this.ouFullName = ouFullName;
		this.usernameJC = usernameJC;
	}

	/**
	 * 员工编号
	 */
	private String erpNum;
	/**
	 * 中文名
	 */
	private String username;
	/**
	 * erp登录名
	 */
	private String erpName;
	/**
	 * 实际工作地
	 */
	private String worksite;

	/**
	 * 是否是管理员
	 */
	private boolean administrator;
	/**
	 * 是否是财务会计
	 */
	private boolean fnAccounting;
	/**
	 * 是否是 出纳
	 */
	private boolean cashier;

	/** 分公司员工|北京员工 */
	private String company;
	/**
	 * 员工所在OU
	 */
	private String ou;
	/**
	 * 员工所在OU全称
	 */
	private String ouFullName;

	/**
	 * 中文名简称
	 */
	private String usernameJC;

	/**
	 * @return the ouFullName
	 */
	public String getOuFullName() {
		return ouFullName;
	}

	/**
	 * @param ouFullName
	 *            the ouFullName to set
	 */
	public void setOuFullName(String ouFullName) {
		this.ouFullName = ouFullName;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
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

	public User() {
		super();
	}

	public User(String erpName, String erpNum) {
		this.erpNum = erpNum;
		this.erpName = erpName;
	}

	/**
	 * @return the administrator
	 */
	public boolean isAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator
	 *            the administrator to set
	 */
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public String getWorksite() {
		return worksite;
	}

	public void setWorksite(String worksite) {
		this.worksite = worksite;
	}

	public String getErpNum() {
		return erpNum;
	}

	public void setErpNum(String erpNum) {
		this.erpNum = erpNum;
	}

	public String getErpName() {
		return erpName;
	}

	public void setErpName(String erpName) {
		this.erpName = erpName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the usernameJC
	 */
	public String getUsernameJC() {
		return usernameJC;
	}

	/**
	 * @param usernameJC
	 *            the usernameJC to set
	 */
	public void setUsernameJC(String usernameJC) {
		this.usernameJC = usernameJC;
	}

	/**
	 * @return the fnAccounting
	 */
	public boolean isFnAccounting() {
		return fnAccounting;
	}

	/**
	 * @param fnAccounting
	 *            the fnAccounting to set
	 */
	public void setFnAccounting(boolean fnAccounting) {
		this.fnAccounting = fnAccounting;
	}

	/**
	 * @return the cashier
	 */
	public boolean isCashier() {
		return cashier;
	}

	/**
	 * @param cashier
	 *            the cashier to set
	 */
	public void setCashier(boolean cashier) {
		this.cashier = cashier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [erpNum=" + erpNum + ", username=" + username
				+ ", erpName=" + erpName + ", worksite=" + worksite
				+ ", administrator=" + administrator + ", fnAccounting="
				+ fnAccounting + ", cashier=" + cashier + ", company="
				+ company + ", ou=" + ou + ", ouFullName=" + ouFullName
				+ ", usernameJC=" + usernameJC + "]";
	}

}
