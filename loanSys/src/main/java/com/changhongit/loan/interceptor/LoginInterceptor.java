/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.interceptor<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;

/**
 * <p>
 * 描述：登陆拦截器
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日下午1:48:47
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	// 上下文名
	private static String contextPath;
	// 不被拦截的资源
	public static List<String> exclude;

	/*
	 * *对受保护的资源进行拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		contextPath = request.getContextPath();
		exclude = Arrays.asList(new String[] { contextPath + "/login.do" });
		String uri = request.getRequestURI().toString();
		// 对不受保护的资源放行
		if (exclude.contains(uri)) {
			return true;
		}
		// 对登陆进行拦截
		// 将session中对象存入object
		Object obj = request.getSession().getAttribute(IConstant.SESSION_USER);
		if (obj instanceof User) {
			User user = (User) obj;
			if (user != null) {
				return true;
			}
		}
		response.sendRedirect(contextPath + "/login.jsp");
		return false;
	}
}
