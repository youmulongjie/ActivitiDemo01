/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import jodd.datetime.JDateTime;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.changhongit.HRModule.hr.CuxHrEmployeePositionRec;
import com.changhongit.HRModule.hr.CuxHrEmployeeRec;
import com.changhongit.HRModule.hr.CuxHrOrgMaxSuperRec;
import com.changhongit.HRModule.hr.CuxHrSetComRec;
import com.changhongit.HRModule.hr.CuxHrSupervisorRec;
import com.changhongit.HRModule.hr.ResultCuxHrOrgLowBean;
import com.changhongit.HRModule.hr.ResultCuxHrOrgLowHigRec;
import com.changhongit.HRModule.orgstruc.CuxHrOrganizationRec;
import com.changhongit.HRModule.param.AllocNumberLevelBean;
import com.changhongit.HRModule.param.ErpNameDateBean;
import com.changhongit.HRModule.param.OrgStrucLevelBean;
import com.changhongit.HRModule.param.PersonIdBean;
import com.changhongit.bean.OperatingUnitsDataBean;
import com.changhongit.commontools.EncoderCompressUtil;
import com.changhongit.commontools.JSONUtils;
import com.changhongit.expense.bean.CuxOperatingUnits;
import com.changhongit.expense.bean.ResultBean;
import com.changhongit.expense.client.factory.EmployeeExpenseFactory;
import com.changhongit.expense.client.impl.ExpenseReimbursement;
import com.changhongit.factory.OperatingUnitsInfoFactory;
import com.changhongit.hrwsiclient.Hrwsimpl;
import com.changhongit.impl.OperatingUnitsInfo;
import com.changhongit.loan.bean.ConfigBean;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.OrganizationLeaderBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.bean.workflowparam.WorkFlowRelatedParam;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.dao.LoanMaxDurationConfigDao;
import com.changhongit.loan.dao.LoanOrganizationDao;
import com.changhongit.loan.dao.LoanSpecialOuDao;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;
import com.changhongit.loan.entity.LoanOrganizationEntity;
import com.changhongit.loan.enums.OuMapEnum;
import com.changhongit.loan.enums.RepaymentEnum;
import com.changhongit.loanInvoiceWS.client.LoanInvoiceWSClient;
import com.changhongit.loanInvoiceWS.ws.LoanInvoiceWS;
import com.changhongit.loanInvoiceWS.ws.PrepaymentBean;
import com.changhongit.orgServiceWS.client.GetSonOrgClient;
import com.changhongit.orgServiceWS.client.SonOrgListBean;
import com.changhongit.voucherno.bean.FapiaoVoucherNoDetail;
import com.changhongit.voucherno.client.factory.VoucherNoCreateFactory;
import com.changhongit.voucherno.client.impl.VoucherNoCreate;

/**
 * <p>
 * 描述：HR 接口实现工具类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月7日下午3:59:25
 */
