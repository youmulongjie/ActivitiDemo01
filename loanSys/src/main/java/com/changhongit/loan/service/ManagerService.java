/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

import java.util.List;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.entity.LoanApproverConfigEntity;
import com.changhongit.loan.entity.LoanBranchAccountingEntity;
import com.changhongit.loan.entity.LoanBranchCashierEntity;
import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;
import com.changhongit.loan.entity.LoanPlatformMinisterEntity;
import com.changhongit.loan.entity.LoanSysAdminEntity;
import com.changhongit.loan.entity.LoanToAccountingEntity;
import com.changhongit.loan.entity.LoanToInvoiceEntity;

/**
 * <p>
 * 描述：业务管理Service层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:55:25
 */
public interface ManagerService extends BaseService {
	/**
	 * 
	 * <p>
	 * 描述：根据职位查询 有效的 “固定审批人配置”记录
	 * </p>
	 * 
	 * @Date 2018年5月17日下午4:24:06 <br>
	 * @param position
	 *            职位
	 * @return
	 */
	public LoanApproverConfigEntity findByPosition(String position);

	/**
	 * 
	 * <p>
	 * 描述：保存 “固定审批人配置”记录
	 * </p>
	 * 
	 * @Date 2018年5月17日下午4:25:03 <br>
	 * @param list
	 * @param username 操作人
	 */
	public void saveOrUpdateLoanApproverConfigEntitys(
			List<LoanApproverConfigEntity> list, String username);

	/**
	 * 
	 * <p>
	 * 描述：新增 OU
	 * </p>
	 * 
	 * @Date 2018年5月18日下午3:43:20 <br>
	 * @param ou
	 *            新增OU简称
	 * @param username
	 *            新增人
	 * @throws Exception
	 */
	public void saveLoanSpecialOuEntity(String ou, String username)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：保存或更新 最长清欠期限配置Entity
	 * </p>
	 * 
	 * @Date 2018年5月21日下午4:50:09 <br>
	 * @param entity
	 *            最长清欠期限配置Entity 对象
	 * @param username
	 *            操作人
	 */
	public Message saveLoanMaxDurationConfigEntity(
			LoanMaxDurationConfigEntity entity, String username);
	
	/**
	 * 
	 * <p>
	 * 描述：保存或更新 借款单对应发票类型Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午2:01:06 <br>
	 * @param entity
	 *            借款单对应发票类型Entity
	 * @param username
	 *            操作人
	 * @return
	 */
	public Message saveLoanToInvoiceEntity(LoanToInvoiceEntity entity,
			String username);

	/**
	 * 
	 * <p>
	 * 描述：根据借款类型，获取对应的发票类型Entity
	 * </p>
	 * 
	 * @Date 2018年6月14日下午2:13:52 <br>
	 * @param loanType
	 *            借款类型
	 * @return
	 */
	public LoanToInvoiceEntity findInvoiceByLoanType(String loanType)
			throws Exception;

	/**
	 * <p>
	 * 描述：保存或更新 借款类型对应会计Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午4:20:01 <br>
	 * @param entity
	 *            要保存或更新的借款类型对应会计Entity
	 * @param username
	 *            操作人
	 * @return
	 */
	public Message saveLoanToAccountingEntity(LoanToAccountingEntity entity,
			String username);

	/**
	 * 
	 * <p>
	 * 描述：根据 借款类型，获取对应的会计Entity
	 * </p>
	 * 
	 * @Date 2018年6月14日下午1:44:09 <br>
	 * @param loanType
	 *            借款类型
	 * @return
	 */
	public LoanToAccountingEntity findAccountingByLoanType(String loanType)
			throws Exception;

	/**
	 * <p>
	 * 描述：保存或更新 分公司出纳配置Entity
	 * </p>
	 * 
	 * @Date 2018年5月22日下午5:18:30 <br>
	 * @param entity
	 *            要保存或更新的分公司出纳配置Entity
	 * @param username
	 *            操作人
	 * @return
	 */
	public Message saveLoanBranchCashierEntity(LoanBranchCashierEntity entity,
			String username);

	/**
	 * 
	 * <p>
	 * 描述：根据分公司名称，获取对应的出纳Entity
	 * </p>
	 * 
	 * @Date 2018年6月14日下午2:06:20 <br>
	 * @param branchName
	 *            分支名称
	 * @return
	 */
	public LoanBranchCashierEntity findBranchCashierByBranchName(
			String branchName) throws Exception;

	/**
	 * <p>
	 * 描述：保存或更新 分公司会计配置Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:19:25 <br>
	 * @param entity
	 *            要保存或更新的分公司会计配置Entity
	 * @param username
	 *            操作人
	 * @return
	 */
	public Message saveLoanBranchAccountingEntity(
			LoanBranchAccountingEntity entity, String username);

	/**
	 * 
	 * <p>
	 * 描述：根据分公司名称，获取对应的会计配置Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午3:20:41 <br>
	 * @param branchName
	 *            分支名称
	 * @return
	 * @throws Exception
	 */
	public LoanBranchAccountingEntity findBranchAccountingByBranchName(
			String branchName) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：根据中文名称，获取系统管理员Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午4:20:30 <br>
	 * @param username
	 *            中文名
	 * @return
	 */
	public LoanSysAdminEntity findSysAdminEntityByUsername(String username);
	
	/**
	 * 
	 * <p>
	 * 描述：保存或更新 平台部长Entity
	 * </p>
	 * 
	 * @Date 2018年7月10日下午2:16:09 <br>
	 * @param entity
	 *            平台部长Entity 对象
	 * @param username
	 *            操作人
	 */
	public Message saveLoanPlatformMinisterEntity(
			LoanPlatformMinisterEntity entity, String username);
}
