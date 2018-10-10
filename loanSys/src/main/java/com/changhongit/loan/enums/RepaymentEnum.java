/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.enums<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.enums;

/**
 * <p>
 * 描述：还款类型 枚举类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月8日下午1:04:58
 */
public enum RepaymentEnum {
	GenerateInvoices("生成发票日期加N天"), LastDayOfYear("提交日期当年最后一天"), SpecifiedDate(
			"指定日期加N天");

	private String type;

	/**
	 * @param type
	 */
	private RepaymentEnum(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
