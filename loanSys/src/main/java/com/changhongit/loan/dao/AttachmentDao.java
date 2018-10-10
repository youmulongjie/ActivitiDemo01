/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao;

import java.util.List;

import com.changhongit.loan.entity.AttachmentEntity;

/**
 * <p>
 * 描述：附件 Dao层接口
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月25日下午4:11:19
 */
public interface AttachmentDao extends BaseDao<AttachmentEntity> {
	/**
	 * 
	 * <p>
	 * 描述：根据 mainID 查询有效的附件列表
	 * </p>
	 * 
	 * @Date 2018年6月25日下午4:27:07 <br>
	 * @param mainId
	 * @return
	 */
	public List<AttachmentEntity> getEntitiyListByMainId(Long mainId);
}
