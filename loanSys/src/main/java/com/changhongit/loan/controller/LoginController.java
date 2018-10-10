/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhongit.HRModule.hr.CuxHrEmployeeRec;
import com.changhongit.expense.bean.CuxOperatingUnits;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.LoanSysAdminEntity;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.service.LoginService;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.loan.util.PingYinUtil;

/**
 * <p>
 * 描述：登陆Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月4日上午10:51:17
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private ManagerService managerService;

	/**
	 * 
	 * <p>
	 * 描述：登陆 事件
	 * </p>
	 * 
	 * @Date 2018年5月4日上午10:55:33 <br>
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "login", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Message login(String username, String password,
			HttpServletRequest req) {
		logger.info("用户【{}】请求登陆.", username);
		Message message = null;
		if (StringUtils.isEmpty(username)) {
			message = Message.failure("用户名 为空！");
			logger.info("登录失败.");
			return message;
		}
		if (StringUtils.isEmpty(password)) {
			message = Message.failure("密码 为空！");
			logger.info("登录失败.");
			return message;
		}
		String erpNum = "";
		try {
			String verifyStr = loginService.loginValid(username, password);
			if (!StringUtils.isEmpty(verifyStr)) {
				String[] str = verifyStr.split(":");
				if (!str[1].equals("0") && !str[1].equals("-1")
						&& !str[1].equals("-2") && !str[1].equals("-3")) {
					username = (str[0] != null && !"".equals(str[0])) ? str[0]
							: username;
					erpNum = str[1];
				}

				if ("".equals(erpNum)) {
					message = Message.failure();
					message.setResult("用户名，密码不匹配");
					logger.info("登录失败.");
				} else {
					User user = new User(username, erpNum);

					CuxHrEmployeeRec hr = HrInfoUtil.getPersonInfo(erpNum);
					// 中文名、实际工作地
					String name = hr.getLastName();
					logger.info("name = {}，erpNum = {}", name, erpNum);
					user.setUsername(name);
					user.setWorksite(hr.getWorkSite());

					// 中文名称简称，财务人员创建凭证号使用
					user.setUsernameJC(PingYinUtil
							.getPinYinHeadChar(name, true));

					// 员工OU
					CuxOperatingUnits cou = HrInfoUtil.getOuByErpNumber(Long
							.parseLong(user.getErpNum()));
					String ouFullName = null;
					String ou = null;
					if (cou != null && cou.getOuList() != null) {
						ouFullName = cou.getOuList().get(0).getOuFullName();
						ou = cou.getOuList().get(0).getOuName();
					}
					user.setOuFullName(ouFullName);
					user.setOu(ou);

					// 是否是 系统管理员
					user.setAdministrator(isAdmin(name));
					// 是否是 财务会计
					user.setFnAccounting(isFnAccounting(name));
					// 是否是 出纳
					user.setCashier(isCashier(name));

					req.getSession().setAttribute(IConstant.SESSION_USER, user);

					logger.info("登录成功.");
					logger.info(user.toString());
					message = Message.success("登陆成功");
				}
			} else {
				message = Message.failure("登陆失败！");
				logger.info("登录失败.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = Message.failure("登陆失败！", e);
			logger.info("登录失败.");
		}
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：判断是否 财务会计
	 * </p>
	 * 
	 * @Date 2018年7月4日下午2:48:53 <br>
	 * @param username
	 *            登陆者中文名
	 * @return
	 */
	private boolean isFnAccounting(String username) {
		// 从借款类型对应会计 判断
		LoanTypeEnum[] enums = LoanTypeEnum.values();
		String fnAccounting = null;
		for (LoanTypeEnum e : enums) {
			fnAccounting = InitUtil.fnAccountingMap.get(e.getType());
			if (!StringUtils.isEmpty(fnAccounting)) {
				if (fnAccounting.contains(username)) {
					return true;
				}
			}
		}

		// 从 分公司会计配置 判断
		Collection<String> branchAccountings = InitUtil.branchAccountingMap
				.values();
		Iterator<String> its = branchAccountings.iterator();
		String branchAccounting = null;
		while (its.hasNext()) {
			branchAccounting = its.next();
			if (!StringUtils.isEmpty(branchAccounting)
					&& branchAccounting.equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * 描述：判断是否 出纳
	 * </p>
	 * 
	 * @Date 2018年7月4日下午2:55:17 <br>
	 * @param username
	 *            登陆者中文名
	 * @return
	 */
	private boolean isCashier(String username) {
		// 从分公司出纳中判断
		Collection<String> branchCashiers = InitUtil.branchCashierMap.values();
		Iterator<String> its = branchCashiers.iterator();
		String branchCashier = null;
		while (its.hasNext()) {
			branchCashier = its.next();
			if (!StringUtils.isEmpty(branchCashier)
					&& branchCashier.equals(username)) {
				return true;
			}
		}

		// 从固定审批人中判断
		// 1）出纳
		String fixedCashier = InitUtil.fixedApproverMap
				.get(FixedApproverEnum.出纳.name());
		if (!StringUtils.isEmpty(fixedCashier) && fixedCashier.equals(username)) {
			return true;
		}

		// 2）财务复核
		String check = InitUtil.fixedApproverMap.get(FixedApproverEnum.财务复核
				.name());
		if (!StringUtils.isEmpty(check) && check.equals(username)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * <p>
	 * 描述：判断是否是管理员
	 * </p>
	 * 
	 * @Date 2018年7月5日下午4:27:13 <br>
	 * @param username
	 *            登陆者中文名
	 * @return
	 */
	private boolean isAdmin(String username) {
		LoanSysAdminEntity entity = managerService
				.findSysAdminEntityByUsername(username);
		if (null != entity) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * 描述：清理session并登出
	 * </p>
	 * 
	 * @Date 2018年5月17日上午9:54:53 <br>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loginOut", method = { RequestMethod.GET }, produces = "application/json")
	public String loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(IConstant.SESSION_USER);
		logger.info("用户【{}】退出登录.", user.getUsername());
		session.removeAttribute(IConstant.SESSION_USER);
		session.invalidate();
		return "redirect:/login.jsp";
	}
}
