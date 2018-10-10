/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

import java.util.List;

import com.changhongit.loan.bean.Page;
import com.changhongit.loan.entity.BaseEntity;

/**
 * <p>
 * 描述：Service层 基类接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日上午9:47:49
 */
public interface BaseService {
	/**
	 * 
	 * <p>
	 * 描述：分页查询 公共方法（默认查询状态为“有效”，按ID升序）
	 * </p>
	 * 
	 * @Date 2018年5月24日上午10:31:05 <br>
	 * @param clazz
	 *            要查询的Entity类
	 * @param currentPage
	 *            当前页数
	 * @return
	 */
	<T extends BaseEntity> Page<T> pageEntitys(Class<T> clazz, int currentPage);

	/**
	 * 
	 * <p>
	 * 描述：根据ID集合，批量删除Entity（将状态设置为“无效”，并非真实删除）
	 * </p>
	 * 
	 * @Date 2018年5月24日上午10:30:26 <br>
	 * @param clazz
	 *            要删除的Entity类
	 * @param ids
	 *            要删除的ID集合
	 * @param deleteUser
	 *            操作人
	 */
	<T extends BaseEntity> void deleteEntityByIds(Class<T> clazz,
			List<Long> ids, String deleteUser);

	/**
	 * 
	 * <p>
	 * 描述：根据ID，获取实体类
	 * </p>
	 * 
	 * @Date 2018年5月24日上午10:38:18 <br>
	 * @param clazz
	 *            要查询的Entity类
	 * @param id
	 *            查询ID
	 * @return
	 */
	<T extends BaseEntity> T getEntityById(Class<T> clazz, Long id);

	/**
	 * 
	 * <p>
	 * 描述：保存或更新 Entity
	 * </p>
	 * 
	 * @Date 2018年7月5日下午4:44:21 <br>
	 * @param t
	 */
	<T extends BaseEntity> void saveOrUpdate(T t);

	/**
	 * 
	 * <p>
	 * 描述：获取所有有效的记录集合
	 * </p>
	 * 
	 * @Date 2018年7月10日下午2:27:35 <br>
	 * @param clazz
	 *            要查询的Entity类
	 * @return
	 */
	<T extends BaseEntity> List<T> getAllEntitys(Class<T> clazz);

}
