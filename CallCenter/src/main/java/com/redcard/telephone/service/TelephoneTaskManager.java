package com.redcard.telephone.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.entity.Customer;
import com.redcard.telephone.dao.TelephoneCustomerDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneTaskManager extends GenericPageHQLQuery<TelephoneTask> {
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	@Autowired
	private TelephoneRecordManager telephoneRecordManager;
	@Autowired
	private TelephoneCustomerDao telephoneCustomerDao;
	@Autowired
	private CustomerDao customerDao;
	
	public long getCount() {
		return telephoneTaskDao.count();
	}
	
	public Page<TelephoneTask> listTask(GridPageRequest page, String where) {
        return (Page<TelephoneTask>) super.findAll(where, page);
    }
	
	@Transactional(readOnly = false)
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
	
	@Transactional(readOnly = false)
	public void updateCust(String taskId,TelephoneCustomer telephoneCustomer,Customer customer) {
		//更新话单原始表
		TelephoneCustomer tmpTelephoneCustomer = telephoneCustomerDao.findOne(telephoneCustomer.getFldId());
		tmpTelephoneCustomer.setFldCustomerName(telephoneCustomer.getFldCustomerName());
		tmpTelephoneCustomer.setFldGender(telephoneCustomer.getFldGender());
		tmpTelephoneCustomer.setFldMobile(telephoneCustomer.getFldMobile());
		tmpTelephoneCustomer.setFldPhone(telephoneCustomer.getFldPhone());
		tmpTelephoneCustomer.setFldAddress(telephoneCustomer.getFldAddress());
		telephoneCustomerDao.save(tmpTelephoneCustomer);
		
		//若客户存在，则更新客户表
		if(!StringUtils.isBlank(customer.getFldId())) {
			Customer tmpCustomer = customerDao.findOne(customer.getFldId());
			tmpCustomer.setFldName(telephoneCustomer.getFldCustomerName());
			tmpCustomer.setFldGender(telephoneCustomer.getFldGender());
			tmpCustomer.setFldMobile(telephoneCustomer.getFldMobile());
			tmpCustomer.setFldPhone(telephoneCustomer.getFldPhone());
			tmpCustomer.setFldBirthday(customer.getFldBirthday());
			tmpCustomer.setFldIdentityNo(customer.getFldIdentityNo());
			tmpCustomer.setFldAddress(telephoneCustomer.getFldAddress());
			tmpCustomer.setFldEmail(customer.getFldEmail());
			customerDao.save(tmpCustomer);
		}
		
		//更新话务任务表
		TelephoneTask tmpTelephoneTask = telephoneTaskDao.findOne(Long.valueOf(taskId));
		tmpTelephoneTask.setFldCustomerName(telephoneCustomer.getFldCustomerName());
		telephoneTaskDao.save(tmpTelephoneTask);
	}
}
