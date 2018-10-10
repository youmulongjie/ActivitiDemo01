/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean.workflowparam<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean.workflowparam;

import java.io.Serializable;

/**
 * <p>
 * 描述：审批流程中 相关人员（财务、法务、固定人员等）参数Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月14日上午10:48:24
 */
public class WorkFlowRelatedParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 财务会计
	 */
	private String accounting;

	/**
	 * 分公司 财务会计
	 */
	private String branchAccounting;

	/**
	 * 出纳
	 */
	private String cashier;

	/**
	 * 财务复核
	 */
	private String check;

	/**
	 * 财务部长
	 */
	private String financeMinister;

	/**
	 * 财务总监
	 */
	private String cfo;

	/**
	 * 分公司名称
	 */
	private String branch;
	/**
	 * 平台高管
	 */
	private String platformExecutives;
	/**
	 * 法务部部长
	 */
	private String lawMinister;
	/**
	 * 平台名称
	 */
	private String platformName;
	/**
	 * 主责平台部长
	 */
	private String mainPlatformMinister;
	/**
	 * 总裁
	 */
	private String ceo;

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

	/**
	 * @return the branchAccounting
	 */
	public String getBranchAccounting() {
		return branchAccounting;
	}

	/**
	 * @param branchAccounting
	 *            the branchAccounting to set
	 */
	public void setBranchAccounting(String branchAccounting) {
		this.branchAccounting = branchAccounting;
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

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}

	/**
	 * @return the cfo
	 */
	public String getCfo() {
		return cfo;
	}

	/**
	 * @param cfo
	 *            the cfo to set
	 */
	public void setCfo(String cfo) {
		this.cfo = cfo;
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
	 * @return the platformExecutives
	 */
	public String getPlatformExecutives() {
		return platformExecutives;
	}

	/**
	 * @param platformExecutives
	 *            the platformExecutives to set
	 */
	public void setPlatformExecutives(String platformExecutives) {
		this.platformExecutives = platformExecutives;
	}

	/**
	 * @return the lawMinister
	 */
	public String getLawMinister() {
		return lawMinister;
	}

	/**
	 * @param lawMinister
	 *            the lawMinister to set
	 */
	public void setLawMinister(String lawMinister) {
		this.lawMinister = lawMinister;
	}

	/**
	 * @return the financeMinister
	 */
	public String getFinanceMinister() {
		return financeMinister;
	}

	/**
	 * @param financeMinister
	 *            the financeMinister to set
	 */
	public void setFinanceMinister(String financeMinister) {
		this.financeMinister = financeMinister;
	}

	/**
	 * @return the mainPlatformMinister
	 */
	public String getMainPlatformMinister() {
		return mainPlatformMinister;
	}

	/**
	 * @param mainPlatformMinister
	 *            the mainPlatformMinister to set
	 */
	public void setMainPlatformMinister(String mainPlatformMinister) {
		this.mainPlatformMinister = mainPlatformMinister;
	}

	/**
	 * @return the ceo
	 */
	public String getCeo() {
		return ceo;
	}

	/**
	 * @param ceo
	 *            the ceo to set
	 */
	public void setCeo(String ceo) {
		this.ceo = ceo;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkFlowRelatedParam [会计=" + accounting + ", 分公司名称=" + branch
				+ ", 分公司会计=" + branchAccounting + ", 出纳=" + cashier + ", 财务复核="
				+ check + ", 财务部长=" + financeMinister + ", 财务总监=" + cfo
				+ ", 平台高管=" + platformExecutives + ", 法务部部长=" + lawMinister
				+ ", 平台部门=" + platformName + ", 主责平台部长=" + mainPlatformMinister
				+ ", 总裁=" + ceo + "]";
	}
}
