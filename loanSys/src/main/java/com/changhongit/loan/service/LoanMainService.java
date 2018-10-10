/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

import java.math.BigDecimal;
import java.util.List;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.Page;
import com.changhongit.loan.bean.QueryBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.entity.AttachmentEntity;
import com.changhongit.loan.entity.LoanMainEntity;

/**
 * <p>
 * 描述：借款主表Entity Service层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月4日下午12:32:54
 */
public interface LoanMainService extends BaseService {
	/**
	 * 
	 * <p>
	 * 描述：生成 申请单编号
	 * </p>
	 * 
	 * @Date 2018年6月21日下午3:51:08 <br>
	 */
	public String applyNumber();

	/**
	 * 
	 * <p>
	 * 描述：创建申请
	 * </p>
	 * 
	 * @Date 2018年6月7日上午11:04:23 <br>
	 * @param loanMainEntity
	 *            借款主表Entity对象
	 * @param sessionUser
	 *            session中登陆者User对象
	 * @throws Exception
	 */
	@Deprecated
	public void createApply(LoanMainEntity loanMainEntity, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：创建申请
	 * </p>
	 * 
	 * @Date 2018年8月17日下午3:55:33 <br>
	 * @param loanMainEntity
	 *            借款主表Entity对象
	 * @param attachmentEntity
	 *            附件Entity对象 集合
	 * @param sessionUser
	 *            session中登陆者User对象
	 * @throws Exception
	 */
	public void createApply(LoanMainEntity loanMainEntity,
			List<AttachmentEntity> attachmentEntitys, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：更新 申请
	 * </p>
	 * 
	 * @Date 2018年7月2日上午10:29:55 <br>
	 * @param loanMainEntity
	 *            借款主表Entity对象
	 * @param sessionUser
	 *            session中登陆者User对象
	 * @throws Exception
	 */
	@Deprecated
	public void updateApply(LoanMainEntity loanMainEntity, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：更新 申请
	 * </p>
	 * 
	 * @Date 2018年8月17日下午4:32:07 <br>
	 * @param loanMainEntity
	 *            借款主表Entity对象
	 * @param attachmentEntity
	 *            附件Entity对象 集合
	 * @param sessionUser
	 *            session中登陆者User对象
	 * @throws Exception
	 */
	public void updateApply(LoanMainEntity loanMainEntity,
			List<AttachmentEntity> attachmentEntitys, User sessionUser)
			throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：分页查询 审批中的记录集合
	 * </p>
	 * 
	 * @Date 2018年6月12日下午3:04:07 <br>
	 * @param currentPage
	 *            当前页数
	 * @param sessionUsername
	 *            登录人姓名
	 * @param queryBean
	 *            查询条件Bean
	 * @return
	 */
	public Page<LoanMainEntity> pageProgressEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean);

	/**
	 * 
	 * <p>
	 * 描述：分页查询 待办的记录集合
	 * </p>
	 * 
	 * @Date 2018年6月13日上午9:33:34 <br>
	 * @param currentPage
	 *            当前页数
	 * @param sessionUsername
	 *            登录人姓名
	 * @param queryBean
	 *            查询条件Bean
	 * @return
	 */
	public Page<LoanMainEntity> pageToDoEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean);

	/**
	 * 
	 * <p>
	 * 描述：查询 登陆者的待办个数
	 * </p>
	 * 
	 * @Date 2018年6月13日上午11:06:57 <br>
	 * @param sessionUsername
	 *            登录人姓名
	 * @return
	 */
	public Integer todoCount(String sessionUsername);

	/**
	 * 
	 * <p>
	 * 描述：分页查询 流程结束的记录集合
	 * </p>
	 * 
	 * @Date 2018年6月21日上午11:30:07 <br>
	 * @param currentPage
	 *            当前页数
	 * @param sessionUsername
	 *            登录人姓名
	 * @param queryBean
	 *            查询条件Bean
	 * @return
	 */
	public Page<LoanMainEntity> pageEndEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean);

	/**
	 * 
	 * <p>
	 * 描述：分页查询 草稿箱的记录集合
	 * </p>
	 * 
	 * @Date 2018年6月28日下午5:33:47 <br>
	 * @param currentPage
	 *            当前页数
	 * @param sessionUsername
	 *            登录人姓名
	 * @param queryBean
	 *            查询条件Bean
	 * @return
	 */
	public Page<LoanMainEntity> pageDraftEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean);

	/**
	 * 
	 * <p>
	 * 描述：查询流程是否结束
	 * </p>
	 * 
	 * @Date 2018年6月21日下午2:04:26 <br>
	 * @param id
	 * @return
	 */
	public boolean isEnd(Long id);

	/**
	 * 
	 * <p>
	 * 描述：更新 凭证号
	 * </p>
	 * 
	 * @Date 2018年6月25日上午10:17:49 <br>
	 * @param mainEntity
	 *            LoanMainEntity实体，设置 pzNumber、lastupdateBy、lastupdateDate列
	 * @return
	 * @throws Exception
	 */
	public boolean updatePZNumber(LoanMainEntity mainEntity) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：保存附件Entity
	 * </p>
	 * 
	 * @Date 2018年6月25日下午4:16:22 <br>
	 * @param entity
	 * @throws Exception
	 */
	public void saveAttachmentEntity(AttachmentEntity entity) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：根据 mainID 查询附件列表
	 * </p>
	 * 
	 * @Date 2018年6月25日下午4:25:12 <br>
	 * @param mainId
	 * @return
	 */
	public List<AttachmentEntity> getAttachmentEntitiesByMainId(Long mainId);

	/**
	 * <p>
	 * 描述：撤销 申请
	 * </p>
	 * 
	 * @Date 2018年7月2日下午3:36:24 <br>
	 * @param entity
	 * @param operatorName
	 */
	void revokeApply(LoanMainEntity entity, String operatorName);

	/**
	 * 
	 * <p>
	 * 描述：催办
	 * </p>
	 * 
	 * @Date 2018年7月2日下午4:28:36 <br>
	 * @param mainId
	 * @throws Exception
	 */
	void urge(Long mainId) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：生成预付款发票
	 * </p>
	 * 
	 * @Date 2018年7月19日上午11:33:21 <br>
	 * @param maidId
	 * @return
	 */
	Message createInvoice(Long mainId) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述：分页查询 管理员查看所有的记录集合
	 * </p>
	 * 
	 * @Date 2018年9月10日上午10:54:17 <br>
	 * @param currentPage
	 *            当前页数
	 * @param sessionUser
	 *            登录人
	 * @param queryBean
	 *            查询条件Bean
	 * @return
	 */
	public Page<LoanMainEntity> pageManageEntitys(int currentPage,
			User sessionUser, QueryBean queryBean);

	/**
	 * 
	 * <p>
	 * 描述：安全认证（当前操作人 是否 包含 登陆人姓名）
	 * </p>
	 * 
	 * @Date 2018年9月10日下午5:19:10 <br>
	 * @param currentApprover
	 *            当前操作人
	 * @param sessionUsername
	 *            登录人姓名
	 * @return
	 */
	public boolean securityVerification(String currentApprover,
			String sessionUsername);
	
	/**
	 * 
	 * <p>
	 * 描述：获取 创建发票失败的 申请单记录ID集合
	 * </p>
	 * 
	 * @Date 2018年9月11日上午10:24:34 <br>
	 * @return
	 */
	public List<BigDecimal> getFailureInvoiceList();

}
