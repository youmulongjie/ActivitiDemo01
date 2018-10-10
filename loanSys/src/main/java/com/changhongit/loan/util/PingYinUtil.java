/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * <p>
 * 描述：汉字转换拼音工具类
 * </p>
 * 
 * @version v1.0 2018年6月22日下午1:48:46
 */
public class PingYinUtil {

	/**
	 * 
	 * <p>
	 * 描述：得到中文首字母
	 * </p>
	 * 
	 * @Date 2018年6月27日上午9:35:16 <br>
	 * @param str
	 *            汉字
	 * @param bUpCase
	 *            是否大写
	 * @return
	 */
	public static String getPinYinHeadChar(String str, boolean bUpCase) {
		String headChar = "";

		Character word = null;
		String[] pinyinArray = null;
		for (int i = 0, j = str.length(); i < j; i++) {
			word = str.charAt(i);
			// 判断是否为汉字字符。不是汉字，忽略后继续
			if (!Character.toString(word).matches("[\\u4E00-\\u9FA5]+")) {
				continue;
			}
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				headChar += pinyinArray[0].charAt(0);
			} else {
				headChar += word;
			}
		}

		if (bUpCase) {
			headChar = headChar.toUpperCase();
		}
		return headChar;
	}

	/**
	 * 
	 * <p>
	 * 描述：得到中文首字母（默认转化成大写字母）
	 * </p>
	 * 
	 * @Date 2018年6月27日上午9:38:53 <br>
	 * @param str
	 *            汉字
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		return getPinYinHeadChar(str, true);
	}
}
