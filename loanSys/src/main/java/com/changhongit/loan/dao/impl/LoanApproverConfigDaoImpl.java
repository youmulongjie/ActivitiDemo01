/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.changhongit.loan.dao.LoanApproverConfigDao;
import com.changhongit.loan.entity.LoanApproverConfigEntity;

/**
 * <p>
 * 描述：固定审批人配置 接口 实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:49:08
 */
@Repository
public class LoanApproverConfigDaoImpl extends
		BaseDaoImpl<LoanApproverConfigEntity> implements LoanApproverConfigDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.LoanApproverConfigDao#findByPosition(java.lang
	 * .String)
	 */
	@Override
	public LoanApproverConfigEntity findByPosition(String position) {
		// TODO Auto-generated method stub
		String hql = "from LoanApproverConfigEntity t where t.position = ? and t.status = 1";
		@SuppressWarnings("unchecked")
		List<LoanApproverConfigEntity> list = (List<LoanApproverConfigEntity>) hibernateTemplate
				.find(hql, position);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new LoanApproverConfigEntity();
	}

}
