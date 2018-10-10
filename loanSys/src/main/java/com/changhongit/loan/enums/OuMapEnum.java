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
 * 描述：OU 映射 枚举类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月23日上午10:37:19
 */
public enum OuMapEnum {
	信产("信息产品"), 智能("佳华智能"), 数字("增值数字公司"), 哆啦("哆啦有货公司");

	/**
	 * @param map
	 */
	private OuMapEnum(String map) {
		this.map = map;
	}

	public String map;

	/**
	 * @return the map
	 */
	public String getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(String map) {
		this.map = map;
	}

}
