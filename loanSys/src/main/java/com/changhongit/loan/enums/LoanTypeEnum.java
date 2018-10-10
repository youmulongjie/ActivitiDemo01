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
 * 描述：借款类型 枚举类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月8日下午12:55:30
 */
public enum LoanTypeEnum {

	Epicyclic("周转备用金"), DepositWarranty("押金、质保金类"), LitigationFee("诉讼费"), SmallProjectProcurement(
			"小额工程采购"), Development("研发"), AssetPurchase("资产购置"), Individual(
			"个人因公特殊"), Other("其他");

	private String type;

	/**
	 * @param type
	 * @param name
	 */
	private LoanTypeEnum(String type) {
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
