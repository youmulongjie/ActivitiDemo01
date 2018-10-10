/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.changhongit.loan.dao.LoanOrganizationDao;
import com.changhongit.loan.entity.LoanOrganizationEntity;

/**
 * <p>
 * 描述：组织库 Dao层接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:43:09
 */
@Repository
public class LoanOrganizationDaoImpl implements LoanOrganizationDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.LoanOrganizationDao#deleteAll()
	 */
	@Override
	public Integer deleteAll() {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				// TODO Auto-generated method stub
				return session.createSQLQuery("delete from loan_organization")
						.executeUpdate();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.LoanOrganizationDao#save(com.changhongit.loan
	 * .entity.LoanOrganizationEntity)
	 */
	@Override
	public void save(LoanOrganizationEntity entity) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.LoanOrganizationDao#getShiYeBuList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanOrganizationEntity> getAllLoanOrganizationEntitys() {
		// TODO Auto-generated method stub
		final String hql = "FROM LoanOrganizationEntity t WHERE t.organizationType IN (?,?,?) ORDER BY nlssort(organizationName, 'NLS_SORT=SCHINESE_PINYIN_M')";
		return hibernateTemplate
				.execute(new HibernateCallback<List<LoanOrganizationEntity>>() {

					@Override
					public List<LoanOrganizationEntity> doInHibernate(
							Session session) throws HibernateException {
						// TODO Auto-generated method stub
						Query query = session.createQuery(hql);
						query.setString(0, "平台部门");
						query.setString(1, "DEP");
						query.setString(2, "事业部");
						return query.list();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.LoanOrganizationDao#getOneByOrganizationName
	 * (java.lang.String)
	 */
	@Override
	public LoanOrganizationEntity getOneByOrganizationName(
			String organizationName) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(LoanOrganizationEntity.class,
				organizationName);
	}
}
