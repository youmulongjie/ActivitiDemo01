/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.changhongit.loan.entity.BaseEntity;

/**
 * <p>
 * 描述：Dao层基类接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午12:46:05
 */
public interface BaseDao<T extends BaseEntity> {
	/**
	 * 
	 * <p>
	 * 描述：根据ID查找 Entity（运行时自动获取 Entity对象类型）
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:05:29 <br>
	 * @param id
	 * @return
	 */
	public T findById(Long id);

	/**
	 * 
	 * <p>
	 * 描述：根据ID查找 Entity（运行时指定 Entity对象类型）
	 * </p>
	 * 
	 * @Date 2018年5月24日上午10:04:15 <br>
	 * @param clazz
	 *            指定 Entity对象类型
	 * @param id
	 * @return
	 */
	public <E extends BaseEntity> E findById(Class<E> clazz, Long id);

	/**
	 * 
	 * <p>
	 * 描述：根据条件查询 Entity 集合
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:11:36 <br>
	 * @param dc
	 * @return
	 */
	public List<T> findByCriteria(DetachedCriteria dc);

	/**
	 * 
	 * <p>
	 * 描述：查询所有
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:13:35 <br>
	 * @return
	 */
	public List<T> listAll();

	// 分页查询相关
	/**
	 * 
	 * <p>
	 * 描述：查询实体的总数
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:15:58 <br>
	 * @param dc
	 *            查询条件
	 * @return
	 */
	public Integer getTotalCount(DetachedCriteria dc);

	/**
	 * 
	 * <p>
	 * 描述：分页查询实体
	 * </p>
	 * 
	 * @Date 2018年5月18日上午10:16:25 <br>
	 * @param dc
	 *            查询条件
	 * @param start
	 *            查询起始下标
	 * @param pageSize
	 *            每页的记录条数
	 * @return
	 */
	List<T> getPageList(DetachedCriteria dc, Integer start, Integer pageSize);

	/**
	 * 
	 * <p>
	 * 描述：保存Entity
	 * </p>
	 * 
	 * @Date 2018年5月17日下午12:46:49 <br>
	 * @param t
	 * @return
	 */
	public Serializable save(T t);

	/**
	 * 
	 * <p>
	 * 描述：更新Entity
	 * </p>
	 * 
	 * @Date 2018年5月18日上午9:36:38 <br>
	 * @param t
	 */
	public void update(T t);

	/**
	 * 
	 * <p>
	 * 描述：保存或更新Entity
	 * </p>
	 * 
	 * @Date 2018年5月18日上午9:36:50 <br>
	 * @param t
	 */
	public void saveOrUpdate(T t);

	/**
	 * 
	 * <p>
	 * 描述：更新Entity
	 * </p>
	 * 
	 * @Date 2018年6月14日下午12:39:09 <br>
	 * @param t
	 */
	public void merge(T t);

	/**
	 * 
	 * <p>
	 * 描述：删除Entity（将状态设置为“无效”，并非真实删除）
	 * </p>
	 * 
	 * @Date 2018年5月18日上午9:50:51 <br>
	 * @param t
	 */
	public void delete(T t);

	/**
	 * 
	 * <p>
	 * 描述：删除Entity（将状态设置为“无效”，并非真实删除）
	 * </p>
	 * 
	 * @Date 2018年5月18日上午9:56:19 <br>
	 * @param id
	 *            要删除的记录ID号
	 */
	public void deleteById(Long id);

	/**
	 * 
	 * <p>
	 * 描述：执行原始SQL，返回单一对象
	 * </p>
	 * 
	 * @Date 2018年6月21日下午12:41:36 <br>
	 * @param sql
	 *            原始SQL查询语句
	 * @param obj
	 *            可变参数数组
	 * @return
	 */
	public <E> E executeQuerySQL(String sql, Object... obj);

	/**
	 * 
	 * <p>
	 * 描述：执行原始SQL，返回 List对象
	 * </p>
	 * 
	 * @Date 2018年9月11日上午11:00:38 <br>
	 * @param sql
	 *            原始SQL查询语句
	 * @param obj
	 *            可变参数数组
	 * @return
	 */
	public <E> List<E> executeQueryListSQL(String sql, Object... obj);

	/**
	 * 
	 * <p>
	 * 描述：执行HQL，返回单一对象
	 * </p>
	 * 
	 * @Date 2018年6月21日下午5:20:52 <br>
	 * @param hql
	 *            HQL查询语句
	 * @param obj
	 *            可变参数数组
	 * @return
	 */
	public <E> E executeQueryHQL(String hql, Object... obj);

	/**
	 * 
	 * <p>
	 * 描述：执行HQL，返回 List对象
	 * </p>
	 * 
	 * @Date 2018年9月11日上午11:03:37 <br>
	 * @param hql
	 *            HQL查询语句
	 * @param obj
	 *            可变参数数组
	 * @return
	 */
	public <E> List<E> executeQueryListHQL(String hql, Object... obj);

	/**
	 * 
	 * <p>
	 * 描述：执行无参 存储过程
	 * </p>
	 * 
	 * @Date 2018年6月21日下午4:10:29 <br>
	 * @param procdureSql
	 *            存储过程执行语句
	 */
	public void executeVoidProcedure(String procdureSql);
}
