/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.Page;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.BaseEntity;
import com.changhongit.loan.entity.LoanApproverConfigEntity;
import com.changhongit.loan.entity.LoanBranchAccountingEntity;
import com.changhongit.loan.entity.LoanBranchCashierEntity;
import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;
import com.changhongit.loan.entity.LoanPlatformMinisterEntity;
import com.changhongit.loan.entity.LoanSpecialOuEntity;
import com.changhongit.loan.entity.LoanSysAdminEntity;
import com.changhongit.loan.entity.LoanToAccountingEntity;
import com.changhongit.loan.entity.LoanToInvoiceEntity;
import com.changhongit.loan.enums.BranchEnum;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：管理工作区 Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日上午11:18:58
 */
@Controller
public class ManagerWorkController {
	private static final Logger logger = LoggerFactory
			.getLogger(ManagerWorkController.class);

	@Autowired
	private ManagerService managerService;

	// 【固定审批人配置】开始
	// TODO: 固定审批人配置
	/**
	 * 
	 * <p>
	 * 描述：调整到 固定审批人配置页面
	 * </p>
	 * 
	 * @Date 2018年5月17日上午11:21:16 <br>
	 * @return
	 */
	@RequestMapping(value = "fixed_approver", method = RequestMethod.GET)
	public String toFixed_approver(ModelMap modelMap) {
		Map<String, LoanApproverConfigEntity> approverMap = new LinkedHashMap<String, LoanApproverConfigEntity>();

		FixedApproverEnum[] approverEnums = FixedApproverEnum.values();
		// 职位
		String position = null;
		for (FixedApproverEnum approverEnum : approverEnums) {
			position = approverEnum.name();
			approverMap.put(position, managerService.findByPosition(position));
		}
		modelMap.put("approverMap", approverMap);
		return "managerWork/fixed_approver";
	}

	/**
	 * <p>
	 * 描述：保存 固定审批人
	 * </p>
	 * 
	 * @Date 2018年5月17日下午3:23:21 <br>
	 * @return
	 */
	@RequestMapping(value = "fixed_approver", method = RequestMethod.POST)
	@ResponseBody
	public Message saveFixedApprover(@RequestParam("param") String param,
			HttpServletRequest request) {
		logger.info("保存 固定审批人配置.");
		logger.info("固定审批人参数：{}", param);
		Message message = null;
		User user = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);

		try {
			String[] array = param.split(";");
			String[] temp = null;
			LoanApproverConfigEntity configEntity = null;
			List<LoanApproverConfigEntity> list = new ArrayList<LoanApproverConfigEntity>();
			for (int i = 0, j = array.length; i < j; i++) {
				temp = array[i].split(",");

				configEntity = new LoanApproverConfigEntity();
				configEntity.setPosition(temp[0]);
				configEntity.setName(temp[1]);
				configEntity.setCreateBy(user.getUsername());
				list.add(configEntity);
			}
			managerService.saveOrUpdateLoanApproverConfigEntitys(list,
					user.getUsername());
			message = Message.success("保存成功！");
		} catch (Exception e) {
			message = Message.failure("保存失败", e);
		}

		// 每次变更时，将固定审批人 信息保存在Map缓存中
		FixedApproverEnum[] enums = FixedApproverEnum.values();
		for (FixedApproverEnum e : enums) {
			InitUtil.fixedApproverMap.put(e.name(), managerService
					.findByPosition(e.name()).getName());
		}
		logger.info("保存时，固定审批人信息：{}", InitUtil.fixedApproverMap);

