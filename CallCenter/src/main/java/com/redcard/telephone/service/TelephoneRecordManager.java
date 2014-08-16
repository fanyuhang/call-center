package com.redcard.telephone.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneRecordDao;
import com.redcard.telephone.entity.TelephoneRecord;

@Component
@Transactional(readOnly = true)
public class TelephoneRecordManager extends GenericPageHQLQuery<TelephoneRecord> {
	@Autowired
	private TelephoneRecordDao telephoneRecordDao;
	
	public List<TelephoneRecord> findByNameAndPhone(String name,String phone,String mobile) {
		if(!StringUtils.isBlank(mobile))
			return telephoneRecordDao.findByNameAndPhone(name,mobile);
		return telephoneRecordDao.findByNameAndPhone(name,phone);
	}
	
	@Transactional(readOnly = false)
	public void save(TelephoneRecord telephoneRecord) {
		telephoneRecordDao.save(telephoneRecord);
	}
}
