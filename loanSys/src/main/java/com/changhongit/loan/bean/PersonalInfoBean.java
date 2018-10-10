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
 * 描述：员工个人信息 Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午1:15:35
 */
public class PersonalInfoBean {
	/**
	 * 上级部门名称
	 */
	private String upDept;
	/**
	 * 部门名称
	 */
	private String dept;
	/**
	 * 分配编号
	 */
	private String assignNum;

	/**
	 * @return the upDept
	 */
	public String getUpDept() {
		return upDept;
	}

	/**
	 * @param upDept
	 *            the upDept to set
	 */
	public void setUpDept(String upDept) {
		this.upDept = upDept;
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
	 * @return the assignNum
	 */
	public String getAssignNum() {
		return assignNum;
	}

	/**
	 * @param assignNum
	 *            the assignNum to set
	 */
	public void setAssignNum(String assignNum) {
		this.assignNum = assignNum;
	}

}
