/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.changhongit.bean.OperatingUnitsDataBean;
import com.changhongit.loan.bean.Message;
import com.changhongit.loan.dao.LoanApproverConfigDao;
import com.changhongit.loan.dao.LoanBranchAccountingDao;
import com.changhongit.loan.dao.LoanBranchCashierDao;
import com.changhongit.loan.dao.LoanMaxDurationConfigDao;
import com.changhongit.loan.dao.LoanPlatformMinisterDao;
import com.changhongit.loan.dao.LoanSpecialOuDao;
import com.changhongit.loan.dao.LoanSysAdminDao;
import com.changhongit.loan.dao.LoanToAccountingDao;
import com.changhongit.loan.dao.LoanToInvoiceDao;
import com.changhongit.loan.entity.LoanApproverConfigEntity;
import com.changhongit.loan.entity.LoanBranchAccountingEntity;
import com.changhongit.loan.entity.LoanBranchCashierEntity;
import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;
import com.changhongit.loan.entity.LoanPlatformMinisterEntity;
import com.changhongit.loan.entity.LoanSpecialOuEntity;
import com.changhongit.loan.entity.LoanSysAdminEntity;
import com.changhongit.loan.entity.LoanToAccountingEntity;
import com.changhongit.loan.entity.LoanToInvoiceEntity;
import com.changhongit.loan.service.ManagerService;
import com.changhongit.loan.util.InitUtil;

/**
 * <p>
 * 描述：业务管理Service层接口 实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:57:54
 */
