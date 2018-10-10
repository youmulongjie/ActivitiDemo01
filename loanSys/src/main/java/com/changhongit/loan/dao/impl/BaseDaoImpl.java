/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.changhongit.loan.dao.BaseDao;
import com.changhongit.loan.entity.BaseEntity;

/**
 * <p>
 * 描述：Dao层基类接口 实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午12:47:55
 */
@Repository
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// 获取子类对象的父类类型
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			this.entityClass = (Class<T>) ((ParameterizedType) type)
					.getActualTypeArguments()[0];
		} else {
			this.entityClass = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#save(java.lang.Object)
	 */
	@Override
	public Serializable save(T t) {
		// TODO Auto-generated method stub
		return hibernateTemplate.save(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#merge(com.changhongit.loan.entity.BaseEntity
	 * )
	 */
	@Override
	public void merge(T t) {
		// TODO Auto-generated method stub
		hibernateTemplate.merge(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		t.setStatus(0);
		hibernateTemplate.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		T entity = hibernateTemplate.load(entityClass, id);
		delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#findById(java.lang.Long)
	 */
	@Override
	public T findById(Long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#findById(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public <E extends BaseEntity> E findById(Class<E> clazz, Long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#findByCriteria(org.hibernate.criterion
	 * .DetachedCriteria)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria dc) {
		// TODO Auto-generated method stub
		return (List<T>) hibernateTemplate.findByCriteria(dc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#listAll()
	 */
	@Override
	public List<T> listAll() {
		// TODO Auto-generated method stub
		return hibernateTemplate.loadAll(entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#getTotalCount(org.hibernate.criterion
	 * .DetachedCriteria)
	 */
	@Override
	public Integer getTotalCount(DetachedCriteria dc) {
		// TODO Auto-generated method stub
		// 设置查询的聚合函数
		dc.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		List<Long> idList = (List<Long>) hibernateTemplate.findByCriteria(dc);
		// 清空之前设置的聚合函数
		dc.setProjection(null);
		if (idList != null && idList.size() > 0) {
			Long count = idList.get(0);
			return count.intValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#getPageList(org.hibernate.criterion.
	 * DetachedCriteria, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<T> getPageList(DetachedCriteria dc, Integer start,
			Integer pageSize) {
		// TODO Auto-generated method stub
		dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) hibernateTemplate.findByCriteria(dc, start,
				pageSize);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#executeQuerySQL(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public <E> E executeQuerySQL(final String sql, final Object... obj) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<E>() {

			@SuppressWarnings("unchecked")
			@Override
			public E doInHibernate(Session session) throws HibernateException {
				// TODO Auto-generated method stub
				Query query = session.createSQLQuery(sql);
				if (null != obj && obj.length > 0) {
					for (int i = 0; i < obj.length; i++) {
						query.setParameter(i, obj[i]);
					}
				}
				return (E) query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#executeQueryListSQL(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public <E> List<E> executeQueryListSQL(final String sql,
			final Object... obj) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<List<E>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<E> doInHibernate(Session session)
					throws HibernateException {
				// TODO Auto-generated method stub
				Query query = session.createSQLQuery(sql);
				if (null != obj && obj.length > 0) {
					for (int i = 0; i < obj.length; i++) {
						query.setParameter(i, obj[i]);
					}
				}
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.dao.BaseDao#executeQueryHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public <E> E executeQueryHQL(final String hql, final Object... obj) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<E>() {

			@SuppressWarnings("unchecked")
			@Override
			public E doInHibernate(Session session) throws HibernateException {
				// TODO Auto-generated method stub
				Query query = session.createQuery(hql);
				if (null != obj && obj.length > 0) {
					for (int i = 0; i < obj.length; i++) {
						query.setParameter(i, obj[i]);
					}
				}
				return (E) query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#executeQueryListHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public <E> List<E> executeQueryListHQL(final String hql,
			final Object... obj) {
		// TODO Auto-generated method stub
		return hibernateTemplate.execute(new HibernateCallback<List<E>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<E> doInHibernate(Session session)
					throws HibernateException {
				// TODO Auto-generated method stub
				Query query = session.createQuery(hql);
				if (null != obj && obj.length > 0) {
					for (int i = 0; i < obj.length; i++) {
						query.setParameter(i, obj[i]);
					}
				}
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.BaseDao#executeVoidProcedure(java.lang.String)
	 */
	@Override
	public void executeVoidProcedure(final String procdureSql) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				CallableStatement cs = conn.prepareCall(procdureSql);
				cs.execute();
			}

		});
	}

}
