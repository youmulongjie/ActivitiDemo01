/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import com.changhongit.loan.entity.LoanMaxDurationConfigEntity;

/**
 * <p>
 * 描述：最长清欠期限配置 Dao层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月21日下午1:31:13
 */
public interface LoanMaxDurationConfigDao extends
		BaseDao<LoanMaxDurationConfigEntity> {
	/**
	 * 
	 * <p>
	 * 描述：根据借款类型，获取对应的最长清欠期限配置Entity
	 * </p>
	 * 
	 * @Date 2018年7月18日下午2:08:28 <br>
	 * @param loanType
	 *            借款类型
	 * @return
	 */
	public LoanMaxDurationConfigEntity findOneByLoanType(String loanType);
}
