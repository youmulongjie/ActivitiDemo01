/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.dao.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.changhongit.loan.dao.AttachmentDao;
import com.changhongit.loan.entity.AttachmentEntity;

/**
 * <p>
 * 描述：附件 Dao层接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月25日下午4:13:34
 */
@Repository
public class AttachmentDaoImpl extends BaseDaoImpl<AttachmentEntity> implements
		AttachmentDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.changhongit.loan.dao.AttachmentDao#getEntitiyListByMainId(java.lang
	 * .Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentEntity> getEntitiyListByMainId(Long mainId) {
		// TODO Auto-generated method stub
		return (List<AttachmentEntity>) hibernateTemplate.find(
				"from AttachmentEntity t where t.status = 1 and t.mainId = ? order by t.id asc",
				mainId);
	}

}
