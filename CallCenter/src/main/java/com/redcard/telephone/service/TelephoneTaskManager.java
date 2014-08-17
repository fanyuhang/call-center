package com.redcard.telephone.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneTaskManager extends GenericPageHQLQuery<TelephoneTask> {
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	@Autowired
	private TelephoneRecordManager telephoneRecordManager;
	
	public long getCount() {
		return telephoneTaskDao.count();
	}
	
	public Page<TelephoneTask> listTask(GridPageRequest page, String where) {
        return (Page<TelephoneTask>) super.findAll(where, page);
    }
	
	public void save(TelephoneRecord telephoneRecord) {
		TelephoneTask oldTelephoneTask = telephoneTaskDao.findOne(telephoneRecord.getFldTaskId());
		oldTelephoneTask.setFldResultType(telephoneRecord.getFldResultType());
		oldTelephoneTask.setFldOperateDate(new Date());
		oldTelephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_ED);
		telephoneTaskDao.save(oldTelephoneTask);
		
		telephoneRecord.setFldCustomerName(oldTelephoneTask.getFldCustomerName());
		telephoneRecord.setFldCallType(Constant.TELEPHONE_CALL_TYPE_OUT);
		telephoneRecord.setFldCreateDate(new Date());
		telephoneRecord.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		telephoneRecord.setFldOperateDate(new Date());
		telephoneRecordManager.save(telephoneRecord);
	}
}
