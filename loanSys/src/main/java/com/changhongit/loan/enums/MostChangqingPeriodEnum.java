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
 * 描述：最长清欠期限 枚举类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月8日下午1:08:54
 */
public enum MostChangqingPeriodEnum {
	三十(30), 九十(90), 一百八(180), 三百六(360);

	private int period;

	/**
	 * @param period
	 */
	private MostChangqingPeriodEnum(int period) {
		this.period = period;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

}