@Component
public class HrInfoUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(HrInfoUtil.class);

	@Autowired
	private ConfigBean configBean;
	@Autowired
	private LoanOrganizationDao loanOrganizationDao;
	@Autowired
	private LoanSpecialOuDao loanSpecialOuDao;
	@Autowired
	private LoanMaxDurationConfigDao loanMaxDurationConfigDao;
	// 流程控制服务组件（管理流程在运行时产生的流程参数、事件、流程实例、以及执行流）
	@Autowired
	private HistoryService historyService;

	private static HrInfoUtil hrInfoUtil;

	@PostConstruct
	public void init() {
		hrInfoUtil = this;
		hrInfoUtil.configBean = this.configBean;
		hrInfoUtil.loanOrganizationDao = this.loanOrganizationDao;
		hrInfoUtil.loanSpecialOuDao = this.loanSpecialOuDao;
		hrInfoUtil.historyService = this.historyService;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取项目发布地址
	 * </p>
	 * 
	 * @Date 2018年6月13日下午3:40:22 <br>
	 * @return
	 */
	public static String publishAddr() {
		return hrInfoUtil.configBean.getPublishAddr();
	}

	/**
	 * 
	 * <p>
	 * 描述：查询 OU列表
	 * </p>
	 * 
	 * @Date 2018年5月11日上午10:06:55 <br>
	 * @return
	 * @throws Exception
	 */
	public static List<OperatingUnitsDataBean> getOUList() throws Exception {
		OperatingUnitsInfo o = OperatingUnitsInfoFactory
				.getOperatingUnitsInfoInstance(hrInfoUtil.configBean
						.getOuServiceWsdl());
		List<OperatingUnitsDataBean> list = o.getCuxOperatingUnitsInfoRec();
		return list;
	}

	/**
	 * 
	 * <p>
	 * 描述：查询员工基本信息
	 * </p>
	 * 
	 * @Date 2018年5月7日下午4:07:49 <br>
	 * @param erpNum
	 *            员工的ERP 编号
	 * @return
	 */
	public static CuxHrEmployeeRec getPersonInfo(String erpNum) {
		ErpNameDateBean bean = new ErpNameDateBean();
		bean.setErpName(erpNum);
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		List<CuxHrEmployeeRec> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl()).getPersonInfo(str);
		if (null != list && 0 != list.size()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 描述：查询员工 部门段值
	 * </p>
	 * 
	 * @Date 2018年7月18日下午3:38:47 <br>
	 * @param personId
	 * @return
	 */
	public static CuxHrSetComRec getPersonFinanceConfig(Long personId) {
		PersonIdBean bean = new PersonIdBean();
		bean.setPersonId(personId);
		bean.setDate(new Date());
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		List<CuxHrSetComRec> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl())
				.getPersonFinanceConfig(str);
		if (null != list && 0 != list.size()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 描述：查询员工的职位信息（职位为空或部门为空，则排除）
	 * </p>
	 * 
	 * @Date 2018年5月8日下午2:28:57 <br>
	 * @param erpNum
	 *            员工的ERP 编号
	 * @return
	 */
	public static List<CuxHrEmployeePositionRec> getPersonJobInfo(String erpNum) {
		return getPersonJobInfo(erpNum, true);
	}

	/**
	 * 
	 * <p>
	 * 描述：查询员工的职位信息，是否排除职位为空
	 * </p>
	 * 
	 * <pre>
	 * 最好的办法是让人事（吕翠）将申请人在ERP系统的职位信息补充完整（zhaona1 就是个案例，西安分公司的职位为空）
	 * 1、页面查询员工职位（下拉列表）时，设置为true;（否则级联部门时有问题）
	 * 2、取领导链分总时，设置为false;（否则取不到分总）
	 * </pre>
	 * 
	 * @Date 2018年9月7日下午2:59:10 <br>
	 * @param erpNum
	 *            员工的ERP 编号
	 * @param isRemoveNull
	 *            是否排除职位为空标识（true：排除；false：不排除）
	 * @return
	 */
	private static List<CuxHrEmployeePositionRec> getPersonJobInfo(
			String erpNum, boolean isRemoveNull) {
		ErpNameDateBean bean = new ErpNameDateBean();
		bean.setErpName(erpNum);
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		List<CuxHrEmployeePositionRec> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl()).getPersonJobInfo(str);

		if (isRemoveNull) {
			/**
			 * 2018-09-07 zhaona1 反馈 切换职位时，部门不会联动。测试发现zhaona1
			 * 有3个职位，且有一个职位为null，程序没有做为null的判断，所以切换职位遍历时报 空指针异常，故在此添加判断
			 */
			// list遍历时 remove 是一个学问，需要特别注意
			CuxHrEmployeePositionRec rec = null;
			Iterator<CuxHrEmployeePositionRec> it = list.iterator();
			while (it.hasNext()) {
				rec = it.next();
				// 职位 或 部门 为空，则去除
				if (StringUtils.isEmpty(rec.getOrganizationName())
						|| StringUtils.isEmpty(rec.getPositionName())) {
					it.remove();
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 描述：查询员工的领导信息
	 * </p>
	 * 
	 * @Date 2018年5月8日下午3:42:10 <br>
	 * @param assignNum
	 *            分配编号
	 * @param Level
	 *            查询层级，调用时填写10
	 * @return
	 */
	public static List<CuxHrSupervisorRec> getPersonLeader(String assignNum,
			Long Level) {
		AllocNumberLevelBean bean = new AllocNumberLevelBean();
		bean.setAllocNumber(assignNum);
		bean.setLevel(Level);
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		List<CuxHrSupervisorRec> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl()).getPersonLeader(str);
		return list;
	}

	/**
	 * 
	 * <p>
	 * 描述：查找 下级部门信息
	 * </p>
	 * 
	 * @Date 2018年5月8日下午4:03:27 <br>
	 * @param organizationId
	 *            组织Id
	 * @return
	 */
	public static List<ResultCuxHrOrgLowBean> getNextOrgStruc(
			long organizationId) {
		OrgStrucLevelBean bean = new OrgStrucLevelBean();
		bean.setOrgStrucId(organizationId);
		bean.setLevel(20L);
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		List<ResultCuxHrOrgLowBean> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl()).getNextOrgStruc(str);
		return list;
	}

	/**
	 * 
	 * <p>
	 * 描述：查找 上级部门信息
	 * </p>
	 * 
	 * @Date 2018年5月8日下午4:20:03 <br>
	 * @param organizationId
	 *            组织Id
	 * @return
	 */
	public static ResultCuxHrOrgLowHigRec getUpOrgStruc(long organizationId) {
		OrgStrucLevelBean bean = new OrgStrucLevelBean();
		bean.setOrgStrucId(organizationId);
		bean.setLevel(20l);
		String rmsg = JSONUtils.bean2Json(bean).toString();
		String str = EncoderCompressUtil.compressEncoder(rmsg);
		ResultCuxHrOrgLowHigRec resultBean = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl()).getUpOrgStruc(str);
		return resultBean;
	}

	/**
	 * 
	 * <p>
	 * 描述：查找 所有组织信息
	 * </p>
	 * 
	 * @Date 2018年5月25日上午10:13:12 <br>
	 * @return
	 */
	public static List<CuxHrOrganizationRec> getCuxHrOrganizationRecList() {
		return new Hrwsimpl(hrInfoUtil.configBean.getHrServiceWsdl())
				.getOrgStrucAll();
	}

	/**
	 * 
	 * <p>
	 * 描述：获取员工OU
	 * </p>
	 * 
	 * @Date 2018年5月14日上午10:22:28 <br>
	 * @param erpNum
	 *            员工ERP编号
	 * @return
	 * @throws Exception
	 */
	public static CuxOperatingUnits getOuByErpNumber(Long erpNum)
			throws Exception {
		ExpenseReimbursement e = EmployeeExpenseFactory
				.getExpenseReimbursementInstance(hrInfoUtil.configBean
						.getSifei_service_wsdl());
		return e.getEmployeeOu(erpNum);
	}

	/**
	 * 
	 * <p>
	 * 描述：获取员工邮箱
	 * </p>
	 * 
	 * @Date 2018年5月29日下午1:50:59 <br>
	 * @param userName
	 *            员工中文名
	 * @return
	 * @throws Exception
	 */
	public static String getUserEmail(String userName) throws Exception {
		ExpenseReimbursement e = EmployeeExpenseFactory
				.getExpenseReimbursementInstance(hrInfoUtil.configBean
						.getSifei_service_wsdl());
		return e.getUserEmail(null, null, userName, null).getResult();
	}

	/**
	 * 
	 * <p>
	 * 描述：获取所有的 组织库Entity中的事业部
	 * </p>
	 * 
	 * @Date 2018年5月28日上午9:42:41 <br>
	 * @return
	 */
	public static List<LoanOrganizationEntity> getLoanOrganizationEntities() {
		return hrInfoUtil.loanOrganizationDao.getAllLoanOrganizationEntitys();
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 业务部集合
	 * </p>
	 * 
	 * @Date 2018年5月30日下午3:06:35 <br>
	 * @param shiyebuName
	 *            事业部名称
	 * @return
	 * @throws Exception
	 */
	public static SonOrgListBean getYeWuBus(String shiyebuName)
			throws Exception {
		return GetSonOrgClient.getSonOrgListBean(
				hrInfoUtil.configBean.getOrgServiceWeb(), "事业部," + shiyebuName
						+ ",业务部门");
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 产品线 集合
	 * </p>
	 * 
	 * @Date 2018年5月30日下午3:07:51 <br>
	 * @param yewubuName
	 *            业务部名称
	 * @return
	 * @throws Exception
	 */
	public static SonOrgListBean getProduceLines(String yewubuName)
			throws Exception {
		return GetSonOrgClient.getSonOrgListBean(
				hrInfoUtil.configBean.getOrgServiceWeb(), "业务部," + yewubuName
						+ ",业务部门");
	}

	/**
	 * 
	 * <p>
	 * 描述：取 分总 审批人
	 * </p>
	 * 
	 * @Date 2018年6月11日上午9:29:06 <br>
	 * @param erpNum
	 *            员工ERP编号
	 * @param position
	 *            申请职位
	 * @return
	 */
	public static List<CuxHrSupervisorRec> getFenZong(String erpNum,
			String position) {
		List<CuxHrSupervisorRec> returnlist = new ArrayList<CuxHrSupervisorRec>();

		String assignmentNumber = "";
		String assigNumberPrimary = "";

		List<CuxHrEmployeePositionRec> list = getPersonJobInfo(erpNum, false);

		// 将组织名称、组织类型、组织类型名称、组织Id、分配编号拼接成一个字符串
		String pinjie = "";
		CuxHrEmployeePositionRec positionRec = null;
		for (int i = 0, j = list.size(); i < j; i++) {
			positionRec = list.get(i);
			pinjie = positionRec.getOrganizationName()
					+ positionRec.getOrganizationType()
					+ positionRec.getOrganizationTypeName()
					+ positionRec.getOrganizationId()
					+ positionRec.getAssignmentNumber();
			if (pinjie.contains("分公司")) {
				assignmentNumber = positionRec.getAssignmentNumber();
				if (!StringUtils.isEmpty(assignmentNumber)) {
					List<CuxHrSupervisorRec> supervisorRecs = getPersonLeader(
							assignmentNumber, 10L);
					for (CuxHrSupervisorRec rec : supervisorRecs) {
						if (rec.getOrganizationTypeName().equals("分公司")) {
							if (rec != null
									&& rec.getOrganizationTypeName() != null
									&& !"".equals(rec.getOrganizationTypeName())) {
								returnlist.add(rec);
								break;
							}
						}
					}
				}
				if (position.contains("分公司")
						&& "Y".equals(positionRec.getPrimaryFlag()
								.toUpperCase())) {
					assigNumberPrimary = positionRec.getAssignmentNumber();
				}
			}
		}

		if (!StringUtils.isEmpty(assigNumberPrimary)) {
			List<CuxHrSupervisorRec> supervisorRecs = getPersonLeader(
					assigNumberPrimary, 10L);
			if (supervisorRecs != null) {
				returnlist.addAll(supervisorRecs);
			}
		}

		return returnlist;
	}

	/**
	 * 
	 * <p>
	 * 描述：判断 申请人 是平台部门还是业务部门
	 * </p>
	 * 
	 * @Date 2018年6月11日上午10:18:53 <br>
	 * @param leaderList
	 * @return
	 */
	public static String platformOrBusiness(List<CuxHrSupervisorRec> leaderList) {
		String orgTypeName = IConstant.ORGTYPENAME_BUSINESS_GROUP;
		for (CuxHrSupervisorRec CuxHrSupervisorRec : leaderList) {
			if (IConstant.platformPositionSet.contains(CuxHrSupervisorRec
					.getOrganizationTypeName())) {
				orgTypeName = IConstant.ORGTYPENAME_PLATFORM_GROUP;
				break;
			}
		}

		if (leaderList.size() == 1) { // 直属领导是总裁
			orgTypeName = IConstant.ORGTYPENAME_PLATFORM_GROUP;
		}

		if (orgTypeName.equals(IConstant.ORGTYPENAME_PLATFORM_GROUP)) {
			List<String> orgTypeNames = new ArrayList<String>();
			for (CuxHrSupervisorRec CuxHrSupervisorRec : leaderList) {
				orgTypeNames.add(CuxHrSupervisorRec.getOrganizationTypeName());
				if (IConstant.businessPositionSet.contains(CuxHrSupervisorRec
						.getOrganizationTypeName())) {
					orgTypeName = IConstant.ORGTYPENAME_BUSINESS_GROUP;
					break;
				}
			}
		}
		return orgTypeName;
	}

	/**
	 * 
	 * <p>
	 * 描述：领导链去重 保留兼职领导的高级职位
	 * </p>
	 * 
	 * @Date 2018年6月11日上午10:20:27 <br>
	 * @param leaderList
	 * @return
	 */
	public static List<CuxHrSupervisorRec> getUnduplicateLeadInfoList(
			List<CuxHrSupervisorRec> leaderList) {
		List<CuxHrSupervisorRec> leadInfoNameList = new ArrayList<CuxHrSupervisorRec>();
		List<CuxHrSupervisorRec> leadInfoPositionList = new ArrayList<CuxHrSupervisorRec>();
		Set<String> unDuplicateNameSet = new HashSet<String>();
		Set<String> unDuplicatePositionSet = new HashSet<String>();
		for (int i = leaderList.size() - 1; i > -1; i--) { // 根据部门去重
			CuxHrSupervisorRec leaderInfo = leaderList.get(i);
			if (!unDuplicatePositionSet.contains(leaderInfo
					.getOrganizationTypeName())) {
				unDuplicatePositionSet
						.add(leaderInfo.getOrganizationTypeName());
				leadInfoPositionList.add(leaderInfo);
			}
		}
		for (int i = 0; i < leadInfoPositionList.size(); i++) { // 根据名字去重
			CuxHrSupervisorRec leaderInfo = leadInfoPositionList.get(i);
			if (!unDuplicateNameSet.contains(leaderInfo.getUserName())) {
				unDuplicateNameSet.add(leaderInfo.getUserName());
				leadInfoNameList.add(leaderInfo);
			}
		}

		return leadInfoNameList;
	}

	/**
	 * 
	 * <p>
	 * 描述：根据用户中文名获取邮箱地址
	 * </p>
	 * 
	 * @Date 2018年6月13日下午3:05:22 <br>
	 * @param userName
	 *            用户中文名
	 * @return
	 * @throws Exception
	 */
	public static String getMailByUserName(String userName) throws Exception {
		String mail = "";
		ExpenseReimbursement e1 = EmployeeExpenseFactory
				.getExpenseReimbursementInstance(hrInfoUtil.configBean
						.getSifei_service_wsdl());
		ResultBean bean = e1.getUserEmail(null, null, userName, null);
		if (bean != null) {
			mail = bean.getResult();
		}
		return mail;
	}

	/**
	 * 
	 * <p>
	 * 描述：生成 凭证号
	 * </p>
	 * 
	 * @Date 2018年6月22日下午5:09:13 <br>
	 * @param sessionUser
	 * @param mainEntity
	 * @return
	 * @throws Exception
	 */
	public static String createPzNum(User sessionUser, LoanMainEntity mainEntity)
			throws Exception {
		String pzNumber = "";
		try {
			VoucherNoCreate dd = VoucherNoCreateFactory
					.getCustomerChaoqiInfoInstance(hrInfoUtil.configBean
							.getVoucherNoWsdl());
			FapiaoVoucherNoDetail param = new FapiaoVoucherNoDetail();
			// OU名称 全写，以及OUID
			String ouName = InitUtil.ouMap.get(mainEntity.getOu());
			String ouId = String.valueOf(hrInfoUtil.loanSpecialOuDao
					.getEntityByOu(ouName).getOuId());
			param.setOuId(ouId);
			param.setOuName(ouName);
			param.setCaiwuCname(sessionUser.getUsername()); // 财务中文名
			param.setCaiwuErpName(sessionUser.getErpName().toUpperCase()); // 财务ERPName
			param.setCaiwuAbbreviation(sessionUser.getUsernameJC()); // 财务简称
			param.setFapiaoType("应付发票"); // 发票类型(预付发票/应付发票)
			param.setFapiaoAbbreviation("YF");// 发票类型简称(YF/E)
			param.setApprovalCode(mainEntity.getLoanNumber()); // 审批系统单号(唯一)
			param.setApprovalName("借款审批系统"); // 审批系统名称
			param.setApprovalAbbreviation("JKSPXT"); // 审批系统简称（NewCCSQ）
			param.setApprovalLink(hrInfoUtil.configBean.getPublishAddr()); // 审批系统链接
			param.setServerIp("localhost"); // 服务器IP
			param.setVoucherNo(sessionUser.getErpNum()); // 操作人
			param.setStatus(1L);
			param.setCreateBy(sessionUser.getErpName());
			param.setAttribute1("JK");// 20170912新增系统简称
			pzNumber = dd.create(param);
		} catch (Exception e) {
			throw new Exception("生成凭证号失败：" + e.getMessage());
		}
		return pzNumber;
	}

	/**
	 * 
	 * <p>
	 * 描述：根据部门名称 获取 组织库Entity
	 * </p>
	 * 
	 * @Date 2018年7月12日下午3:36:52 <br>
	 * @param organizationName
	 *            部门名称
	 * @return
	 */
	public static LoanOrganizationEntity getOrganizationEntity(
			String organizationName) {
		return hrInfoUtil.loanOrganizationDao
				.getOneByOrganizationName(organizationName);
	}

	/**
	 * 
	 * <p>
	 * 描述：根据 组织ID 获取领导信息
	 * </p>
	 * 
	 * @Date 2018年7月12日下午3:37:28 <br>
	 * @param orgId
	 *            组织ID
	 * @return
	 */
	public static CuxHrOrgMaxSuperRec getCuxHrOrgMaxSuperRecList(Long orgId) {
		String str = EncoderCompressUtil.compressEncoder(String.valueOf(orgId));
		List<CuxHrOrgMaxSuperRec> list = new Hrwsimpl(
				hrInfoUtil.configBean.getHrServiceWsdl())
				.getHrOrgHighSupervisor(str);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 描述：根据 部门名称 获取领导链信息（跨部门借款时，获取跨部门的领导链信息，包含事业部总、本部总、分管高管）
	 * </p>
	 * 
	 * @Date 2018年7月12日下午3:38:08 <br>
	 * @param organizationName
	 *            部门名称（借款所属部门）
	 * @return
	 */
	public static List<OrganizationLeaderBean> getLeaderByOrganizationName(
			String organizationName) {
		List<OrganizationLeaderBean> list = new ArrayList<OrganizationLeaderBean>();
		OrganizationLeaderBean bean = null;

		LoanOrganizationEntity entity = getOrganizationEntity(organizationName);
		Long organizationId = entity.getOrganizationId();

		// 本级领导信息
		CuxHrOrgMaxSuperRec rec = getCuxHrOrgMaxSuperRecList(organizationId);
		if (null != rec) {
			bean = new OrganizationLeaderBean(rec.getEmailAddress(),
					rec.getLastName(), rec.getUserName(), rec.getPositionName());
			bean.setOrganizationTypeName(entity.getOrganizationTypeName());
			list.add(bean);
		}

		// 上级部门
		ResultCuxHrOrgLowHigRec higRec = getUpOrgStruc(organizationId);
		rec = getCuxHrOrgMaxSuperRecList(higRec.getHighOrg()
				.getOrganizationId());
		if (null != rec) {
			bean = new OrganizationLeaderBean(rec.getEmailAddress(),
					rec.getLastName(), rec.getUserName(), rec.getPositionName());
			bean.setOrganizationTypeName(higRec.getOrganizationTypeName());
			list.add(bean);
		}

		// 上级部门
		higRec = getUpOrgStruc(higRec.getHighOrg().getOrganizationId());
		rec = getCuxHrOrgMaxSuperRecList(higRec.getParentOrganizationId());
		if (null != rec) {
			bean = new OrganizationLeaderBean(rec.getEmailAddress(),
					rec.getLastName(), rec.getUserName(), rec.getPositionName());
			bean.setOrganizationTypeName(higRec.getOrganizationTypeName());
			list.add(bean);
		}

		// 领导按姓名去重，保留高职位信息
		return removalDuplicate(list);
	}

	// 领导按姓名去重，保留高职位信息
	private static List<OrganizationLeaderBean> removalDuplicate(
			List<OrganizationLeaderBean> list) {
		OrganizationLeaderBean bean = null;
		Set<String> nameSet = new HashSet<String>();
		for (int i = 0, j = list.size(); i < j; i++) {
			bean = list.get(i);
			nameSet.add(bean.getLastName());
		}

		if (nameSet.size() == list.size()) {
			return list;
		} else {
			List<OrganizationLeaderBean> tempList = new ArrayList<OrganizationLeaderBean>();
			List<String> tempNameList = new ArrayList<String>();
			for (int i = 0, j = list.size() - 1; i < j; j--) {
				bean = list.get(j);
				if (!tempNameList.contains(bean.getLastName())) {
					tempNameList.add(bean.getLastName());
					tempList.add(bean);
				} else {
					continue;
				}
			}
			return tempList;
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：创建发票
	 * </p>
	 * 
	 * @Date 2018年7月17日下午4:55:09 <br>
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static Message createInvoice(LoanMainEntity entity) throws Exception {
		try {
			LoanInvoiceWS loanInvoiceWS = LoanInvoiceWSClient
					.createClient(hrInfoUtil.configBean.getLoanInvoiceWS());
			PrepaymentBean prepaymentBean = buildPrepaymentBean(entity);
			logger.info("发票参数Bean：{}", prepaymentBean.toString());
			com.changhongit.loanInvoiceWS.ws.Message source = loanInvoiceWS
					.createInvoice(prepaymentBean);
			Message target = new Message();
			BeanUtils.copyProperties(source, target);
			return target;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("发票创建失败：" + e.getMessage());
		}
	}

	// 构建创建发票参数Bean
	private static PrepaymentBean buildPrepaymentBean(LoanMainEntity entity) {
		PrepaymentBean bean = new PrepaymentBean();
		bean.setUserName(entity.getPerson());
		bean.setUserNum(entity.getErpNum());
		bean.setInvoiceNum(entity.getLoanNumber());
		bean.setInvoiceAmount(entity.getTotalMoney());
		JDateTime dateTime = new JDateTime(entity.getShenpiEndDate());
		bean.setCreateDate(dateTime.toString("YYYY-MM-DD"));
		bean.setDescription(entity.getLoanExplain());
		bean.setPayType(InitUtil.invoiceMap.get(entity.getLoantype()));
		// bean.setPayType("员工借款_周转备用金类"); // 测试用
		bean.setPlanDate(setPlanDate(entity));// 预计还款时间
		bean.setOuName(setOuName(entity));// OU
		bean.setDep(setDep(entity.getErpNum()));// 部门段
		bean.setShiyebu(entity.getReplaceDept());
		bean.setYewubu(entity.getReplaceBusiness());
		bean.setCpx(entity.getReplaceLine());
		bean.setCurrencyCode(entity.getCny());
		bean.setVoucherNum(entity.getPzNumber());
		return bean;
	}

	// 取 发票OU
	private static String setOuName(LoanMainEntity entity) {
		// 创建申请时 选择是OU
		String ou = entity.getOu();
		if (!ou.equals(OuMapEnum.信产.name())) {
			// 不是 “信产”OU，则取 全称
			return InitUtil.ouMap.get(ou);
		} else {
			// 是 “信产”OU，判断是否转“分公司”；转“分公司”则取分公司OU，没有转，则取“信息产品”OU
			// 获取 审批流程中 相关人员（财务、法务、固定人员等）参数Bean，并修改参数
			HistoricVariableInstance historicVariableInstance = hrInfoUtil.historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(entity.getProcinstId())
					.variableName("relatedParam").singleResult();
			WorkFlowRelatedParam relatedParam = (WorkFlowRelatedParam) historicVariableInstance
					.getValue();

			// 取流程参数：分支机构
			String branch = relatedParam.getBranch();
			if (StringUtils.isEmpty(branch)) {
				// 分支机构为空，则没有转“分公司”
				return InitUtil.ouMap.get(OuMapEnum.信产.name());
			} else {
				// 分支机构不为空，则取 转“分公司”的OU
				return InitUtil.ouMap.get(OuMapEnum.信产.name()) + branch;
			}
		}
	}

	// 取 预计还款时间
	private static String setPlanDate(LoanMainEntity entity) {
		LoanMaxDurationConfigEntity durationConfigEntity = hrInfoUtil.loanMaxDurationConfigDao
				.findOneByLoanType(entity.getLoantype());
		// 还款类型、最长清欠期限
		String repaymentType = durationConfigEntity.getRepaymentType();
		String maxDuration = durationConfigEntity.getMaxDuration();

		String planDate = null;
		JDateTime dateTime = null;
		if (repaymentType.equals(RepaymentEnum.GenerateInvoices.getType())) {
			// 生成发票日期加N天 规则：审批结束时间（借款时间）+ 最长清欠期限
			Date date = entity.getShenpiEndDate();
			dateTime = new JDateTime(date);
			dateTime.addDay(Integer.parseInt(maxDuration));
		} else if (repaymentType.equals(RepaymentEnum.LastDayOfYear.getType())) {
			// 提交日期当年最后一天 规则：当年最后一天
			dateTime = new JDateTime(getYearLast(Calendar.getInstance().get(
					Calendar.YEAR)));
		} else if (repaymentType.equals(RepaymentEnum.SpecifiedDate.getType())) {
			// 指定日期加N天 规则：预计还款日（只有 ‘押金、质保金类’才有该属性）+ 最长清欠期限
			String date = entity.getPlanDate();
			dateTime = new JDateTime(date);
			dateTime.addDay(Integer.parseInt(maxDuration));
		}
		planDate = dateTime.toString("YYYY-MM-DD");
		return planDate;
	}

	// 获取年 最后一天
	private static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	// 获取 发票部门段 值
	private static String setDep(Long erpNum) {
		CuxHrEmployeeRec rec = getPersonInfo(erpNum + "");
		Long personId = rec.getPersonId();

		CuxHrSetComRec cuxHrSetComRec = getPersonFinanceConfig(personId);
		return cuxHrSetComRec.getSegment2();
	}

}
