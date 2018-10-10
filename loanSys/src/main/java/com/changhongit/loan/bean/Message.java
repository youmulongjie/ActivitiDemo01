package com.changhongit.loan.bean;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * <p>
 * 描述：页面消息显示
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午10:46:26
 */
public class Message {
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 结果
	 */
	private String result;
	/**
	 * 数据
	 */
	private Object data;

	public Message() {
		// 默认构造方法
	}

	public Message(String code, String result) {
		this.code = code;
		this.result = result;
	}

	/**
	 * @param code
	 * @param result
	 * @param data
	 */
	public Message(String code, String result, Object data) {
		super();
		this.code = code;
		this.result = result;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public Message setData(Object data) {
		this.data = data;
		return this;
	}

	public static Message create(String code, String result) {
		return new Message(code, result);
	}

	public static Message create(String code, String result, Object data) {
		return new Message(code, result, data);
	}

	public static Message success() {
		return success("操作成功!");
	}

	public static Message success(String msg) {
		return create("success", msg);
	}

	public static Message success(Object data) {
		return create("success", null, data);
	}

	public static Message success(String msg, Object data) {
		return create("success", msg, data);
	}

	public static Message failure() {
		return failure("操作失败!");
	}

	public static Message failure(String msg) {
		return create("failure", msg);
	}

	public static Message failure(Exception ex) {
		return failure("系统异常:" + ex.getMessage(), ex);
	}

	public static Message failure(String message, Exception ex) {
		if (ex == null) {
			return failure();
		}
		StringWriter sw = null;
		try {
			sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
		} catch (Exception e) {
		}
		Message msg = failure(message + " : " + sw.toString());
		msg.setData(sw.toString());
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [code=" + code + ", result=" + result + ", data="
				+ data + "]";
	}

}
