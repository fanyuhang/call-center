package com.redcard.telephone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneTaskManager extends GenericPageHQLQuery<TelephoneTask> {
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	
	public long getCount() {
		return telephoneTaskDao.count();
	}
}
