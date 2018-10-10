/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;

/**
 * @deprecated
 * <p>
 * 描述：分页 回调函数类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:02:44
 */
public class PageHibernateCallback<T> implements HibernateCallback<List<T>> {
	private String hql;
	private Object[] params;
	private int startIndex;
	private int pageSize;

	/**
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            实参数组
	 * @param startIndex
	 *            开始索引
	 * @param pageSize
	 *            每页数量
	 */
	public PageHibernateCallback(String hql, Object[] params, int startIndex,
			int pageSize) {
		super();
		this.hql = hql;
		this.params = params;
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.orm.hibernate4.HibernateCallback#doInHibernate(org
	 * .hibernate.Session)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> doInHibernate(Session session) throws HibernateException {
		// TODO Auto-generated method stub
		Query query = session.createQuery(hql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);

		return query.list();
	}
}
