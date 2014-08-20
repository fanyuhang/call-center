package com.redcard.telephone.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneRecordDao;
import com.redcard.telephone.entity.TelephoneRecord;

@Component
@Transactional(readOnly = true)
public class TelephoneRecordManager extends GenericPageHQLQuery<TelephoneRecord> {
	@Autowired
	private TelephoneRecordDao telephoneRecordDao;
	
	public List<TelephoneRecord> findByNameAndPhone(String name,String phone,String mobile) {
		List<TelephoneRecord> list = new ArrayList<TelephoneRecord>();
		if(!StringUtils.isBlank(mobile))
			list = telephoneRecordDao.findByNameAndPhone(name,mobile);
		List<TelephoneRecord> tmpList = telephoneRecordDao.findByNameAndPhone(name,phone);
		if(null != tmpList && tmpList.size() > 0) {
			list.addAll(tmpList);
		}
		
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(TelephoneRecord telephoneRecord) {
		telephoneRecordDao.save(telephoneRecord);
	}
	
	public Page<TelephoneRecord> findAllTelephoneRecord(GridPageRequest page, String where) {
        return (Page<TelephoneRecord>) super.findAll(where, page);
    }
}
