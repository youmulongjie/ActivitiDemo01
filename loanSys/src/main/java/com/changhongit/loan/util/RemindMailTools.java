/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import jodd.datetime.JDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.changhongit.jms.Sender;
import com.changhongit.jms.properties.Utils;
import com.changhongit.loan.bean.ConfigBean;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.mail.client.MailSender;
import com.changhongit.mail.module.Mail;
import com.changhongit.mail.module.MailModuleTools;
import com.changhongit.mail.module.MailReceive;

/**
 * <p>
 * 描述：邮件提醒
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月13日下午3:17:51
 */
@Component
public class RemindMailTools {
	private static final Logger logger = LoggerFactory
			.getLogger(RemindMailTools.class);
	private static final String TEST_PROFIE = "测试环境------";

	@Autowired
	private ConfigBean configBean;
	private static RemindMailTools remindMailTools;

	@PostConstruct
	public void init() {
		remindMailTools = this;
		remindMailTools.configBean = this.configBean;
	}

	/**
	 * 
	 * <p>
	 * 描述：发送需审批提醒邮件
	 * </p>
	 * 
	 * @Date 2018年6月13日下午3:45:06 <br>
	 * @param assignee
	 *            当前任务 办理人
	 * @param entity
	 * @throws Exception
	 */
	public static void sendTipMail(String assignee, LoanMainEntity entity)
			throws Exception {
		logger.info("审批人(assignee):{}", assignee);
		String email = "";
		if (!StringUtils.isEmpty(assignee)) {
			assignee = assignee.replaceAll("，", ",").replaceAll(" ", "");
			if (assignee.contains(",")) {
				String[] assignees = StringUtils.tokenizeToStringArray(assignee, ",");
				for (int i = 0, j = assignees.length; i < j; i++) {
					email += HrInfoUtil.getMailByUserName(assignees[i]) + ",";
				}
			} else {
				email = HrInfoUtil.getMailByUserName(assignee);
			}
		}
		Mail m = new Mail();

		logger.info("正式环境审批人邮箱地址(email):{}", email);
		boolean profie = remindMailTools.configBean.isProfie();
		if (!profie) {
			// 测试环境
			email = remindMailTools.configBean.getTestEmailReceive();
			logger.info("测试环境审批人邮箱地址(email):{}", email);
		}

		JDateTime dateTime = new JDateTime(entity.getApplyDate());
		try {
			String title = "您有『" + entity.getPerson() + "的个人借款申请单("
					+ dateTime.toString("YYYY-MM-DD hh:mm:ss") + ")"
					+ "』需要审批，请尽快处理，谢谢!";
			if (!profie) {
				// 测试环境
				title = TEST_PROFIE + title;
			}
			m.setTitle(title);

			String body = "";
			if (!profie) {
				// 测试环境
				body += TEST_PROFIE + "<br/><br/>";
			}
			body += "您有『" + entity.getPerson() + "的个人借款申请单("
					+ dateTime.toString("YYYY-MM-DD hh:mm:ss")
					+ ")』需要审批，请尽快处理，谢谢!<br/><br/>";
			body += "链接地址:<a href='" + HrInfoUtil.publishAddr() + "'>"
					+ HrInfoUtil.publishAddr() + "</a><br/>";
			m.setBody("<b>" + body + "</b><br/>"
					+ MailModuleTools.getDefaultBody());
			m.setCreateBy("loanSysMail");
			m.setSender("xinxi@changhongit.com");
			m.setModel("loanSysMail online notice");
			String desc = "借款审批" + System.currentTimeMillis();// 模块描述
			m.setModelDesc(desc);
			// 设置接收人
			MailReceive mr = new MailReceive();
			mr.setReceiverMail(email);
			List<MailReceive> list = new ArrayList<MailReceive>();
			list.add(mr);
			m.setReceiveList(list);
			// 建立邮件发送类
			Sender sender = Utils
					.getSenderInstance("/send.mail.channel.properties");
			// 发送邮件
			MailSender.sendMail(sender, m);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：发送驳回提醒邮件
	 * </p>
	 * 
	 * @Date 2018年7月3日下午2:29:22 <br>
	 * @param assignee
	 *            当前任务 办理人
	 * @param entity
	 * @param reason
	 *            原因
	 * @param operator
	 *            操作（驳回|补正）
	 * @param operatorName
	 *            操作人
	 * @throws Exception
	 */
	public static void sendRejectMail11(String assignee, LoanMainEntity entity,
			String reason, String operator, String operatorName)
			throws Exception {
		logger.info("审批人(assignee):{}", assignee);
		String email = HrInfoUtil.getMailByUserName(assignee);
		Mail m = new Mail();

		logger.info("原审批人邮箱地址(email):{}", email);
		boolean profie = remindMailTools.configBean.isProfie();
		if (!profie) {
			// 测试环境
			email = remindMailTools.configBean.getTestEmailReceive();
			logger.info("测试环境审批人邮箱地址(email):{}", email);
		}

		JDateTime dateTime = new JDateTime(entity.getApplyDate());
		String applyDate = dateTime.toString("YYYY-MM-DD hh:mm:ss");

		try {
			String date = new JDateTime(entity.getSuperiorApproveDate())
					.toString();
			String title = "您的个人借款申请单『" + entity.getPerson() + "(" + applyDate
					+ ")』被" + operator + "通知";
			if (!profie) {
				// 测试环境
				title = TEST_PROFIE + title;
			}
			m.setTitle(title);

			String body = "";
			if (!profie) {
				// 测试环境
				body += TEST_PROFIE + "<br/><br/>";
			}

			body += "您的个人借款申请单『" + entity.getPerson() + "(" + applyDate
					+ ")』被『" + operatorName + "』" + operator + "。<br/><br/>";
			body += operator + "原因：『" + reason + "』，" + operator + "时间：『"
					+ date + "』<br/><br/>";
			body += "您可进入员工借款系统完成相关操作(如您已处理过该借款单，可忽略此邮件提醒)！<br/><br/>";
			body += "链接地址:<a href='" + HrInfoUtil.publishAddr() + "'>"
					+ HrInfoUtil.publishAddr() + "</a>";
			m.setBody("<b>" + body + "</b><br/>"
					+ MailModuleTools.getDefaultBody());
			m.setCreateBy("loanSysRejectMail");
			m.setSender("xinxi@changhongit.com");
			m.setModel("loanSysMail online notice");
			String desc = "借款审批驳回" + System.currentTimeMillis();// 模块描述
			m.setModelDesc(desc);
			// 设置接收人
			MailReceive mr = new MailReceive();
			mr.setReceiverMail(email);
			List<MailReceive> list = new ArrayList<MailReceive>();
			list.add(mr);
			m.setReceiveList(list);
			// 建立邮件发送类
			Sender sender = Utils
					.getSenderInstance("/send.mail.channel.properties");
			// 发送邮件
			MailSender.sendMail(sender, m);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：审批完成通知 申请人邮件
	 * </p>
	 * 
	 * @Date 2018年8月23日下午3:00:04 <br>
	 * @param applyer
	 *            申请人
	 * @param entity
	 * @throws Exception
	 */
	public static void sendFinishMail(String applyer, LoanMainEntity entity)
			throws Exception {
		logger.info("申请人(applyer):{}", applyer);
		String email = "";
		if (!StringUtils.isEmpty(applyer)) {
			email = HrInfoUtil.getMailByUserName(applyer);
		}
		Mail m = new Mail();

		logger.info("正式环境申请人邮箱地址(email):{}", email);
		boolean profie = remindMailTools.configBean.isProfie();
		if (!profie) {
			// 测试环境
			email = remindMailTools.configBean.getTestEmailReceive();
			logger.info("测试环境申请人邮箱地址(email):{}", email);
		}

		JDateTime dateTime = new JDateTime(entity.getApplyDate());
		String applyDate = dateTime.toString("YYYY-MM-DD hh:mm:ss");

		try {
			String title = "您的借款申请已审批完成，请打印纸质单据，递交财务会计签字付款";
			if (!profie) {
				// 测试环境
				title = TEST_PROFIE + title;
			}
			m.setTitle(title);

			String body = "";
			if (!profie) {
				// 测试环境
				body += TEST_PROFIE + "<br/><br/>";
			}
			body += "借款类型：" + entity.getLoantype() + ", 借款金额："
					+ entity.getTotalMoney() + "，借款申请时间：" + applyDate + "<br/><br/>";
			body += "链接地址:<a href='" + HrInfoUtil.publishAddr()
					+ "'>您的借款申请已审批完成，请打印纸质单据，递交财务会计签字付款（登陆系统--》个人工作区--》审批中）</a><br/>";
			m.setBody("<b>" + body + "</b><br/>"
					+ MailModuleTools.getDefaultBody());
			m.setCreateBy("loanSysMail");
			m.setSender("xinxi@changhongit.com");
			m.setModel("loanSysMail online notice");
			String desc = "借款审批" + System.currentTimeMillis();// 模块描述
			m.setModelDesc(desc);
			// 设置接收人
			MailReceive mr = new MailReceive();
			mr.setReceiverMail(email);
			List<MailReceive> list = new ArrayList<MailReceive>();
			list.add(mr);
			m.setReceiveList(list);
			// 建立邮件发送类
			Sender sender = Utils
					.getSenderInstance("/send.mail.channel.properties");
			// 发送邮件
			MailSender.sendMail(sender, m);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
