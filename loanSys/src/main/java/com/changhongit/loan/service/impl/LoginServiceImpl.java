/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhongit.loan.bean.ConfigBean;
import com.changhongit.loan.service.LoginService;
import com.changhongit.loan.util.Axis1Client;

/**
 * <p>
 * 描述：登陆接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午11:40:55
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private ConfigBean configBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoginService#loginValid(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String loginValid(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		Axis1Client axis1Client = Axis1Client.create(
				configBean.getNotesLoginEndpoint(),
				configBean.getNotesLoginOperationName(), new Object[] {
						username, password });
		return (String) axis1Client.rpc();
	}
}