@Service
@Transactional
public class ManagerServiceImpl extends BaseServiceImpl implements
		ManagerService {
	@Autowired
	private LoanApproverConfigDao loanApproverConfigDao;
	@Autowired
	private LoanSpecialOuDao loanSpecialOuDao;
	@Autowired
	private LoanMaxDurationConfigDao loanMaxDurationConfigDao;
	@Autowired
	private LoanToInvoiceDao loanToInvoiceDao;
	@Autowired
	private LoanToAccountingDao loanToAccountingDao;
	@Autowired
	private LoanBranchCashierDao loanBranchCashierDao;
	@Autowired
	private LoanBranchAccountingDao loanBranchAccountingDao;
	@Autowired
	private LoanSysAdminDao loanSysAdminDao;
	@Autowired
	private LoanPlatformMinisterDao loanPlatformMinisterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findByPosition(java.lang.
	 * String)
	 */
	@Override
	public LoanApproverConfigEntity findByPosition(String position) {
		// TODO Auto-generated method stub
		return loanApproverConfigDao.findByPosition(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.ManagerService#
	 * saveOrUpdateLoanApproverConfigEntitys(java.util.List)
	 */
	@Override
	public void saveOrUpdateLoanApproverConfigEntitys(
			List<LoanApproverConfigEntity> list, String username) {
		// TODO Auto-generated method stub
		LoanApproverConfigEntity entity = null;
		LoanApproverConfigEntity temp = null;
		for (int i = 0, j = list.size(); i < j; i++) {
			entity = list.get(i);
			temp = loanApproverConfigDao.findByPosition(entity.getPosition());
			if (StringUtils.isEmpty(temp.getName())) {
				// 没有记录，保存
				entity.setCreateBy(username);
				loanApproverConfigDao.save(entity);
			} else {
				// 有记录，更新
				temp.setName(entity.getName());
				temp.setLastupdateDate(new Date());
				temp.setLastupdateBy(username);
				loanApproverConfigDao.update(temp);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanSpecialOuEntity(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public void saveLoanSpecialOuEntity(String ou, String username)
			throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanSpecialOuEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("ou", ou));

		List<LoanSpecialOuEntity> list = loanSpecialOuDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			// 已存在有效记录，不操作
		} else {
			// 不存在，新增操作
			LoanSpecialOuEntity entity = new LoanSpecialOuEntity();
			entity.setOu(ou);

			OperatingUnitsDataBean bean = null;
			List<OperatingUnitsDataBean> ouList = InitUtil.getOulist();
			for (int i = 0, j = ouList.size(); i < j; i++) {
				bean = ouList.get(i);
				if (bean.getOuName().equals(ou)) {
					break;
				}
			}

			entity.setOuId(bean.getOrganizationId());
			entity.setOuMap(InitUtil.ouMap.get(ou) == null ? ""
					: InitUtil.ouMap.get(ou));
			entity.setCreateBy(username);
			entity.setLastupdateBy(username);
			loanSpecialOuDao.save(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanMaxDurationConfigEntity
	 * (com.changhongit.loan.entity.LoanMaxDurationConfigEntity,
	 * java.lang.String)
	 */
	@Override
	public Message saveLoanMaxDurationConfigEntity(
			LoanMaxDurationConfigEntity entity, String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMaxDurationConfigEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", entity.getLoanType()));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanMaxDurationConfigEntity> list = loanMaxDurationConfigDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效：借款类型为【" + entity.getLoanType()
						+ "】已存在 最长清欠期限配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanMaxDurationConfigDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {

					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanMaxDurationConfigDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						// 更新自己记录的内容
						entity.setLastupdateBy(username);
						loanMaxDurationConfigDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效：借款类型为【"
								+ entity.getLoanType() + "】已存在 最长清欠期限配置！");
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanToInvoiceEntity(com
	 * .changhongit.loan.entity.LoanToInvoiceEntity, java.lang.String)
	 */
	@Override
	public Message saveLoanToInvoiceEntity(LoanToInvoiceEntity entity,
			String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanToInvoiceEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", entity.getLoanType()));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanToInvoiceEntity> list = loanToInvoiceDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效：借款类型为【" + entity.getLoanType()
						+ "】已存在 发票类型配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanToInvoiceDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {

					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanToInvoiceDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						// 更新自己记录的内容
						entity.setLastupdateBy(username);
						loanToInvoiceDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效：借款类型为【"
								+ entity.getLoanType() + "】已存在 发票类型配置！");
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findInvoiceByLoanType(java
	 * .lang.String)
	 */
	@Override
	public LoanToInvoiceEntity findInvoiceByLoanType(String loanType)
			throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanToInvoiceEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", loanType));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanToInvoiceEntity> list = loanToInvoiceDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanToInvoiceEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanToAccountingEntity
	 * (com.changhongit.loan.entity.LoanToAccountingEntity, java.lang.String)
	 */
	@Override
	public Message saveLoanToAccountingEntity(LoanToAccountingEntity entity,
			String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanToAccountingEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", entity.getLoanType()));
		criteria.add(Restrictions.eq("status", 1));
		List<LoanToAccountingEntity> list = loanToAccountingDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效：借款类型为【" + entity.getLoanType()
						+ "】已存在 对应会计配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanToAccountingDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {

					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanToAccountingDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						// 更新自己记录的内容
						entity.setLastupdateBy(username);
						loanToAccountingDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效：借款类型为【"
								+ entity.getLoanType() + "】已存在 对应会计配置！");
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findAccountingByLoanType(
	 * java.lang. String)
	 */
	@Override
	public LoanToAccountingEntity findAccountingByLoanType(String loanType)
			throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanToAccountingEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", loanType));
		criteria.add(Restrictions.eq("status", 1));
		List<LoanToAccountingEntity> list = loanToAccountingDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanToAccountingEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanBranchCashierEntity
	 * (com.changhongit.loan.entity.LoanBranchCashierEntity, java.lang.String)
	 */
	@Override
	public Message saveLoanBranchCashierEntity(LoanBranchCashierEntity entity,
			String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanBranchCashierEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("branch", entity.getBranch()));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanBranchCashierEntity> list = loanBranchCashierDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效: 分公司名称为【" + entity.getBranch()
						+ "】已存在 分公司出纳配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanBranchCashierDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {

					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanBranchCashierDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						entity.setLastupdateBy(username);
						loanBranchCashierDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效: 分公司名称为【"
								+ entity.getBranch() + "】已存在 分公司出纳配置！");
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findBranchCashierByBranchName
	 * (java.lang.String)
	 */
	@Override
	public LoanBranchCashierEntity findBranchCashierByBranchName(
			String branchName) throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanBranchCashierEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("branch", branchName));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanBranchCashierEntity> list = loanBranchCashierDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanBranchCashierEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanBranchAccountingEntity
	 * (com.changhongit.loan.entity.LoanBranchAccountingEntity,
	 * java.lang.String)
	 */
	@Override
	public Message saveLoanBranchAccountingEntity(
			LoanBranchAccountingEntity entity, String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanBranchAccountingEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("branch", entity.getBranch()));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanBranchAccountingEntity> list = loanBranchAccountingDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效: 分公司名称为【" + entity.getBranch()
						+ "】已存在 分公司会计配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanBranchAccountingDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {
					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanBranchAccountingDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						entity.setLastupdateBy(username);
						loanBranchAccountingDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效: 分公司名称为【"
								+ entity.getBranch() + "】已存在 分公司会计配置！");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findBranchAccountingByBranchName
	 * (java.lang.String)
	 */
	@Override
	public LoanBranchAccountingEntity findBranchAccountingByBranchName(
			String branchName) throws Exception {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanBranchAccountingEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("branch", branchName));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanBranchAccountingEntity> list = loanBranchAccountingDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanBranchAccountingEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#findSysAdminEntityByUsername
	 * (java.lang.String)
	 */
	@Override
	public LoanSysAdminEntity findSysAdminEntityByUsername(String username) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanSysAdminEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanSysAdminEntity> list = loanSysAdminDao
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.ManagerService#saveLoanPlatformMinisterEntity
	 * (com.changhongit.loan.entity.LoanPlatformMinisterEntity,
	 * java.lang.String)
	 */
	@Override
	public Message saveLoanPlatformMinisterEntity(
			LoanPlatformMinisterEntity entity, String username) {
		// TODO Auto-generated method stub
		Message message = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanPlatformMinisterEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("platformName", entity.getPlatformName()));
		criteria.add(Restrictions.eq("status", 1));

		List<LoanPlatformMinisterEntity> list = loanPlatformMinisterDao
				.findByCriteria(criteria);

		if (null == entity.getId()) {
			// ID为空，保存操作
			if (null != list && list.size() > 0) {
				// 已存在有效记录，不操作
				message = Message.failure("操作无效: 平台部门名称为【"
						+ entity.getPlatformName() + "】已存在 平台部长配置！");
			} else {
				// 不存在，新增操作
				try {
					entity.setCreateBy(username);
					loanPlatformMinisterDao.save(entity);
					message = Message.success("保存成功！");
				} catch (Exception e) {
					message = Message.failure("保存操作失败", e);
				}
			}
		} else {
			// ID不为空，更新操作
			try {
				if (null == list || list.size() == 0) {
					entity.setLastupdateBy(username);
					loanPlatformMinisterDao.merge(entity);
					message = Message.success("更新成功！");
				} else {
					if (list.get(0).getId() == entity.getId()) {
						entity.setLastupdateBy(username);
						loanPlatformMinisterDao.merge(entity);
						message = Message.success("更新成功！");
					} else {
						// 已存在有效记录，不操作
						message = Message.failure("操作无效: 平台部门名称为【"
								+ entity.getPlatformName() + "】已存在 平台部长配置！");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = Message.failure("更新操作失败", e);
			}
		}

		return message;
	}

}
