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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhongit.loan.bean.Page;
import com.changhongit.loan.dao.BaseDao;
import com.changhongit.loan.entity.BaseEntity;
import com.changhongit.loan.service.BaseService;

/**
 * <p>
 * 描述：Service层 基类接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日上午9:48:39
 */
@Service
@Transactional
public class BaseServiceImpl implements BaseService {
	@Autowired
	private BaseDao<BaseEntity> baseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.BaseService#pageEntitys(java.lang.Class,
	 * java.lang.Integer)
	 */
	@Override
	public <T extends BaseEntity> Page<T> pageEntitys(Class<T> clazz,
			int currentPage) {
		// TODO Auto-generated method stub
		Page<T> page = new Page<T>();

		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		// 查询有效记录
		criteria.add(Restrictions.eq("status", 1));
		// ID排序
		criteria.addOrder(Order.asc("id"));
		// 总行数
		int totalCount = baseDao.getTotalCount(criteria);
		page.setTotalCount(totalCount).setCurrentPage(currentPage);

		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) baseDao.getPageList(criteria,
				(currentPage - 1) * page.getPageSize(), page.getPageSize());

		page.setTotalCount(totalCount).setCurrentPage(currentPage)
				.setList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.BaseService#deleteEntityByIds(java.lang.
	 * Class, java.util.List, java.lang.String)
	 */
	@Override
	public <T extends BaseEntity> void deleteEntityByIds(Class<T> clazz,
			List<Long> ids, String deleteUser) {
		// TODO Auto-generated method stub
		T entity = null;
		for (int i = 0, j = ids.size(); i < j; i++) {
			entity = baseDao.findById(clazz, ids.get(i));
			entity.setLastupdateBy(deleteUser);
			entity.setLastupdateDate(new Date());

			baseDao.delete(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.BaseService#getEntityById(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public <T extends BaseEntity> T getEntityById(Class<T> clazz, Long id) {
		// TODO Auto-generated method stub
		return baseDao.findById(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.BaseService#saveOrUpdate(com.changhongit
	 * .loan.entity.BaseEntity)
	 */
	@Override
	public <T extends BaseEntity> void saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		baseDao.saveOrUpdate(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.service.BaseService#getAllEntitys(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> List<T> getAllEntitys(Class<T> clazz) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		// 查询有效记录
		criteria.add(Restrictions.eq("status", 1));

		return (List<T>) baseDao.findByCriteria(criteria);
	}

}
