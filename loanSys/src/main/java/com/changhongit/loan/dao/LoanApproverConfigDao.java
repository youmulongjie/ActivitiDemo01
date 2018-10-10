/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import com.changhongit.loan.entity.LoanApproverConfigEntity;

/**
 * <p>
 * 描述：固定审批人配置 接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:44:41
 */
public interface LoanApproverConfigDao extends
		BaseDao<LoanApproverConfigEntity> {
	/**
	 * 
	 * <p>
	 * 描述：根据职位查询 有效的 “固定审批人配置”记录
	 * </p>
	 * 
	 * @Date 2018年5月17日下午4:13:47 <br>
	 * @param position
	 *            职位
	 * @return
	 */
	public LoanApproverConfigEntity findByPosition(String position);

}
