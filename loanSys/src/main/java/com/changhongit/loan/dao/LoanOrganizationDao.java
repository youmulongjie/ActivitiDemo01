/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import java.util.List;

import com.changhongit.loan.entity.LoanOrganizationEntity;

/**
 * <p>
 * 描述：组织库 Dao层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:39:27
 */
public interface LoanOrganizationDao {
	/**
	 * 
	 * <p>
	 * 描述：删除所有 组织库记录（物理删除，每天定时更新时先删除再创建）
	 * </p>
	 * 
	 * @Date 2018年5月25日下午2:49:46 <br>
	 * @return 删除的数量
	 */
	public Integer deleteAll();

	/**
	 * 
	 * <p>
	 * 描述：保存 组织库记录
	 * </p>
	 * 
	 * @Date 2018年5月25日下午2:41:18 <br>
	 * @param entity
	 *            组织库记录对象
	 */
	public void save(LoanOrganizationEntity entity);

	/**
	 * 
	 * <p>
	 * 描述：获取 事业部列表（按首字母排序）
	 * </p>
	 * 
	 * @Date 2018年5月25日下午5:01:31 <br>
	 * @return
	 */
	public List<LoanOrganizationEntity> getAllLoanOrganizationEntitys();

	/**
	 * 
	 * <p>
	 * 描述：根据 部门名称 获取Entity
	 * </p>
	 * 
	 * @Date 2018年7月12日下午1:58:21 <br>
	 * @param organizationName
	 *            部门名称
	 * @return
	 */
	public LoanOrganizationEntity getOneByOrganizationName(String organizationName);
}
