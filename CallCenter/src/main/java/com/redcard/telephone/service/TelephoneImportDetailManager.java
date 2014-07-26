package com.redcard.telephone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneImportDetailDao;
import com.redcard.telephone.entity.TelephoneImportDetail;

@Component
@Transactional(readOnly = true)
public class TelephoneImportDetailManager extends GenericPageHQLQuery<TelephoneImportDetail> {
	@Autowired
	private TelephoneImportDetailDao telephoneImportDetailDao;
	
	public void save(List<TelephoneImportDetail> telephoneImportDetailList) {
		telephoneImportDetailDao.save(telephoneImportDetailList);	
	}
}
