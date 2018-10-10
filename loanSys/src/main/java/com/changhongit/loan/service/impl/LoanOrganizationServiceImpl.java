/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.service.impl<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhongit.HRModule.orgstruc.CuxHrOrganizationRec;
import com.changhongit.loan.dao.LoanOrganizationDao;
import com.changhongit.loan.entity.LoanOrganizationEntity;
import com.changhongit.loan.service.LoanOrganizationService;
import com.changhongit.loan.util.HrInfoUtil;

/**
 * <p>
 * 描述：组织库 Service层接口实现类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月25日下午2:51:55
 */
@Service
@Transactional
public class LoanOrganizationServiceImpl implements LoanOrganizationService {
	private static final Logger logger = LoggerFactory
			.getLogger(LoanOrganizationServiceImpl.class);

	@Autowired
	private LoanOrganizationDao loanOrganizationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.updateLoanOrganizationEntitys#
	 * saveLoanOrganizationEntitys()
	 */
	@Override
	public void updateLoanOrganizationEntitys() {
		// TODO Auto-generated method stub
		int len = loanOrganizationDao.deleteAll();
		logger.info("删除个数：{}", len);

		List<LoanOrganizationEntity> list = getLoanOrganizationEntitys();
		for (int i = 0, j = list.size(); i < j; i++) {
			loanOrganizationDao.save(list.get(i));
		}
		logger.info("保存个数：{}", list.size());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.initLoanOrganizationEntitys#
	 * saveLoanOrganizationEntitys(java.util.List)
	 */
	@Override
	public void initLoanOrganizationEntitys() {
		// TODO Auto-generated method stub
		List<LoanOrganizationEntity> list = getLoanOrganizationEntitys();
		for (int i = 0, j = list.size(); i < j; i++) {
			loanOrganizationDao.save(list.get(i));
		}
		logger.info("初始化保存个数：{}", list.size());
	}

	/**
	 * <p>
	 * 描述：从接口 获取 组织库集合
	 * </p>
	 * 
	 * @Date 2018年5月25日下午3:22:19 <br>
	 * @return
	 */
	private List<LoanOrganizationEntity> getLoanOrganizationEntitys() {
		List<CuxHrOrganizationRec> list = HrInfoUtil
				.getCuxHrOrganizationRecList();
		LoanOrganizationEntity entity = null;
		CuxHrOrganizationRec rec = null;
		List<LoanOrganizationEntity> entities = new ArrayList<LoanOrganizationEntity>();
		logger.info("接口查询 组织库个数：{}", list.size());
		for (int i = 0; i < list.size(); i++) {
			rec = list.get(i);

			entity = new LoanOrganizationEntity();
			entity.setCreateBy("SYSTEM");
			entity.setCreateDate(new Date());
			entity.setDateFrom(rec.getDateFrom());
			entity.setDateTo(rec.getDateTo());
			entity.setLocationId(rec.getLocationId());
			entity.setOrganizationId(rec.getOrganizationId());
			entity.setOrganizationName(rec.getOrganizationName());
			entity.setOrganizationType(rec.getOrganizationType());
			entity.setOrganizationTypeName(rec.getOrganizationTypeName());

			entities.add(entity);
		}
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.changhongit.loan.service.LoanOrganizationService#
	 * getAllLoanOrganizationEntitys()
	 */
	@Override
	public List<LoanOrganizationEntity> getAllLoanOrganizationEntitys() {
		// TODO Auto-generated method stub
		return loanOrganizationDao.getAllLoanOrganizationEntitys();
	}
}
