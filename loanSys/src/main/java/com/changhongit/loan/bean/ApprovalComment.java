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
 * 描述：审批流程中 评论表
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月12日上午9:53:20
 */
public class ApprovalComment {
	/**
	 * 审批人
	 */
	private String approvor;

	/**
	 * 处理环节
	 */
	private String taskName;
	/**
	 * 处理时间
	 */
	private String approvorDate;

	/**
	 * 审批状态
	 */
	private String status;

	/**
	 * 审批意见
	 */
	private String desc;

	/**
	 * @return the approvor
	 */
	public String getApprovor() {
		return approvor;
	}

	/**
	 * @param approvor
	 *            the approvor to set
	 */
	public void setApprovor(String approvor) {
		this.approvor = approvor;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the approvorDate
	 */
	public String getApprovorDate() {
		return approvorDate;
	}

	/**
	 * @param approvorDate
	 *            the approvorDate to set
	 */
	public void setApprovorDate(String approvorDate) {
		this.approvorDate = approvorDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApprovalComment [approvor=" + approvor + ", taskName="
				+ taskName + ", approvorDate=" + approvorDate + ", status="
				+ status + ", desc=" + desc + "]";
	}

}
