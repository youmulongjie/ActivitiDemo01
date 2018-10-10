/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 描述：接口配置Bean
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午11:25:47
 */
@Component
public class ConfigBean {
	/**
	 * 邮箱登陆服务地址
	 */
	@Value("${notes_login_endpoint}")
	private String notesLoginEndpoint;

	/**
	 * 邮箱登陆服务访问名称
	 */
	@Value("${notes_login_operation_name}")
	private String notesLoginOperationName;

	/**
	 * HR服务地址
	 */
	@Value("${hr_service_wsdl}")
	private String hrServiceWsdl;

	/**
	 * OU服务地址
	 */
	@Value("${ou_service_wsdl}")
	private String ouServiceWsdl;

	/**
	 * 四费接口地址（获取员工OU）
	 */
	@Value("${sifei_service_wsdl}")
	private String sifei_service_wsdl;

	/**
	 * 组织结构接口
	 */
	@Value("${orgServiceWeb_wsdl}")
	private String orgServiceWeb;

	/**
	 * 项目发布地址
	 */
	@Value("${publishAddr}")
	private String publishAddr;

	/**
	 * 生成凭证号 服务接口
	 */
	@Value("${voucherNo_wsdl}")
	private String voucherNoWsdl;

	/**
	 * 创建发票
	 */
	@Value("${loanInvoiceWS}")
	private String loanInvoiceWS;

	/**
	 * 测试环境 邮件接收人
	 */
	@Value("${test_email_receive}")
	private String testEmailReceive;

	/**
	 * 发布环境(测试环境，设置为 false；正式环境，设置为 true)，配合 test_email_receive 使用
	 */
	@Value("${profie}")
	private boolean profie;

	/**
	 * @return the sifei_service_wsdl
	 */
	public String getSifei_service_wsdl() {
		return sifei_service_wsdl;
	}

	/**
	 * @param sifei_service_wsdl
	 *            the sifei_service_wsdl to set
	 */
	public void setSifei_service_wsdl(String sifei_service_wsdl) {
		this.sifei_service_wsdl = sifei_service_wsdl;
	}

	/**
	 * @return the ouServiceWsdl
	 */
	public String getOuServiceWsdl() {
		return ouServiceWsdl;
	}

	/**
	 * @param ouServiceWsdl
	 *            the ouServiceWsdl to set
	 */
	public void setOuServiceWsdl(String ouServiceWsdl) {
		this.ouServiceWsdl = ouServiceWsdl;
	}

	/**
	 * @return the hrServiceWsdl
	 */
	public String getHrServiceWsdl() {
		return hrServiceWsdl;
	}

	/**
	 * @param hrServiceWsdl
	 *            the hrServiceWsdl to set
	 */
	public void setHrServiceWsdl(String hrServiceWsdl) {
		this.hrServiceWsdl = hrServiceWsdl;
	}

	/**
	 * @return the notesLoginEndpoint
	 */
	public String getNotesLoginEndpoint() {
		return notesLoginEndpoint;
	}

	/**
	 * @param notesLoginEndpoint
	 *            the notesLoginEndpoint to set
	 */
	public void setNotesLoginEndpoint(String notesLoginEndpoint) {
		this.notesLoginEndpoint = notesLoginEndpoint;
	}

	/**
	 * @return the notesLoginOperationName
	 */
	public String getNotesLoginOperationName() {
		return notesLoginOperationName;
	}

	/**
	 * @param notesLoginOperationName
	 *            the notesLoginOperationName to set
	 */
	public void setNotesLoginOperationName(String notesLoginOperationName) {
		this.notesLoginOperationName = notesLoginOperationName;
	}

	/**
	 * @return the orgServiceWeb
	 */
	public String getOrgServiceWeb() {
		return orgServiceWeb;
	}

	/**
	 * @param orgServiceWeb
	 *            the orgServiceWeb to set
	 */
	public void setOrgServiceWeb(String orgServiceWeb) {
		this.orgServiceWeb = orgServiceWeb;
	}

	/**
	 * @return the publishAddr
	 */
	public String getPublishAddr() {
		return publishAddr;
	}

	/**
	 * @param publishAddr
	 *            the publishAddr to set
	 */
	public void setPublishAddr(String publishAddr) {
		this.publishAddr = publishAddr;
	}

	/**
	 * @return the voucherNoWsdl
	 */
	public String getVoucherNoWsdl() {
		return voucherNoWsdl;
	}

	/**
	 * @param voucherNoWsdl
	 *            the voucherNoWsdl to set
	 */
	public void setVoucherNoWsdl(String voucherNoWsdl) {
		this.voucherNoWsdl = voucherNoWsdl;
	}

	/**
	 * @return the loanInvoiceWS
	 */
	public String getLoanInvoiceWS() {
		return loanInvoiceWS;
	}

	/**
	 * @param loanInvoiceWS
	 *            the loanInvoiceWS to set
	 */
	public void setLoanInvoiceWS(String loanInvoiceWS) {
		this.loanInvoiceWS = loanInvoiceWS;
	}

	/**
	 * @return the testEmailReceive
	 */
	public String getTestEmailReceive() {
		return testEmailReceive;
	}

	/**
	 * @param testEmailReceive
	 *            the testEmailReceive to set
	 */
	public void setTestEmailReceive(String testEmailReceive) {
		this.testEmailReceive = testEmailReceive;
	}

	/**
	 * @return the profie
	 */
	public boolean isProfie() {
		return profie;
	}

	/**
	 * @param profie the profie to set
	 */
	public void setProfie(boolean profie) {
		this.profie = profie;
	}

}
