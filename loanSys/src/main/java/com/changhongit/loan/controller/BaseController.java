/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * <p>
 * 描述：Controller 基类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月7日上午10:20:48
 */
@Controller
public class BaseController {
	/**
	 * 
	 * <p>
	 * 描述：处理 String 与 Date 之间转化
	 * </p>
	 * 
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}
}
