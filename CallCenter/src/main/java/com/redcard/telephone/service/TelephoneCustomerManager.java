package com.redcard.telephone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.phone.dao.CalllogDao;
import com.phone.entity.Calllog;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;
import com.redcard.telephone.dao.TelephoneCustomerDao;
import com.redcard.telephone.dao.TelephoneImportDetailDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneImportDetail;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneCustomerManager extends GenericPageHQLQuery<TelephoneCustomer> {
	@Autowired
	private TelephoneCustomerDao telephoneCustomerDao;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
    private CustomerDao customerDao;
	@Autowired
	private CalllogDao calllogDao;
	@Autowired
	private TelephoneImportDetailDao telephoneImportDetailDao;
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	
	public Long countByPhoneOrMobile(String name,String phone,String mobile) {
		Long rtn = 0L;
		if(!StringUtils.isEmpty(phone))
			rtn += telephoneCustomerDao.countByPhone(name,phone);
		if(!StringUtils.isEmpty(mobile))
			rtn += telephoneCustomerDao.countByMobile(mobile);
		return rtn;
	}
	
	public void save(List<TelephoneCustomer> telephoneCustomerList) {
		telephoneCustomerDao.save(telephoneCustomerList);
	}
	
	public Integer countCustomer() {
		return ((List<TelephoneCustomer>)telephoneCustomerDao.findAll()).size();
	}
	
	public Page<TelephoneCustomer> findAllTelephoneCustomer(GridPageRequest page, String where) {
        return (Page<TelephoneCustomer>) super.findAll(where, page);
    }
	
	public List<TelephoneTask> queryTaskHis(String custId) {
		List<TelephoneTask> taskList = new ArrayList<TelephoneTask>();
		List<TelephoneImportDetail> list = telephoneImportDetailDao.queryByCustId(custId);
		
		for(TelephoneImportDetail tmpTelephoneImportDetail : list) {
			List<TelephoneTask> tmpTaskList = telephoneTaskDao.listByCustomerId(tmpTelephoneImportDetail.getFldId());		
			taskList.addAll(tmpTaskList);
		}
		
		return taskList;
	}
	
	public TelephoneCustomer findByPhoneOrMobile(String name,String phone,String mobile) {
		TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findByPhone(name, phone);
		if(null != telephoneCustomer)
			return telephoneCustomer;
		else {
			telephoneCustomer = telephoneCustomerDao.findByMobile(mobile);
			return telephoneCustomer;
		}
	}
	
	public String saveAsCustomer(String id) {
		TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findOne(id);
		//校验客户是否已存在
		Long count = customerManager.countByPhoneOrMobile(telephoneCustomer.getFldCustomerName(), telephoneCustomer.getFldPhone(), telephoneCustomer.getFldMobile());
		if(count > 0) {
			return "客户已存在";
		} else {
			Customer customer = new Customer();
			customer.setFldId(EntityUtil.getId());
			customer.setFldName(telephoneCustomer.getFldCustomerName());
			customer.setFldSource(Constant.CUSTOMER_SOURCE_TELEPHONE);
			customer.setFldGender(telephoneCustomer.getFldGender());
			customer.setFldPhone(telephoneCustomer.getFldPhone());
			customer.setFldMobile(telephoneCustomer.getFldMobile());
			customer.setFldAddress(telephoneCustomer.getFldAddress());
			customer.setFldOperateDate(new Date());
			customer.setFldCreateDate(new Date());
			customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
			customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
			customerManager.save(customer);
			return "true";
		}
	}
	
	@Transactional(readOnly = false)
    public void updateCust(Customer customer,String telephoneCustomerId,String callId) {
		if(!StringUtils.isBlank(customer.getFldId())) {
			Customer tmpCustomer = customerDao.findOne(customer.getFldId());
			tmpCustomer.setFldName(customer.getFldName());
			tmpCustomer.setFldGender(customer.getFldGender());
			tmpCustomer.setFldMobile(customer.getFldMobile());
			tmpCustomer.setFldPhone(customer.getFldPhone());
			tmpCustomer.setFldBirthday(customer.getFldBirthday());
			tmpCustomer.setFldIdentityNo(customer.getFldIdentityNo());
			tmpCustomer.setFldAddress(customer.getFldAddress());
			tmpCustomer.setFldEmail(customer.getFldEmail());
			customerDao.save(tmpCustomer);
		}
		
		if(!StringUtils.isBlank(telephoneCustomerId)) {
			TelephoneCustomer tmpTelephoneCustomer = telephoneCustomerDao.findOne(telephoneCustomerId);
			tmpTelephoneCustomer.setFldCustomerName(customer.getFldName());
			tmpTelephoneCustomer.setFldGender(customer.getFldGender());
			tmpTelephoneCustomer.setFldMobile(customer.getFldMobile());
			tmpTelephoneCustomer.setFldPhone(customer.getFldPhone());
			tmpTelephoneCustomer.setFldAddress(customer.getFldAddress());
			
			Calllog calllog = calllogDao.findOne(Long.valueOf(callId));
			tmpTelephoneCustomer.setFldLatestCallDate(calllog.getAnsweredTime());//最近通话时间
			
			telephoneCustomerDao.save(tmpTelephoneCustomer);
		}
	}
	
	public List<TelephoneCustomer> findByMobileOrPhone(String num) {
		List<TelephoneCustomer> list = new ArrayList<TelephoneCustomer>();
		TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findByMobile(num);
		if(null != telephoneCustomer) {
			list.add(telephoneCustomer);
			return list;
		}
		
		return telephoneCustomerDao.findByPhone(num);
	}
}
