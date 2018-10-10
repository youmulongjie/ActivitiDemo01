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
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年9月10日上午11:35:31
 */
public enum InvoiceStatusEnum {
	发票创建成功(1), 发票创建失败(2);
	
	private int value;

	/**
	 * @param value
	 */
	private InvoiceStatusEnum(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
