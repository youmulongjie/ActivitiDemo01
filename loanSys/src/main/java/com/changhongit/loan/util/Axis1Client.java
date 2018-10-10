/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 描述：axis1客户端 访问工具类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午11:34:10
 */
public class Axis1Client {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AxisClient.class);

	/**
	 * wsdlsoap服务地址
	 */
	private String endpoint;
	/**
	 * 调用方法
	 */
	private String operationName;
	/**
	 * 传入参数
	 */
	private Object[] param;

	/**
	 * 
	 */
	public Axis1Client() {
		super();
	}

	/**
	 * @param endpoint
	 * @param operationName
	 * @param param
	 */
	public Axis1Client(String endpoint, String operationName, Object[] param) {
		super();
		this.endpoint = endpoint;
		this.operationName = operationName;
		this.param = param;
	}

	/**
	 * 
	 * <p>
	 * 描述：调用方法
	 * </p>
	 * 
	 * @Date 2018年5月4日下午1:12:03 <br>
	 * @return
	 * @throws Exception
	 */
	public Object rpc() throws Exception {
		Service s = new Service();
		Call call;
		try {
			call = (Call) s.createCall();
			call.setTargetEndpointAddress(new URL(getEndpoint()));
			call.setOperationName(getOperationName());
			return call.invoke(getParam());
		} catch (ServiceException e) {
			LOGGER.error("创建axis1 service失败： " + e.getMessage(), e);
			throw new ServiceException("创建axis1 service失败： " + e.getMessage());
		} catch (MalformedURLException e) {
			LOGGER.error("创建URL失败： " + e.getMessage(), e);
			throw new MalformedURLException("创建URL失败： " + e.getMessage());
		} catch (RemoteException e) {
			LOGGER.error("远程调用异常： " + e.getMessage(), e);
			throw new RemoteException("远程调用异常： " + e.getMessage());
		}
	}

	public String getEndpoint() {
		return endpoint;
	}

	public Axis1Client setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public String getOperationName() {
		return operationName;
	}

	public Axis1Client setOperationName(String operationName) {
		this.operationName = operationName;
		return this;
	}

	public Object[] getParam() {
		return param;
	}

	public Axis1Client setParam(Object[] param) {
		this.param = param;
		return this;
	}

	/**
	 * 
	 * <p>
	 * 描述：根据参数创建 axis1客户端
	 * </p>
	 * 
	 * @Date 2018年5月4日下午1:15:35 <br>
	 * @param endpoint
	 *            wsdlsoap服务地址
	 * @param operationName
	 *            调用方法
	 * @param param
	 *            传入参数
	 * @return
	 */
	public static Axis1Client create(String endpoint, String operationName,
			Object[] param) {
		return new Axis1Client(endpoint, operationName, param);
	}
}
