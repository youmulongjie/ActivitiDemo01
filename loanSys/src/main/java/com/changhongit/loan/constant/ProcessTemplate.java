/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.constant<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.constant;

import java.text.MessageFormat;

/**
 * <p>
 * 描述：流程中标识模板
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月7日下午3:38:49
 */
public abstract class ProcessTemplate {
	/**
	 * 流程业务键模板（例如：LOANSYS_MAIN:35）<br>
	 * 
	 * <pre>
	 * 模板说明：
	 * 1）{0}对应loan_Main的主键属性 mainid（唯一）
	 * </pre>
	 */
	public static final String businesskeyTemplate = "LOANSYS_MAIN:{0}";
	/**
	 * 流程实例模板（例如：王龙杰的借款申请单(35)）<br>
	 * 
	 * <pre>
	 * 模板说明：
	 * 1）{0}对应申请人的中文名称
	 * 2）{1}对应loan_Main的主键属性 mainid（唯一）
	 * </pre>
	 * 
	 */
	public static final String instanceNameTemplate = "{0}的借款申请单({1})";

	/**
	 * 申请单编号模板（例如：JKSQ2018070004）<br>
	 * 
	 * <pre>
	 * 模板说明：
	 * 1）{0}创建申请日期（格式YYYYMM）
	 * 2）{1}loan_main_number_s的序列号（每月自动从1开始）
	 * </pre>
	 */
	public static final String loannumberTemplate = "JKSQ{0}{1}";

	/**
	 * 安全验证 失败模板（例如：该申请单当前处理人是【赵月】，您【Andy.wang】没有操作权限！）<br>
	 * 
	 * <pre>
	 * 模板说明：
	 * 1）{0}申请单当前处理人
	 * 2）{1}登录人中文名称
	 * </pre>
	 */
	private static final String verificationTemplate = "该申请单当前处理人是【{0}】，您【{1}】没有操作权限！";

	/**
	 * 
	 * <p>
	 * 描述：安全验证 失败返回值
	 * </p>
	 * 
	 * @Date 2018年9月10日下午5:33:04 <br>
	 * @param currentApprover
	 *            当前操作人
	 * @param sessionUsername
	 *            登录人姓名
	 * @return
	 */
	public static String verificationTemplate(String currentApprover,
			String sessionUsername) {
		return MessageFormat.format(ProcessTemplate.verificationTemplate,
				currentApprover, sessionUsername);
	}
}
