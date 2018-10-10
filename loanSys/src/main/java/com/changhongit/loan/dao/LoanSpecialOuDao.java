/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import com.changhongit.loan.entity.LoanSpecialOuEntity;

/**
 * <p>
 * 描述：专用OU Dao层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月18日上午10:37:44
 */
public interface LoanSpecialOuDao extends BaseDao<LoanSpecialOuEntity> {
	/**
	 * 
	 * <p>
	 * 描述：根据OU名称获取 LoanSpecialOuEntity对象
	 * </p>
	 * 
	 * @Date 2018年6月25日上午9:44:33 <br>
	 * @param ou
	 *            OU名称
	 * @return
	 */
	public LoanSpecialOuEntity getEntityByOu(String ou);
}
