package com.redcard.telephone.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
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
	
	public void save(TelephoneTask telephoneTask) {
		TelephoneTask oldTelephoneTask = telephoneTaskDao.findOne(telephoneTask.getFldId());
		oldTelephoneTask.setFldResultType(telephoneTask.getFldResultType());
		oldTelephoneTask.setFldOperateDate(new Date());
		oldTelephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_ED);
		telephoneTaskDao.save(oldTelephoneTask);
		
		TelephoneRecord telephoneRecord = new TelephoneRecord();
		telephoneRecord.setFldTaskId(telephoneTask.getFldId());
		telephoneRecord.setFldCustomerName(oldTelephoneTask.getFldCustomerName());
		
		telephoneRecordManager.save(telephoneRecord);
	}
}
