/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service;

import java.util.List;

import com.changhongit.loan.entity.LoanOrganizationEntity;


/**
 * <p>
 * 描述：组织库 Service层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:50:47
 */
public interface LoanOrganizationService {
	/**
	 * 
	 * <p>
	 * 描述：初始 组织库记录（初始化操作，系统上线前执行）
	 * </p>
	 * 
	 * @Date 2018年5月25日下午2:41:18 <br>
	 * @param entity
	 *            组织库记录对象
	 */
	public void initLoanOrganizationEntitys();

	/**
	 * 
	 * <p>
	 * 描述：更新 组织库记录（先全表删除在创建，由定时器调用）
	 * </p>
	 * 
	 * @Date 2018年5月25日下午3:12:03 <br>
	 * @param list
	 */
	public void updateLoanOrganizationEntitys();
	
	/**
	 * 
	 * <p>
	 * 描述：获取 事业部列表
	 * </p>
	 *
	 * @Date 2018年5月25日下午5:19:13 <br>
	 */
	public List<LoanOrganizationEntity> getAllLoanOrganizationEntitys();
}
