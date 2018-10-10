/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.bean.workflowparam<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.bean.workflowparam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhongit.HRModule.hr.CuxHrSupervisorRec;
import com.changhongit.loan.bean.OrganizationLeaderBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：审批流程中 获取参数工具类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月11日上午9:34:11
 */
public class WorkFlowParamUtil {

	private LoanMainEntity loanMainEntity;
	private User sessionUser;

	/**
	 * 
	 */
	public WorkFlowParamUtil() {
		super();
	}

	/**
	 * @param loanMainEntity
	 * @param sessionUser
	 */
	public WorkFlowParamUtil(LoanMainEntity loanMainEntity, User sessionUser) {
		super();
		this.loanMainEntity = loanMainEntity;
		this.sessionUser = sessionUser;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 审批流中 申请人信息
	 * </p>
	 * 
	 * @Date 2018年6月11日上午9:40:12 <br>
	 * @return
	 */
	public WorkFlowApplyerParam getApplyerParam() {
		WorkFlowApplyerParam applyerParam = new WorkFlowApplyerParam();
		applyerParam.setUserName(loanMainEntity.getPerson());
		applyerParam.setErpName(sessionUser.getErpName());
		applyerParam.setErpNum(sessionUser.getErpNum());
		applyerParam.setDept(loanMainEntity.getDept());
		applyerParam.setPosition(loanMainEntity.getPosition());

		// 设置 是否是北京员工
		String ou = sessionUser.getOu();
		if (ou != null && !ou.equals("") && ou.contains("分公司")) {
			applyerParam.setBeijing(false);
		} else {
			applyerParam.setBeijing(true);
		}

		applyerParam.setAssignmentNumber(loanMainEntity.getAssignNum());

		// 申请人所在的领导链层级 后续（还没想好怎么用这个属性）
		// applyerParam.setLeaderIn(leaderIn);

		return applyerParam;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 审批流中 领导链信息
	 * </p>
	 * 
	 * @Date 2018年6月11日上午9:43:57 <br>
	 * @return
	 */
	public WorkFlowLeaderParam getLeaderParam() {
		WorkFlowLeaderParam leaderParam = new WorkFlowLeaderParam();

		// 如果list.size=1(直接领导是祝总)，则启动直接领导为祝总的工作流;否则启动普通员工的工作流
		List<CuxHrSupervisorRec> leaderList = new ArrayList<CuxHrSupervisorRec>();

		String ou = sessionUser.getOu();
		boolean isBeijing = false;
		if (ou != null && !ou.equals("") && ou.contains("分公司")) {
			isBeijing = false;
		} else {
			isBeijing = true;
		}

		if (!isBeijing) {
			// 分公司员工，先取分总
			List<CuxHrSupervisorRec> fenzonglist = HrInfoUtil.getFenZong(
					String.valueOf(loanMainEntity.getErpNum()),
					loanMainEntity.getPosition());
			leaderList.addAll(fenzonglist);
		}

		List<CuxHrSupervisorRec> leaderList2 = HrInfoUtil.getPersonLeader(
				loanMainEntity.getAssignNum(), 10L);
		leaderList.addAll(leaderList2);

		// 人员是业务部门还是平台部门
		String orgTypeName = HrInfoUtil.platformOrBusiness(leaderList);
		// 去掉重复的领导
		List<CuxHrSupervisorRec> qcLeaderList = HrInfoUtil
				.getUnduplicateLeadInfoList(leaderList);

		int startNum;
		if (qcLeaderList != null
				&& "总裁".equals(qcLeaderList.get(0).getOrganizationTypeName())
				&& qcLeaderList.size() > 1) {
			startNum = 1;
		} else {
			startNum = 0;
		}
		// 取领导链（不包含直接领导）
		for (int i = startNum; i < qcLeaderList.size(); i++) {
			CuxHrSupervisorRec leaderInfo = qcLeaderList.get(i);
			String typeName = leaderInfo.getOrganizationTypeName();
			String leaderName = leaderInfo.getLastName();

			// 平台部门
			if (IConstant.ORGTYPENAME_PLATFORM_GROUP.equals(orgTypeName)) {
				if (typeName.equals(IConstant.platform_level5)) {
					// 平台部门--分管平台高管
					leaderParam.setLeader5(leaderName);
					continue;
				}
				if (typeName.equals(IConstant.platform_level3)) {
					// 平台部门--平台部门
					leaderParam.setLeader3(leaderName);
					continue;
				}
				if (typeName.equals(IConstant.platform_level2)) {
					// 平台部门--平台组
					leaderParam.setLeader2(leaderName);
					continue;
				}
			}
			// 业务部门
			if (IConstant.ORGTYPENAME_BUSINESS_GROUP.equals(orgTypeName)) {
				if (typeName.equals(IConstant.business_level5)) {
					// 业务部门--业务群
					leaderParam.setLeader5(leaderName);
					continue;
				}
				if (typeName.equals(IConstant.business_level4)) {
					// 业务部门--本部
					leaderParam.setLeader4(leaderName);
					continue;
				}
				if (typeName.equals(IConstant.business_level3)) {
					// 业务部门--事业部
					leaderParam.setLeader3(leaderName);
					continue;
				}
				if (typeName.equals(IConstant.business_level2)) {
					// 业务部门--业务部
					leaderParam.setLeader2(leaderName);
					continue;
				}
			}
			if (typeName.equals("分公司")) {
				leaderParam.setFenZong(leaderName);
				continue;
			}

		}

		// 取 跨部门的领导链信息
		String replaceLeader3 = null;
		String replaceLeader4 = null;
		String replaceLeader5 = null;
		if (!loanMainEntity.getDepartmentLoan().equals("是")
				&& !StringUtils.isEmpty(loanMainEntity.getReplaceDept())) {
			// 跨部门借款 部门
			String replaceDape = loanMainEntity.getReplaceDept();
			List<OrganizationLeaderBean> list = HrInfoUtil
					.getLeaderByOrganizationName(replaceDape);

			// 取部门领导链
			OrganizationLeaderBean temp = null;
			String organizationTypeName = null;
			for (int i = 0, j = list.size(); i < j; i++) {
				temp = list.get(i);
				organizationTypeName = temp.getOrganizationTypeName();
				if (organizationTypeName.equals(IConstant.business_level3)
						|| organizationTypeName
								.equals(IConstant.platform_level3)) {
					replaceLeader3 = temp.getLastName();
				} else if (organizationTypeName
						.equals(IConstant.business_level4)) {
					replaceLeader4 = temp.getLastName();
				} else if (organizationTypeName
						.equals(IConstant.business_level5)
						|| organizationTypeName
								.equals(IConstant.platform_level5)) {
					replaceLeader5 = temp.getLastName();
				}
			}

			// 2018-09-05 李媛提出需求：诉讼费+跨部门借款时，业务群总（replaceLeader5）不审批
			String loantype = loanMainEntity.getLoantype();
			if (loantype.equals(LoanTypeEnum.LitigationFee.getType())) {
				replaceLeader5 = "";
			}

		}

		List<String> leader3List = new ArrayList<String>();
		addList(leader3List, leaderParam.getLeader3());
		addList(leader3List, replaceLeader3);
		leaderParam.setLeader3List(leader3List);

		List<String> leader4List = new ArrayList<String>();
		addList(leader4List, leaderParam.getLeader4());
		addList(leader4List, replaceLeader4);
		leaderParam.setLeader4List(leader4List);

		List<String> leader5List = new ArrayList<String>();
		addList(leader5List, leaderParam.getLeader5());
		addList(leader5List, replaceLeader5);
		leaderParam.setLeader5List(leader5List);

		// 取总裁
		leaderParam.setZc(InitUtil.fixedApproverMap.get(FixedApproverEnum.总裁
				.name()));

		// 取直接领导
		if (!StringUtils.isEmpty(leaderParam.getLeader2())
				|| !StringUtils.isEmpty(leaderParam.getLeader3())) {
			// 存在 2级领导（业务部|平台组）或者 3级领导（事业部|平台部门），则无直接领导
			leaderParam.setLeader1("");
		} else {
			if (!StringUtils.isEmpty(leaderParam.getLeader4())) {
				// 4级领导（本部总）不为空，则将 本部总 设置为直接领导
				leaderParam.setLeader1(leaderParam.getLeader4());
			} else if (!StringUtils.isEmpty(leaderParam.getLeader5())) {
				// 5级领导（业务群|分管平台高管）不为空，则将 业务群总|分管平台高管 设置为直接领导
				leaderParam.setLeader1(leaderParam.getLeader5());
				leaderParam.setLeader5("");
			} else if (StringUtils.isEmpty(leaderParam.getLeader5())) {
				// 5级领导（业务群|分管平台高管）为空，则将 总裁 设置为直接领导
				leaderParam.setLeader1(leaderParam.getZc());
				leaderParam.setLeader5("");
			}
		}

		return leaderParam;
	}

	private void addList(List<String> list, String param) {
		if (!StringUtils.isEmpty(param) && !list.contains(param)) {
			list.add(param);
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 审批流中 业务数据信息
	 * </p>
	 * 
	 * @Date 2018年6月11日上午9:43:49 <br>
	 * @return
	 */
	public WorkFlowBusinessParam getBusinessParam() {
		WorkFlowBusinessParam businessParam = new WorkFlowBusinessParam();
		businessParam.setLoanType(loanMainEntity.getLoantype());
		businessParam.setBorrowingFlag(loanMainEntity.getDepartmentLoan()
				.equals("是") ? true : false);
		businessParam.setContractOrBudgetFlag(loanMainEntity
				.getContractOrBudget().equals("是") ? true : false);
		businessParam.setLoanMoney(loanMainEntity.getTotalMoney());
		businessParam.setReplaceDept(loanMainEntity.getReplaceDept());

		return businessParam;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 审批流程中 相关人员（财务、法务、固定人员等）参数Bean
	 * </p>
	 * 
	 * @Date 2018年6月14日上午10:53:14 <br>
	 * @return
	 */
	public WorkFlowRelatedParam getRelatedParam() {
		WorkFlowRelatedParam relatedParam = new WorkFlowRelatedParam();
		relatedParam.setCfo(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.财务总监.name()));
		relatedParam.setFinanceMinister(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.财务部长.name()));
		relatedParam.setCheck(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.财务复核.name()));
		relatedParam.setCashier(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.出纳.name()));
		relatedParam.setAccounting(InitUtil.fnAccountingMap.get(loanMainEntity
				.getLoantype()));
		relatedParam.setPlatformExecutives(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.平台高管.name()));
		relatedParam.setLawMinister(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.法务部部长.name()));
		relatedParam.setCeo(InitUtil.fixedApproverMap.get(FixedApproverEnum.总裁
				.name()));
		relatedParam.setMainPlatformMinister(InitUtil.fixedApproverMap
				.get(FixedApproverEnum.主责平台部长.name()));
		return relatedParam;
	}
}
