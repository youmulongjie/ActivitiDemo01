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

import com.changhongit.loan.dao.LoanSpecialOuDao;
import com.changhongit.loan.entity.LoanSpecialOuEntity;

/**
 * <p>
 * 描述：专用OU 接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月18日上午10:40:08
 */
@Repository
public class LoanSpecialOuDaoImpl extends BaseDaoImpl<LoanSpecialOuEntity>
		implements LoanSpecialOuDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.LoanSpecialOuDao#getEntityByOu(java.lang.String)
	 */
	@Override
	public LoanSpecialOuEntity getEntityByOu(String ou) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<LoanSpecialOuEntity> list = (List<LoanSpecialOuEntity>) hibernateTemplate
				.find("from LoanSpecialOuEntity t where t.status = 1 and t.ou = ?",
						ou);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
