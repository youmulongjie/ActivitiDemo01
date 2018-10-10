/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

/**
 * <p>
 * 描述：登陆接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午11:37:27
 */
public interface LoginService {
	/**
	 * 
	 * <p>
	 * 描述：登陆验证
	 * </p>
	 * 
	 * @Date 2018年5月4日上午11:38:35 <br>
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public String loginValid(String username, String password) throws Exception;
}
