/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.constant<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 描述：常量定义类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日下午2:08:34
 */
public abstract class IConstant {
	/**
	 * 用户Session标识
	 */
	public static final String SESSION_USER = "session_user";

	/**
	 * 分页每页默认10条
	 */
	public static final int INIT_PAGESIZE = 10;

	/**********************************************************************/
	/**
	 * 组织类型 业务部门
	 */
	public static final String ORGTYPENAME_BUSINESS_GROUP = "业务";
	/**
	 * 组织类型 业务部门--业务部
	 */
	public static final String business_level2 = "业务部";
	/**
	 * 组织类型 业务部门--事业部
	 */
	public static final String business_level3 = "事业部";
	/**
	 * 组织类型 业务部门--本部
	 */
	public static final String business_level4 = "本部";
	/**
	 * 组织类型 业务部门--业务群
	 */
	public static final String business_level5 = "业务群";

	/**
	 * 组织类型 平台部门
	 */
	public static final String ORGTYPENAME_PLATFORM_GROUP = "平台";
	/**
	 * 组织类型 平台部门--平台组
	 */
	public static final String platform_level2 = "平台组";
	/**
	 * 组织类型 平台部门--平台部门
	 */
	public static final String platform_level3 = "平台部门";
	/**
	 * 组织类型 平台部门--分管平台高管
	 */
	public static final String platform_level5 = "分管平台高管";

	/**
	 * 平台部门的 领导所在的组织名称
	 */
	public final static Set<String> platformPositionSet;

	/**
	 * 业务部门的 领导所在的组织名称
	 */
	public final static Set<String> businessPositionSet;

	static {
		// 初始化 平台部门领导所在的 组织类型名称
		platformPositionSet = new HashSet<String>();
		platformPositionSet.add(platform_level2);
		platformPositionSet.add(platform_level3);
		platformPositionSet.add(platform_level5);

		// 初始化 业务部门领导所在的 组织类型名称
		businessPositionSet = new HashSet<String>();
		businessPositionSet.add(business_level2);
		businessPositionSet.add(business_level3);
		businessPositionSet.add(business_level4);
		businessPositionSet.add(business_level5);
	}

	/**********************************************************************/

	/**
	 * 填写申请单 环节名称
	 */
	public static final String CREATE_APPLY_LINK = "填写申请单";
	/**
	 * 财务会计 环节名称
	 */
	public static final String FN_ACCOUNTING_LINK = "财务会计";
	/**
	 * 转分公司会计 环节名称
	 */
	public static final String FN_BRANCH_ACCOUNTING_LINK = "转分公司会计";
	/**
	 * 流程结束标识
	 */
	public static final String END_LINK = "End";

}
