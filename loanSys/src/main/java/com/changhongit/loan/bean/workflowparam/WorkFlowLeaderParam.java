/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean.workflowparam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述：审批流程中 领导链参数Bean
 * </p>
 * 
 * <pre>
 * 	类型：业务部门			平台部门
 * 		 业务部			平台组
 * 		事业部			平台部门
 * 		本部				
 * 		业务群			分管平台高管
 * 
 * 
 * </pre>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月8日下午3:51:17
 */
public class WorkFlowLeaderParam implements Serializable {
	/**
	 * 
	 */
	public WorkFlowLeaderParam() {
		leader1 = "";
		leader2 = "";
		leader3 = "";
		leader4 = "";
		leader5 = "";
		fenZong = "";
		zc = "";

		leader3List = new ArrayList<String>();
		leader4List = new ArrayList<String>();
		leader5List = new ArrayList<String>();
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 直接领导
	 */
	private String leader1;
	/**
	 * 2级领导（业务部|平台组）
	 */
	private String leader2;
	/**
	 * 3级领导（事业部|平台部门）
	 */
	private String leader3;
	/**
	 * 3级领导集合（跨部门借款时，可能多人审批）
	 */
	private List<String> leader3List;
	/**
	 * 4级领导（本部）
	 */
	private String leader4;
	/**
	 * 4级领导集合（跨部门借款时，可能多人审批）
	 */
	private List<String> leader4List;
	/**
	 * 5级领导（业务群|分管平台高管）
	 */
	private String leader5;
	/**
	 * 5级领导集合（跨部门借款时，可能多人审批）
	 */
	private List<String> leader5List;

	/**
	 * 分总（分总不在7层中，需单独取）
	 */
	private String fenZong;
	/**
	 * 总裁
	 */
	private String zc;

	/**
	 * @return the leader1
	 */
	public String getLeader1() {
		return leader1;
	}

	/**
	 * @param leader1
	 *            the leader1 to set
	 */
	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}

	/**
	 * @return the leader2
	 */
	public String getLeader2() {
		return leader2;
	}

	/**
	 * @param leader2
	 *            the leader2 to set
	 */
	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}

	/**
	 * @return the leader3
	 */
	public String getLeader3() {
		return leader3;
	}

	/**
	 * @param leader3
	 *            the leader3 to set
	 */
	public void setLeader3(String leader3) {
		this.leader3 = leader3;
	}

	/**
	 * @return the leader4
	 */
	public String getLeader4() {
		return leader4;
	}

	/**
	 * @param leader4
	 *            the leader4 to set
	 */
	public void setLeader4(String leader4) {
		this.leader4 = leader4;
	}

	/**
	 * @return the leader5
	 */
	public String getLeader5() {
		return leader5;
	}

	/**
	 * @param leader5
	 *            the leader5 to set
	 */
	public void setLeader5(String leader5) {
		this.leader5 = leader5;
	}

	/**
	 * @return the zc
	 */
	public String getZc() {
		return zc;
	}

	/**
	 * @param zc
	 *            the zc to set
	 */
	public void setZc(String zc) {
		this.zc = zc;
	}

	/**
	 * @return the fenZong
	 */
	public String getFenZong() {
		return fenZong;
	}

	/**
	 * @param fenZong
	 *            the fenZong to set
	 */
	public void setFenZong(String fenZong) {
		this.fenZong = fenZong;
	}

	/**
	 * @return the leader3List
	 */
	public List<String> getLeader3List() {
		return leader3List;
	}

	/**
	 * @param leader3List
	 *            the leader3List to set
	 */
	public void setLeader3List(List<String> leader3List) {
		this.leader3List = leader3List;
	}

	/**
	 * @return the leader4List
	 */
	public List<String> getLeader4List() {
		return leader4List;
	}

	/**
	 * @param leader4List
	 *            the leader4List to set
	 */
	public void setLeader4List(List<String> leader4List) {
		this.leader4List = leader4List;
	}

	/**
	 * @return the leader5List
	 */
	public List<String> getLeader5List() {
		return leader5List;
	}

	/**
	 * @param leader5List
	 *            the leader5List to set
	 */
	public void setLeader5List(List<String> leader5List) {
		this.leader5List = leader5List;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkFlowLeaderParam [leader1=" + leader1 + ", leader2="
				+ leader2 + ", leader3=" + leader3 + ", leader3List="
				+ leader3List + ", leader4=" + leader4 + ", leader4List="
				+ leader4List + ", leader5=" + leader5 + ", leader5List="
				+ leader5List + ", fenZong=" + fenZong + ", zc=" + zc + "]";
	}
	
	

}
