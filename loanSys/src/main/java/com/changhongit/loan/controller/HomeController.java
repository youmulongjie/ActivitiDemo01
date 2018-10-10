/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @deprecated
 * <p>
 * 描述：主页Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日上午9:35:25
 */
@Controller
public class HomeController {
	/**
	 * 
	 * <p>
	 * 描述：主界面
	 * </p>
	 * 
	 * @Date 2018年5月17日上午10:58:36 <br>
	 * @return
	 */
	@RequestMapping(value = "homePage", produces = "application/json", method = RequestMethod.GET)
	public String homePage() {
		return "personalWork/Todo";
	}
}
