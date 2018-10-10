/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import jodd.datetime.JDateTime;

import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.Page;
import com.changhongit.loan.bean.QueryBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.constant.ProcessTemplate;
import com.changhongit.loan.dao.AttachmentDao;
import com.changhongit.loan.dao.BaseDao;
import com.changhongit.loan.dao.LoanMainDao;
import com.changhongit.loan.entity.AttachmentEntity;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.enums.ApprovalStatus;
import com.changhongit.loan.enums.InvoiceStatusEnum;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.service.WorkflowService;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.loan.util.RemindMailTools;

/**
 * <p>
 * 描述：借款主表Entity Service层接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月4日下午12:35:07
 */
@Service
@Transactional
public class LoanMainServiceImpl extends BaseServiceImpl implements
		LoanMainService {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanMainServiceImpl.class);

	@Autowired
	private BaseDao<LoanMainEntity> baseDao;
	@Autowired
	private LoanMainDao loanMainDao;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private AttachmentDao attachmentDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#applyNumber()
	 */
	@Override
	public String applyNumber() {
		// TODO 生成 申请单编号

		// 月初清零序列
		String key = "JKSQ" + new JDateTime().toString("YYYYMM");
		Long count = new BigDecimal(
				loanMainDao
						.executeQuerySQL(
								"select count(1) from loan_main t where t.status = 1 and t.loannumber is not null and t.loannumber like ?",
								new Object[] { "" + key + "%" }).toString())
				.longValue();
		if (count == 0) {
			// 月初清零序列
			loanMainDao.executeVoidProcedure("{ call seq_reset_jk()}");
		}
		Integer squ = new BigDecimal(loanMainDao.executeQuerySQL(
				"select loan_main_number_s.nextval from dual").toString())
				.intValue();
		String sequences = "";
		if (squ < 10) {
			sequences += "000" + squ;
		} else if (squ >= 10 && squ < 100) {
			sequences += "00" + squ;
		} else if (squ >= 100 && squ < 1000) {
			sequences += "0" + squ;
		} else if (squ >= 1000 && squ < 10000) {
			sequences += +squ;
		}

		return MessageFormat.format(ProcessTemplate.loannumberTemplate,
				new JDateTime().toString("YYYYMM"), sequences);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#createApply(com.changhongit
	 * .loan .entity.LoanMainEntity)
	 */
	@Deprecated
	@Override
	public void createApply(LoanMainEntity loanMainEntity, User sessionUser)
			throws Exception {
		// TODO 创建申请单
		createApply(loanMainEntity, null, sessionUser);

	}

	private void saveAttach(List<AttachmentEntity> attachmentEntitys,
			Long mainId) throws Exception {
		if (attachmentEntitys.size() > 0) {
			logger.info("保存附件.");
			AttachmentEntity attachmentEntity = null;
			for (int i = 0, j = attachmentEntitys.size(); i < j; i++) {
				attachmentEntity = attachmentEntitys.get(i);

				logger.info("主表ID(mainid):{}, 附件ID(attachmentId):{}", mainId,
						attachmentEntity.getAttachmentId());

				attachmentEntity.setMainId(mainId);
				saveAttachmentEntity(attachmentEntity);
			}
			attachmentEntity = null;
			attachmentEntitys.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#createApply(com.changhongit
	 * .loan.entity.LoanMainEntity,
	 * com.changhongit.loan.entity.AttachmentEntity,
	 * com.changhongit.loan.bean.User)
	 */
	@Override
	public void createApply(LoanMainEntity loanMainEntity,
			List<AttachmentEntity> attachmentEntitys, User sessionUser)
			throws Exception {
		// TODO 创建申请单
		logger.info("创建申请单......");
		if (StringUtils.isEmpty(loanMainEntity.getLoanNumber())) {
			// 创建 申请单编号
			String loanNumber = applyNumber();
			loanMainEntity.setLoanNumber(loanNumber);
			logger.info("创建生成的申请单编号(loanNumber):{}", loanNumber);
		}
		// 保存Entity对象
		Serializable id = loanMainDao.save(loanMainEntity);
		loanMainEntity.setMainId((Long) id);
		logger.info("保存 借款主表Entity成功。生成的MAINID:{}", id);

		// 提交，则启动审批流（保存草稿不启动审批流）
		if (loanMainEntity.getDraftsFlag() == 1) {
			// 开启工作流
			loanMainEntity = workflowService.startProcess(loanMainEntity,
					sessionUser);
			loanMainDao.update(loanMainEntity);
		}

		saveAttach(attachmentEntitys, (Long) id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#updateApply(com.changhongit
	 * .loan.entity.LoanMainEntity, com.changhongit.loan.bean.User)
	 */
	@Override
	@Deprecated
	public void updateApply(LoanMainEntity loanMainEntity, User sessionUser)
			throws Exception {
		updateApply(loanMainEntity, null, sessionUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#updateApply(com.changhongit
	 * .loan.entity.LoanMainEntity,
	 * com.changhongit.loan.entity.AttachmentEntity,
	 * com.changhongit.loan.bean.User)
	 */
	@Override
	public void updateApply(LoanMainEntity loanMainEntity,
			List<AttachmentEntity> attachmentEntitys, User sessionUser)
			throws Exception {
		// TODO 更新 申请
		logger.info("更新申请单......");
		if (StringUtils.isEmpty(loanMainEntity.getLoanNumber())) {
			// 创建 申请单编号
			String loanNumber = applyNumber();
			loanMainEntity.setLoanNumber(loanNumber);
			logger.info("更新生成的申请单编号(loanNumber):{}", loanNumber);
		}

		loanMainDao.update(loanMainEntity);
		logger.info("更新 借款主表Entity成功。MAINID:{}", loanMainEntity.getMainId());
		// 提交，则启动审批流（保存草稿不启动审批流）
		if (loanMainEntity.getDraftsFlag() == 1) {
			// 开启工作流
			loanMainEntity = workflowService.startProcess(loanMainEntity,
					sessionUser);
			loanMainDao.update(loanMainEntity);
		}

		saveAttach(attachmentEntitys, (loanMainEntity.getMainId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#pageProgressEntitys(int,
	 * String,QueryBean)
	 */
	@Override
	public Page<LoanMainEntity> pageProgressEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean) {
		// TODO 分页查询 审批中的记录集合
		Page<LoanMainEntity> page = new Page<LoanMainEntity>();

		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 流程未结束的
		criteria.add(Restrictions.ne("currentWork", IConstant.END_LINK));
		// 页面查询条件
		setQueryBean(queryBean, criteria);
		// 登录人有权限的
		criteria.add(Restrictions.like("historyApprovers", "%"
				+ sessionUsername + "%,"));

		// ID排序
		criteria.addOrder(Order.desc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		List<LoanMainEntity> list = baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#pageToDoEntitys(int,
	 * java.lang.String, com.changhongit.loan.bean.QueryBean)
	 */
	@Override
	public Page<LoanMainEntity> pageToDoEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean) {
		// TODO 分页查询 待办的记录集合
		Page<LoanMainEntity> page = new Page<LoanMainEntity>();

		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 流程未结束的
		criteria.add(Restrictions.ne("currentWork", IConstant.END_LINK));
		// 当前任务审批人包含自己的
		criteria.add(Restrictions.like("currentApprover", "%" + sessionUsername
				+ "%"));
		// 页面查询条件
		setQueryBean(queryBean, criteria);
		// 历史审批人包含 登录人姓名的
		criteria.add(Restrictions.like("historyApprovers", "%"
				+ sessionUsername + "%,"));

		// ID排序
		criteria.addOrder(Order.desc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		List<LoanMainEntity> list = baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#todoCount(java.lang.String)
	 */
	@Override
	public Integer todoCount(String sessionUsername) {
		// TODO 查询 登陆者的待办个数
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 流程未结束的
		criteria.add(Restrictions.ne("currentWork", IConstant.END_LINK));
		// 当前任务审批人包含自己的
		criteria.add(Restrictions.like("currentApprover", "%" + sessionUsername
				+ "%"));
		// 历史审批人包含 登录人姓名的
		criteria.add(Restrictions.like("historyApprovers", "%"
				+ sessionUsername + ",%"));

		// 总行数
		return baseDao.getTotalCount(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#pageEndEntitys(int,
	 * java.lang.String, com.changhongit.loan.bean.QueryBean)
	 */
	@Override
	public Page<LoanMainEntity> pageEndEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean) {
		// TODO 分页查询 流程结束的记录集合
		Page<LoanMainEntity> page = new Page<LoanMainEntity>();

		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 流程结束的
		criteria.add(Restrictions.eq("currentWork", IConstant.END_LINK));
		// 页面查询条件
		setQueryBean(queryBean, criteria);
		// 登录人有权限的
		criteria.add(Restrictions.like("historyApprovers", "%"
				+ sessionUsername + "%,"));

		// ID排序
		criteria.addOrder(Order.desc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		List<LoanMainEntity> list = baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#pageDraftEntitys(int,
	 * java.lang.String, com.changhongit.loan.bean.QueryBean)
	 */
	@Override
	public Page<LoanMainEntity> pageDraftEntitys(int currentPage,
			String sessionUsername, QueryBean queryBean) {
		// TODO 分页查询 草稿箱的记录集合
		Page<LoanMainEntity> page = new Page<LoanMainEntity>();

		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 草稿箱
		criteria.add(Restrictions.eq("draftsFlag", 0));
		// 页面查询条件
		setQueryBean(queryBean, criteria);
		// 登录人有权限的
		criteria.add(Restrictions.eq("person", sessionUsername));

		// ID排序
		criteria.addOrder(Order.desc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		List<LoanMainEntity> list = baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/**
	 * <p>
	 * 描述：页面查询条件
	 * </p>
	 * 
	 * @Date 2018年6月27日下午5:09:21 <br>
	 * @param queryBean
	 * @param criteria
	 */
	private void setQueryBean(QueryBean queryBean, DetachedCriteria criteria) {
		if (null != queryBean) {
			String title = queryBean.getTitle();
			if (!StringUtils.isEmpty(title)) {
				criteria.add(Restrictions.like("title", "%" + title + "%"));
			}

			String person = queryBean.getPerson();
			if (!StringUtils.isEmpty(person)) {
				criteria.add(Restrictions.eq("person", person));
			}

			String currentWork = queryBean.getCurrentWork();
			if (!StringUtils.isEmpty(currentWork)) {
				criteria.add(Restrictions.eq("currentWork", currentWork));
			}

			String currentApprover = queryBean.getCurrentApprover();
			if (!StringUtils.isEmpty(currentApprover)) {
				criteria.add(Restrictions
						.eq("currentApprover", currentApprover));
			}

			JDateTime jDateTime = null;
			String beginDate = queryBean.getBeginDate();
			if (!StringUtils.isEmpty(beginDate)) {
				jDateTime = new JDateTime(beginDate, "YYYY-MM-DD");

				criteria.add(Restrictions.ge("applyDate",
						jDateTime.convertToDate()));
			}

			String endDate = queryBean.getEndDate();
			if (!StringUtils.isEmpty(endDate)) {
				jDateTime = new JDateTime(endDate, "YYYY-MM-DD");
				jDateTime.addDay(1);
				criteria.add(Restrictions.le("applyDate",
						jDateTime.convertToDate()));
			}

			String loantype = queryBean.getLoantype();
			if (!StringUtils.isEmpty(loantype)) {
				criteria.add(Restrictions.eq("loantype", loantype));
			}

			String procStatus = queryBean.getProcStatus();
			if (!StringUtils.isEmpty(procStatus)) {
				criteria.add(Restrictions.eq("procStatus", procStatus));
			}

			String loanNumber = queryBean.getLoanNumber();
			if (!StringUtils.isEmpty(loanNumber)) {
				criteria.add(Restrictions.eq("loanNumber", loanNumber));
			}

			String pzNumber = queryBean.getPzNumber();
			if (!StringUtils.isEmpty(pzNumber)) {
				criteria.add(Restrictions.eq("pzNumber", pzNumber));
			}

			String invoiceStatus = queryBean.getInvoiceStatus();
			if (!StringUtils.isEmpty(invoiceStatus)) {
				criteria.add(Restrictions.eq("invoiceStatus",
						Integer.parseInt(invoiceStatus)));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#isEnd(java.lang.Long)
	 */
	@Override
	public boolean isEnd(Long id) {
		// TODO 查询流程是否结束
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 查询有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 流程未结束的
		criteria.add(Restrictions.eq("currentWork", IConstant.END_LINK));
		// ID
		criteria.add(Restrictions.idEq(id));
		int count = baseDao.getTotalCount(criteria);
		return count == 0 ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#updatePZNumber(com.changhongit
	 * .loan.entity.LoanMainEntity)
	 */
	@Override
	public boolean updatePZNumber(LoanMainEntity mainEntity) throws Exception {
		// TODO 更新 凭证号
		if (StringUtils.isEmpty(mainEntity.getPzNumber())) {
			throw new Exception("凭证号为空！");
		}
		try {
			loanMainDao.update(mainEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#saveAttachmentEntity(com
	 * .changhongit.loan.entity.AttachmentEntity)
	 */
	@Override
	public void saveAttachmentEntity(AttachmentEntity entity) throws Exception {
		// TODO 保存附件Entity
		attachmentDao.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#getAttachmentEntitiesByMainId
	 * (java.lang.Long)
	 */
	@Override
	public List<AttachmentEntity> getAttachmentEntitiesByMainId(Long mainId) {
		// TODO 根据 mainID 查询附件列表
		return attachmentDao.getEntitiyListByMainId(mainId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#revokeApply(com.changhongit
	 * .loan.entity.LoanMainEntity, java.lang.String)
	 */
	@Override
	public void revokeApply(LoanMainEntity entity, String operatorName) {
		// TODO 撤销 申请
		workflowService.deleteProcinstById(entity.getProcinstId(),
				ApprovalStatus.已撤销.name());

		entity.setLastupdateBy(operatorName);
		entity.setLastupdateDate(new Date());

		entity.setProcinstId("");
		entity.setProcStatus(ApprovalStatus.已撤销.name());
		entity.setSuperiorApprover("");
		entity.setSuperiorApproveDate(null);
		entity.setCurrentApprover(entity.getCreateBy());
		entity.setCurrentWork(IConstant.END_LINK);

		loanMainDao.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#urge(java.lang.Long)
	 */
	@Override
	public void urge(Long mainId) throws Exception {
		// TODO 催办
		LoanMainEntity entity = getEntityById(LoanMainEntity.class, mainId);
		Task task = workflowService.getCurrentTask(entity.getProcinstId());

		String assignee = null;
		if (task.getName().equals(IConstant.FN_ACCOUNTING_LINK)
				&& StringUtils.isEmpty(task.getAssignee())) {
			assignee = InitUtil.fnAccountingMap.get(entity.getLoantype());
		} else {
			assignee = task.getAssignee();
		}

		RemindMailTools.sendTipMail(assignee, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#createInvoice(java.lang.
	 * Long)
	 */
	@Override
	public Message createInvoice(Long mainId) throws Exception {
		// TODO 生成预付款发票
		LoanMainEntity loanMainEntity = loanMainDao.findById(mainId);
		logger.info("生成预付款发票(mainId):{}", mainId);
		Message message = null;
		if (StringUtils.isEmpty(loanMainEntity.getInvoiceNum())) {
			// 还没有发票号，则创建
			message = HrInfoUtil.createInvoice(loanMainEntity);
			if (message.getCode().equals("success")) {
				// 发票创建成功
				String invoiceNum = (String) message.getData();

				loanMainEntity.setProcStatus(InvoiceStatusEnum.发票创建成功.name());
				loanMainEntity.setInvoiceStatus(InvoiceStatusEnum.发票创建成功
						.getValue());
				loanMainEntity.setInvoiceNum(invoiceNum);
				loanMainEntity.setInvoiceFailure("");
			} else {
				// 发票创建失败
				loanMainEntity.setProcStatus(InvoiceStatusEnum.发票创建失败.name());
				loanMainEntity.setInvoiceStatus(InvoiceStatusEnum.发票创建失败
						.getValue());
				loanMainEntity.setInvoiceFailure(message.getResult());
				loanMainEntity.setInvoiceNum("");
			}
			loanMainDao.update(loanMainEntity);
		} else {
			// 发票号已存在
			message = Message.failure("发票创建失败！已存在发票号："
					+ loanMainEntity.getInvoiceNum());
		}

		logger.info("创建发票结果：{}", message.toString());
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#pageManageEntitys(int,
	 * com.changhongit.loan.bean.User, com.changhongit.loan.bean.QueryBean)
	 */
	@Override
	public Page<LoanMainEntity> pageManageEntitys(int currentPage,
			User sessionUser, QueryBean queryBean) {
		// TODO 分页查询 管理员查看所有的记录集合
		Page<LoanMainEntity> page = new Page<LoanMainEntity>();
		// 不是管理员，不能查看
		if (!sessionUser.isAdministrator()) {
			return page;
		}

		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMainEntity.class);

		// 有效记录
		criteria.add(Restrictions.eq("status", 1));
		// 正式提交的（非草稿箱）
		criteria.add(Restrictions.eq("draftsFlag", 1));
		// 页面查询条件
		setQueryBean(queryBean, criteria);

		// ID排序
		criteria.addOrder(Order.desc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		List<LoanMainEntity> list = baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.LoanMainService#securityVerification(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public boolean securityVerification(String currentApprover,
			String sessionUsername) {
		// TODO 安全认证（当前操作人 是否 包含 登陆人姓名）
		return currentApprover.contains(sessionUsername);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanMainService#getFailureInvoiceList()
	 */
	@Override
	public List<BigDecimal> getFailureInvoiceList() {
		// TODO Auto-generated method stub
		String sql = "select t.mainid from loan_Main t where t.status = 1 and t.invoicestatus = 2 and t.procstatus = '发票创建失败'";
		return loanMainDao.executeQueryListSQL(sql);
	}

}