		logger.info(message.getResult());
		return message;
	}

	// 【固定审批人配置】结束
	// ************************************//
	// 【借款专用OU配置】开始
	// TODO 借款专用OU配置

	/**
	 * 
	 * <p>
	 * 描述：调整到 专用OU列表页面
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:44:29 <br>
	 * @return
	 */
	@RequestMapping(value = "loan_special_OU", method = RequestMethod.GET)
	public String toLoan_special_OU() {
		return "managerWork/loan_special_OU";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 专用OU列表
	 * </p>
	 * 
	 * @Date 2018年5月21日下午12:21:54 <br>
	 * @param currentPage
	 *            当前页数
	 * @return
	 */
	@RequestMapping(value = "pageLoan_special_OU", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanSpecialOuEntity> pageLoan_special_OU(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanSpecialOuEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：根据ID，删除专用OU配置
	 * </p>
	 * 
	 * @Date 2018年5月18日下午2:36:56 <br>
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteLoan_special_OU", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoan_special_OU(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除 借款专用OU配置（ids={}）...", ids);
		return deleteEntityByIds(LoanSpecialOuEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：调整到 新增专用OU配置页面
	 * </p>
	 * 
	 * @Date 2018年5月18日下午3:19:18 <br>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_special_ou_new", method = RequestMethod.GET)
	public String toLoan_special_OU_new(ModelMap modelMap) throws Exception {
		modelMap.put("list", InitUtil.getOulist());
		return "managerWork/loan_special_ou_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存 专用OU配置
	 * </p>
	 * 
	 * @Date 2018年5月18日下午3:41:24 <br>
	 * @param ou
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_special_ou_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoan_special_OU_new(@RequestParam("ou") String ou,
			HttpServletRequest request) throws Exception {
		logger.info("保存新ou:{}.", ou);
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			managerService.saveLoanSpecialOuEntity(ou, user.getUsername());
			message = Message.success("保存操作成功！");
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}
		logger.info(message.getResult());
		return message;
	}

	// 【借款专用OU配置】结束
	// ************************************//
	// 【最长清欠期限配置】开始
	// TODO 最长清欠期限配置

	/**
	 * 
	 * <p>
	 * 描述：跳转到最长清欠期限配置页面
	 * </p>
	 * 
	 * @Date 2018年5月21日下午2:28:43 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "loan_maxduration_config", method = RequestMethod.GET)
	public String toLoan_maxduration_config() {
		return "managerWork/loan_maxduration_config";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 最长清欠期限配置 Entity 列表
	 * </p>
	 * 
	 * @Date 2018年5月21日下午2:31:24 <br>
	 * @param currentPage
	 *            当前页数
	 * @return
	 */
	@RequestMapping(value = "pageLoan_maxduration_config", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanMaxDurationConfigEntity> pageLoan_maxduration_config(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanMaxDurationConfigEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：根据ID，删除最长清欠期限配Entity
	 * </p>
	 * 
	 * @Date 2018年5月21日下午3:35:17 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteLoan_maxduration_config", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoan_maxduration_config(
			@RequestParam("ids") String ids, HttpServletRequest request) {
		logger.info("删除最长清欠期限配置（ids={}）...", ids);
		return deleteEntityByIds(LoanMaxDurationConfigEntity.class, ids,
				request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增最长清欠期限配置页面
	 * </p>
	 * 
	 * @Date 2018年5月21日下午4:04:48 <br>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_naxduration_config_new", method = RequestMethod.GET)
	public String toLoan_naxduration_config_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanMaxDurationConfigEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(
					LoanMaxDurationConfigEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loan_naxduration_config_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存或更新 最长清欠期限配置Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午1:05:19 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_naxduration_config_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoan_naxduration_config_new(
			LoanMaxDurationConfigEntity entity, HttpServletRequest request)
			throws Exception {
		logger.info("保存新增最长清欠期限配置");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanMaxDurationConfigEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}
		logger.info(message.getResult());
		return message;
	}

	// 【最长清欠期限配置】结束
	// ************************************//
	// 【借款单对应发票类型】开始
	// TODO 借款单对应发票类型

	/**
	 * 
	 * <p>
	 * 描述：跳转到借款单对应发票类型页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午1:05:08 <br>
	 * @return
	 */
	@RequestMapping(value = "loanToInvoice", method = RequestMethod.GET)
	public String toLoanToInvoice() {
		return "managerWork/loanToInvoice";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 借款单对应发票类型Entity集合
	 * </p>
	 * 
	 * @Date 2018年5月22日下午1:34:39 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "pageLoanToInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanToInvoiceEntity> pageLoanToInvoice(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanToInvoiceEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 借款单对应发票类型Entity集合
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:18:01 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteLoanToInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoanToInvoice(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除借款单对应发票类型配置（ids={}）...", ids);
		return deleteEntityByIds(LoanToInvoiceEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增借款单对应发票类型页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:18:18 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loanToInvoice_new", method = RequestMethod.GET)
	public String toLoanToInvoice_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanToInvoiceEntity entity = null;
		if (null != id) {
			entity = managerService
					.getEntityById(LoanToInvoiceEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loanToInvoice_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：更新或保存 借款单对应发票类型Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:18:27 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loanToInvoice_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoanToInvoice_new(LoanToInvoiceEntity entity,
			HttpServletRequest request) throws Exception {
		logger.info("保存新增借款单对应发票类型");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanToInvoiceEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		// 每次变更时，将借款单对应发票类型信息 保存在Map缓存中
		LoanTypeEnum[] enums = LoanTypeEnum.values();
		for (LoanTypeEnum e : enums) {
			InitUtil.invoiceMap.put(e.getType(), managerService
					.findInvoiceByLoanType(e.getType()).getAccounting());
		}
		logger.info("保存时，借款单对应发票类型信息：{}", InitUtil.invoiceMap);

		logger.info(message.getResult());
		return message;
	}

	// 【借款单对应发票类型】结束
	// ************************************//
	// 【借款类型对应会计】开始
	// TODO 借款类型对应会计

	/**
	 * 
	 * <p>
	 * 描述：跳转到借款类型对应会计页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:04:35 <br>
	 * @return
	 */
	@RequestMapping(value = "loanToAccounting", method = RequestMethod.GET)
	public String toLoanToAccounting() {
		return "managerWork/loanToAccounting";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 借款类型对应会计Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:04:41 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "pageLoanToAccounting", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanToAccountingEntity> pageLoanToAccounting(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanToAccountingEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 借款类型对应会计Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:07:29 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteLoanToAccounting", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoanToAccounting(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除借款类型对应会计配置（ids={}）...", ids);
		return deleteEntityByIds(LoanToAccountingEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增借款类型对应会计页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:10:30 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loanToAccounting_new", method = RequestMethod.GET)
	public String toLoanToAccounting_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanToAccountingEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(LoanToAccountingEntity.class,
					id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loanToAccounting_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：更新或保存 借款类型对应会计Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:18:27 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loanToAccounting_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoanToAccounting_new(LoanToAccountingEntity entity,
			HttpServletRequest request) throws Exception {
		logger.info("保存新增借款类型对应会计");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanToAccountingEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		// 每次变更时，将借款类型对应会计 信息保存在Map缓存中
		LoanTypeEnum[] enums = LoanTypeEnum.values();
		for (LoanTypeEnum e : enums) {
			InitUtil.fnAccountingMap.put(e.getType(), managerService
					.findAccountingByLoanType(e.getType()).getAccounting());
		}
		logger.info("保存时，借款类型对应会计信息：{}", InitUtil.fnAccountingMap);

		logger.info(message.getResult());
		return message;
	}

	// 【借款类型对应会计】结束
	// ************************************//
	// 【分公司会计配置】开始
	// TODO 分公司会计配置
	/**
	 * 
	 * <p>
	 * 描述：跳转到分公司会计配置页面
	 * </p>
	 * 
	 * @Date 2018年7月5日下午2:56:15 <br>
	 * @return
	 */
	@RequestMapping(value = "branchAccounting", method = RequestMethod.GET)
	public String branchAccounting() {
		return "managerWork/loan_branchAccounting";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 分公司会计Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:11:15 <br>
	 * @return
	 */
	@RequestMapping(value = "pageLoan_BranchAccounting", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanBranchAccountingEntity> pageLoan_BranchAccounting(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanBranchAccountingEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 分公司会计Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:11:15 <br>
	 * @return
	 */
	@RequestMapping(value = "deleteLoan_BranchAccounting", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoan_BranchAccounting(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除分公司会计配置（ids={}）...", ids);
		return deleteEntityByIds(LoanBranchAccountingEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到 新增分公司会计配置页面
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:15:46 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_BranchAccounting_new", method = RequestMethod.GET)
	public String toLoan_BranchAccounting_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanBranchAccountingEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(
					LoanBranchAccountingEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loan_BranchAccounting_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存 新增分公司会计配置页面
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:15:46 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_BranchAccounting_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoan_BranchAccounting_new(
			LoanBranchAccountingEntity entity, HttpServletRequest request)
			throws Exception {
		logger.info("保存 新增分公司会计配置页面");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanBranchAccountingEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		// 每次变更时，将分公司出纳配置信息 保存在Map缓存中
		BranchEnum[] enums = BranchEnum.values();
		for (BranchEnum e : enums) {
			InitUtil.branchAccountingMap
					.put(e.name(), managerService
							.findBranchAccountingByBranchName(e.name())
							.getAccounting());
		}
		logger.info("保存时，分公司会计信息：{}", InitUtil.branchAccountingMap);

		logger.info(message.getResult());
		return message;
	}

	// 【分公司会计配置】结束
	// ************************************//
	// 【分公司出纳配置】开始
	// TODO 分公司出纳配置

	/**
	 * 
	 * <p>
	 * 描述：跳转到分公司出纳配置页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:12:06 <br>
	 * @return
	 */
	@RequestMapping(value = "loan_BranchCashier", method = RequestMethod.GET)
	public String toLoan_BranchCashier() {
		return "managerWork/loan_BranchCashier";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 分公司出纳Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:04:41 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "pageLoan_BranchCashier", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanBranchCashierEntity> pageLoan_BranchCashier(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanBranchCashierEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 分公司出纳Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:07:29 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteLoan_BranchCashier", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoan_BranchCashier(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除分公司出纳配置（ids={}）...", ids);
		return deleteEntityByIds(LoanBranchCashierEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增分公司出纳配置页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:15:46 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_BranchCashier_new", method = RequestMethod.GET)
	public String toLoan_BranchCashier_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanBranchCashierEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(
					LoanBranchCashierEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loan_BranchCashier_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存 新增分公司出纳配置
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:25:46 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_BranchCashier_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoan_BranchCashier_new(LoanBranchCashierEntity entity,
			HttpServletRequest request) throws Exception {
		logger.info("保存新增分公司出纳配置");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanBranchCashierEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		// 每次变更时，将分公司出纳配置信息 保存在Map缓存中
		BranchEnum[] enums = BranchEnum.values();
		for (BranchEnum e : enums) {
			InitUtil.branchCashierMap.put(e.name(), managerService
					.findBranchCashierByBranchName(e.name()).getCashier());
		}
		logger.info("保存时，分公司出纳信息：{}", InitUtil.branchCashierMap);

		logger.info(message.getResult());
		return message;
	}

	// 【分公司出纳配置】结束
	// ************************************//
	// 【平台部长配置】开始
	// TODO 平台部长配置

	/**
	 * 
	 * <p>
	 * 描述：跳转到平台部长配置页面
	 * </p>
	 * 
	 * @Date 2018年7月10日下午1:32:06 <br>
	 * @return
	 */
	@RequestMapping(value = "platformMinister", method = RequestMethod.GET)
	public String toPlatformMinister() {
		return "managerWork/platformMinister";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 平台部长Entity
	 * </p>
	 * 
	 * @Date 2018年7月10日下午1:34:41 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "pagePlatformMinister", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanPlatformMinisterEntity> pagePlatformMinister(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService.pageEntitys(LoanPlatformMinisterEntity.class,
				currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 平台部长Entity
	 * </p>
	 * 
	 * @Date 2018年7月17日下午1:52:29 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletePlatformMinister", method = RequestMethod.POST)
	@ResponseBody
	public Message deletePlatformMinister(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除平台部长配置（ids={}）...", ids);
		return deleteEntityByIds(LoanPlatformMinisterEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增平台部长配置页面
	 * </p>
	 * 
	 * @Date 2018年7月10日下午1:55:46 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "platformMinister_new", method = RequestMethod.GET)
	public String toPlatformMinister_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanPlatformMinisterEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(
					LoanPlatformMinisterEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/platformMinister_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存 新增平台部长配置
	 * </p>
	 * 
	 * @Date 2018年7月10日下午1:57:46 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "platformMinister_new", method = RequestMethod.POST)
	@ResponseBody
	public Message savePlatformMinister_new(LoanPlatformMinisterEntity entity,
			HttpServletRequest request) throws Exception {
		logger.info("保存新增平台部长配置");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			message = managerService.saveLoanPlatformMinisterEntity(entity,
					user.getUsername());
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		// 每次变更时，将平台部长配置信息 保存在Map缓存中
		List<LoanPlatformMinisterEntity> entities = managerService
				.getAllEntitys(LoanPlatformMinisterEntity.class);
		if (null != entities && entities.size() > 0) {
			LoanPlatformMinisterEntity temp = null;
			for (int i = 0, j = entities.size(); i < j; i++) {
				temp = entities.get(i);
				InitUtil.platformMinisterMap.put(temp.getPlatformName(),
						temp.getMinisterName());
			}
		}
		logger.info("保存时，平台部长信息：{}", InitUtil.platformMinisterMap);

		logger.info(message.getResult());
		return message;
	}

	// 【平台部长配置】结束
	// ************************************//
	// 【系统管理员配置】开始
	// TODO 系统管理员配置

	/**
	 * 
	 * <p>
	 * 描述：跳转到系统管理员配置页面
	 * </p>
	 * 
	 * @Date 2018年7月5日下午4:32:06 <br>
	 * @return
	 */
	@RequestMapping(value = "loan_sys_admin", method = RequestMethod.GET)
	public String toLoan_sys_admin() {
		return "managerWork/loan_sys_admin";
	}

	/**
	 * 
	 * <p>
	 * 描述：分页查询 系统管理员Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午4:34:41 <br>
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "pageLoan_sysAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Page<LoanSysAdminEntity> pageLoan_sysAdmin(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {
		return managerService
				.pageEntitys(LoanSysAdminEntity.class, currentPage);
	}

	/**
	 * 
	 * <p>
	 * 描述：批量删除 系统管理员Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:07:29 <br>
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteLoan_sysAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteLoan_sysAdmin(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		logger.info("删除系统管理员配置（ids={}）...", ids);
		return deleteEntityByIds(LoanSysAdminEntity.class, ids, request);
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到新增系统管理员配置页面
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:15:46 <br>
	 * @param id
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_sys_admin_new", method = RequestMethod.GET)
	public String toLoan_sys_admin_new(
			@RequestParam(value = "id", required = false) Long id,
			ModelMap modelMap) throws Exception {
		LoanSysAdminEntity entity = null;
		if (null != id) {
			entity = managerService.getEntityById(LoanSysAdminEntity.class, id);
		}
		modelMap.put("entity", entity);
		return "managerWork/loan_sys_admin_new";
	}

	/**
	 * 
	 * <p>
	 * 描述：保存 新增系统管理员配置
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:25:46 <br>
	 * @param entity
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loan_sys_admin_new", method = RequestMethod.POST)
	@ResponseBody
	public Message saveLoan_sys_admin_new(LoanSysAdminEntity entity,
			HttpServletRequest request) throws Exception {
		logger.info("保存新增系统管理员配置");
		logger.info(entity.toString());
		Message message = null;
		try {
			User user = (User) request.getSession().getAttribute(
					IConstant.SESSION_USER);
			if (null == entity.getId()) {
				entity.setCreateBy(user.getUsername());
			} else {
				entity.setLastupdateBy(user.getUsername());
			}

			managerService.saveOrUpdate(entity);
			message = Message.success("保存成功！");
		} catch (Exception e) {
			message = Message.failure("保存操作失败", e);
		}

		logger.info(message.getResult());
		return message;
	}

	// 【系统管理员配置】结束
	// ************************************//
	/**
	 * 
	 * <p>
	 * 描述：根据ID，删除配置记录
	 * </p>
	 * 
	 * @Date 2018年5月24日上午10:23:19 <br>
	 * @param clazz
	 *            要删除的Entity类
	 * @param ids
	 *            要删除的ID集合
	 * @param request
	 * @return
	 */
	private <T extends BaseEntity> Message deleteEntityByIds(Class<T> clazz,
			String ids, HttpServletRequest request) {
		Message message = null;
		if (StringUtils.isEmpty(ids)) {
			message = Message.failure("没有要删除的选项");
		} else {
			String[] temp = ids.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (int i = 0, j = temp.length; i < j; i++) {
				idList.add(Long.valueOf(temp[i].trim()));
			}
			try {
				User user = (User) request.getSession().getAttribute(
						IConstant.SESSION_USER);
				managerService.deleteEntityByIds(clazz, idList,
						user.getUsername());
				message = Message.success("删除操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				message = Message.failure("删除操作失败", e);
			}
		}
		logger.info(message.getResult());
		return message;
	}
}
