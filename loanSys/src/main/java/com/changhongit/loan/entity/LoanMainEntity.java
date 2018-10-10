/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.entity<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 描述：借款主表Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日下午2:23:17
 */
@Entity
@Table(name = "loan_Main")
public class LoanMainEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_Main_s")
	@SequenceGenerator(name = "loan_Main_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_Main_s")
	@Column(name = "mainid")
	private Long mainId;

	/**
	 * 申请人
	 */
	@Column(name = "PERSON")
	private String person;
	/**
	 * erp 编号
	 */
	@Column(name = "ERPNUM")
	private Long erpNum;
	/**
	 * ERP 登陆名
	 */
	@Column(name = "ERPNAME")
	private String erpName;
	/**
	 * 申请人所在ou
	 */
	@Column(name = "PERSON_OU")
	private String personOu;
	/**
	 * （上级）所在部门（页面展示需要）
	 */
	@Column(name = "UP_DEPT")
	private String upDept;
	/**
	 * 所在部门
	 */
	@Column(name = "DEPT")
	private String dept;
	/**
	 * 职位
	 */
	@Column(name = "POSITION")
	private String position;
	/**
	 * 分配编号（由职位、所在部门决定）
	 */
	@Column(name = "ASSIGN_NUM")
	private String assignNum;
	/**
	 * 借款类型
	 */
	@Column(name = "LOANTYPE")
	private String loantype;
	/**
	 * 申请日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "APPLY_DATE")
	private Date applyDate;
	/**
	 * 所属分机构公司
	 */
	@Column(name = "BRANCH")
	private String branch;
	/**
	 * 是否本部门借款
	 */
	@Column(name = "DEPARTMENTLOAN")
	private String departmentLoan;
	/**
	 * 代替借款所属部门
	 */
	@Column(name = "REPLACE_DEPT")
	private String replaceDept;
	/**
	 * 代替借款所属业务部
	 */
	@Column(name = "REPLACE_BUSINESS")
	private String replaceBusiness;
	/**
	 * 代替借款所属产品线
	 */
	@Column(name = "REPLACE_LINE")
	private String replaceLine;
	/**
	 * 借款所属ou
	 */
	@Column(name = "OU")
	private String ou;
	/**
	 * 借款所属ou ID
	 */
	@Column(name = "OUID")
	private Long ouid;
	/**
	 * 币种（默认人民币）
	 */
	@Column(name = "CNY")
	private String cny;
	/**
	 * 借款用途
	 */
	@Column(name = "LOANEXPLAIN")
	private String loanExplain;
	/**
	 * 是否有合同或预算
	 */
	@Column(name = "CONTRACT_OR_BUDGET")
	private String contractOrBudget;
	/**
	 * 预计还款日
	 */
	@Column(name = "PLAN_DATE")
	private String planDate;
	/**
	 * 合计金额
	 */
	@Column(name = "TOTAL_MONEY")
	private Double totalMoney;
	/**
	 * 申请单编号
	 */
	@Column(name = "LOANNUMBER")
	private String loanNumber;
	/**
	 * 凭证号
	 */
	@Column(name = "PZNUMBER")
	private String pzNumber;
	/**
	 * 审批时间
	 */
	@Column(name = "SUPERIORAPPROVE_DATE")
	private Date superiorApproveDate;
	/**
	 * 当前审批人
	 */
	@Column(name = "CURRENTAPPROVER")
	private String currentApprover;
	/**
	 * 发票创建状态（1：成功；2：失败；默认为 0）
	 */
	@Column(name = "INVOICESTATUS")
	private int invoiceStatus = 0;
	/**
	 * 流程定义id（业务没用到）
	 */
	@Column(name = "PROCDEFID")
	private String procdefId;
	/**
	 * 流程实例id
	 */
	@Column(name = "PROCINSTID")
	private String procinstId;
	/**
	 * 流程状态
	 */
	@Column(name = "PROCSTATUS")
	private String procStatus;
	/**
	 * 上级审批人
	 */
	@Column(name = "SUPERIORAPPROVER")
	private String superiorApprover;
	/**
	 * 当前审批环节
	 */
	@Column(name = "CURRENTWORK")
	private String currentWork;
	/**
	 * 流程标题
	 */
	@Column(name = "TITLE")
	private String title;

	/**
	 * 历史审批人（以","隔开）
	 */
	@Column(name = "HISTORY_APPROVERS")
	private String historyApprovers;
	/**
	 * 是否草稿标识（1：非草稿箱；0：草稿箱），默认为1
	 */
	@Column(name = "DRAFTS_FLAG")
	private Integer draftsFlag = 1;

	/**
	 * 发票创建失败原因
	 */
	@Column(name = "INVOICE_FAILURE")
	private String invoiceFailure;

	/**
	 * 发票号
	 */
	@Column(name = "INVOICE_NUM")
	private String invoiceNum;

	/**
	 * 审批结束时间（借款时间）
	 */
	@Column(name = "SHENPI_END_DATE")
	private Date shenpiEndDate;

	/**
	 * 借款费用信息行表Entity（单项一对多）
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "MAINID")
	private List<LoanBursementEntity> bursementEntities = new ArrayList<LoanBursementEntity>();

	/**
	 * @return the person
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * @return the erpNum
	 */
	public Long getErpNum() {
		return erpNum;
	}

	/**
	 * @param erpNum
	 *            the erpNum to set
	 */
	public void setErpNum(Long erpNum) {
		this.erpNum = erpNum;
	}

	/**
	 * @return the erpName
	 */
	public String getErpName() {
		return erpName;
	}

	/**
	 * @param erpName
	 *            the erpName to set
	 */
	public void setErpName(String erpName) {
		this.erpName = erpName;
	}

	/**
	 * @return the personOu
	 */
	public String getPersonOu() {
		return personOu;
	}

	/**
	 * @param personOu
	 *            the personOu to set
	 */
	public void setPersonOu(String personOu) {
		this.personOu = personOu;
	}

	/**
	 * @return the upDept
	 */
	public String getUpDept() {
		return upDept;
	}

	/**
	 * @param upDept
	 *            the upDept to set
	 */
	public void setUpDept(String upDept) {
		this.upDept = upDept;
	}

	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the assignNum
	 */
	public String getAssignNum() {
		return assignNum;
	}

	/**
	 * @param assignNum
	 *            the assignNum to set
	 */
	public void setAssignNum(String assignNum) {
		this.assignNum = assignNum;
	}

	/**
	 * @return the loantype
	 */
	public String getLoantype() {
		return loantype;
	}

	/**
	 * @param loantype
	 *            the loantype to set
	 */
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	/**
	 * @return the applyDate
	 */
	public Date getApplyDate() {
		return applyDate;
	}

	/**
	 * @param applyDate
	 *            the applyDate to set
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the departmentLoan
	 */
	public String getDepartmentLoan() {
		return departmentLoan;
	}

	/**
	 * @param departmentLoan
	 *            the departmentLoan to set
	 */
	public void setDepartmentLoan(String departmentLoan) {
		this.departmentLoan = departmentLoan;
	}

	/**
	 * @return the replaceDept
	 */
	public String getReplaceDept() {
		return replaceDept;
	}

	/**
	 * @param replaceDept
	 *            the replaceDept to set
	 */
	public void setReplaceDept(String replaceDept) {
		this.replaceDept = replaceDept;
	}

	/**
	 * @return the replaceBusiness
	 */
	public String getReplaceBusiness() {
		return replaceBusiness;
	}

	/**
	 * @param replaceBusiness
	 *            the replaceBusiness to set
	 */
	public void setReplaceBusiness(String replaceBusiness) {
		this.replaceBusiness = replaceBusiness;
	}

	/**
	 * @return the replaceLine
	 */
	public String getReplaceLine() {
		return replaceLine;
	}

	/**
	 * @param replaceLine
	 *            the replaceLine to set
	 */
	public void setReplaceLine(String replaceLine) {
		this.replaceLine = replaceLine;
	}

	/**
	 * @return the ou
	 */
	public String getOu() {
		return ou;
	}

	/**
	 * @param ou
	 *            the ou to set
	 */
	public void setOu(String ou) {
		this.ou = ou;
	}

	/**
	 * @return the ouid
	 */
	public Long getOuid() {
		return ouid;
	}

	/**
	 * @param ouid
	 *            the ouid to set
	 */
	public void setOuid(Long ouid) {
		this.ouid = ouid;
	}

	/**
	 * @return the cny
	 */
	public String getCny() {
		return cny;
	}

	/**
	 * @param cny
	 *            the cny to set
	 */
	public void setCny(String cny) {
		this.cny = cny;
	}

	/**
	 * @return the loanExplain
	 */
	public String getLoanExplain() {
		return loanExplain;
	}

	/**
	 * @param loanExplain
	 *            the loanExplain to set
	 */
	public void setLoanExplain(String loanExplain) {
		this.loanExplain = loanExplain;
	}

	/**
	 * @return the contractOrBudget
	 */
	public String getContractOrBudget() {
		return contractOrBudget;
	}

	/**
	 * @param contractOrBudget
	 *            the contractOrBudget to set
	 */
	public void setContractOrBudget(String contractOrBudget) {
		this.contractOrBudget = contractOrBudget;
	}

	/**
	 * @return the loanNumber
	 */
	public String getLoanNumber() {
		return loanNumber;
	}

	/**
	 * @param loanNumber
	 *            the loanNumber to set
	 */
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	/**
	 * @return the pzNumber
	 */
	public String getPzNumber() {
		return pzNumber;
	}

	/**
	 * @param pzNumber
	 *            the pzNumber to set
	 */
	public void setPzNumber(String pzNumber) {
		this.pzNumber = pzNumber;
	}

	/**
	 * @return the procdefId
	 */
	public String getProcdefId() {
		return procdefId;
	}

	/**
	 * @param procdefId
	 *            the procdefId to set
	 */
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	/**
	 * @return the procinstId
	 */
	public String getProcinstId() {
		return procinstId;
	}

	/**
	 * @param procinst
	 *            the procinstId to set
	 */
	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}

	/**
	 * @return the superiorApprover
	 */
	public String getSuperiorApprover() {
		return superiorApprover;
	}

	/**
	 * @param superiorApprover
	 *            the superiorApprover to set
	 */
	public void setSuperiorApprover(String superiorApprover) {
		this.superiorApprover = superiorApprover;
	}

	/**
	 * @return the mainid
	 */
	public Long getMainId() {
		return mainId;
	}

	/**
	 * @param mainid
	 *            the mainid to set
	 */
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	/**
	 * @return the bursementEntities
	 */
	public List<LoanBursementEntity> getBursementEntities() {
		return bursementEntities;
	}

	/**
	 * @param bursementEntities
	 *            the bursementEntities to set
	 */
	public void setBursementEntities(List<LoanBursementEntity> bursementEntities) {
		this.bursementEntities = bursementEntities;
	}

	/**
	 * @return the planDate
	 */
	public String getPlanDate() {
		return planDate;
	}

	/**
	 * @param planDate
	 *            the planDate to set
	 */
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	/**
	 * @return the totalMoney
	 */
	public Double getTotalMoney() {
		return totalMoney;
	}

	/**
	 * @param totalMoney
	 *            the totalMoney to set
	 */
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * @return the superiorApproveDate
	 */
	public Date getSuperiorApproveDate() {
		return superiorApproveDate;
	}

	/**
	 * @param superiorApproveDate
	 *            the superiorApproveDate to set
	 */
	public void setSuperiorApproveDate(Date superiorApproveDate) {
		this.superiorApproveDate = superiorApproveDate;
	}

	/**
	 * @return the currentApprover
	 */
	public String getCurrentApprover() {
		return currentApprover;
	}

	/**
	 * @param currentApprover
	 *            the currentApprover to set
	 */
	public void setCurrentApprover(String currentApprover) {
		this.currentApprover = currentApprover;
	}

	/**
	 * @return the invoiceStatus
	 */
	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            the invoiceStatus to set
	 */
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the procStatus
	 */
	public String getProcStatus() {
		return procStatus;
	}

	/**
	 * @param procStatus
	 *            the procStatus to set
	 */
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	/**
	 * @return the currentWork
	 */
	public String getCurrentWork() {
		return currentWork;
	}

	/**
	 * @param currentWork
	 *            the currentWork to set
	 */
	public void setCurrentWork(String currentWork) {
		this.currentWork = currentWork;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the historyApprovers
	 */
	public String getHistoryApprovers() {
		return historyApprovers;
	}

	/**
	 * @param historyApprovers
	 *            the historyApprovers to set
	 */
	public void setHistoryApprovers(String historyApprovers) {
		this.historyApprovers = historyApprovers;
	}

	/**
	 * @return the draftsFlag
	 */
	public Integer getDraftsFlag() {
		return draftsFlag;
	}

	/**
	 * @param draftsFlag
	 *            the draftsFlag to set
	 */
	public void setDraftsFlag(Integer draftsFlag) {
		this.draftsFlag = draftsFlag;
	}

	/**
	 * @return the invoiceFailure
	 */
	public String getInvoiceFailure() {
		return invoiceFailure;
	}

	/**
	 * @param invoiceFailure
	 *            the invoiceFailure to set
	 */
	public void setInvoiceFailure(String invoiceFailure) {
		this.invoiceFailure = invoiceFailure;
	}

	/**
	 * @return the shenpiEndDate
	 */
	public Date getShenpiEndDate() {
		return shenpiEndDate;
	}

	/**
	 * @param shenpiEndDate
	 *            the shenpiEndDate to set
	 */
	public void setShenpiEndDate(Date shenpiEndDate) {
		this.shenpiEndDate = shenpiEndDate;
	}

	/**
	 * @return the invoiceNum
	 */
	public String getInvoiceNum() {
		return invoiceNum;
	}

	/**
	 * @param invoiceNum
	 *            the invoiceNum to set
	 */
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

}
