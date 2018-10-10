/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.changhongit.loan.dao.LoanMaxDurationConfigDao;
import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;

/**
 * <p>
 * 描述：最长清欠期限配置 Dao层接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月21日下午1:32:18
 */
@Repository
public class LoanMaxDurationConfigDaoImpl extends
		BaseDaoImpl<LoanMaxDurationConfigEntity> implements
		LoanMaxDurationConfigDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.LoanMaxDurationConfigDao#findOneByLoanType(java
	 * .lang.String)
	 */
	@Override
	public LoanMaxDurationConfigEntity findOneByLoanType(String loanType) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(LoanMaxDurationConfigEntity.class);
		// 查询有效记录
		criteria.add(Restrictions.eq("loanType", loanType));
		criteria.add(Restrictions.eq("status", 1));

		@SuppressWarnings("unchecked")
		List<LoanMaxDurationConfigEntity> list = (List<LoanMaxDurationConfigEntity>) hibernateTemplate
				.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanMaxDurationConfigEntity();
	}

}
