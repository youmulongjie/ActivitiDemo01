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
 * 描述：审批流程中 申请人信息参数Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月8日下午5:18:54
 */
public class WorkFlowApplyerParam implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 中文名
	 */
	private String userName;
	/**
	 * erp登录名
	 */
	private String erpName;
	/**
	 * 员工编号
	 */
	private String erpNum;
	/**
	 * 部门
	 */
	private String dept;
	/**
	 * 职位
	 */
	private String position;

	/**
	 * 分公司员工|北京员工
	 */
	private boolean beijing;

	/**
	 * 申请人分配编号（查询领导链）
	 */
	private String assignmentNumber;
	/**
	 * 申请人所在的领导链层级
	 */
	private String leaderIn;

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
	 * @return the erpName
	 */
	public String getErpName() {
		return erpName;
	}

	/**
	 * @param erpName
	 *            the erpName to set
	 */
	public void setErpName(String erpName) {
		this.erpName = erpName;
	}

	/**
	 * @return the erpNum
	 */
	public String getErpNum() {
		return erpNum;
	}

	/**
	 * @param erpNum
	 *            the erpNum to set
	 */
	public void setErpNum(String erpNum) {
		this.erpNum = erpNum;
	}

	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the assignmentNumber
	 */
	public String getAssignmentNumber() {
		return assignmentNumber;
	}

	/**
	 * @param assignmentNumber
	 *            the assignmentNumber to set
	 */
	public void setAssignmentNumber(String assignmentNumber) {
		this.assignmentNumber = assignmentNumber;
	}

	/**
	 * @return the leaderIn
	 */
	public String getLeaderIn() {
		return leaderIn;
	}

	/**
	 * @param leaderIn
	 *            the leaderIn to set
	 */
	public void setLeaderIn(String leaderIn) {
		this.leaderIn = leaderIn;
	}

	/**
	 * @return the beijing
	 */
	public boolean isBeijing() {
		return beijing;
	}

	/**
	 * @param beijing
	 *            the beijing to set
	 */
	public void setBeijing(boolean beijing) {
		this.beijing = beijing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkFlowApplyerParam [userName=" + userName + ", erpName="
				+ erpName + ", erpNum=" + erpNum + ", dept=" + dept
				+ ", position=" + position + ", beijing=" + beijing
				+ ", assignmentNumber=" + assignmentNumber + ", leaderIn="
				+ leaderIn + "]";
	}

}
