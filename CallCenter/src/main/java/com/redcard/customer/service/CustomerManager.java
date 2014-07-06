package com.redcard.customer.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.entity.Customer;

@Component
@Transactional(readOnly = true)
public class CustomerManager extends GenericPageHQLQuery<Customer> {
	private static Logger log = LoggerFactory.getLogger(CustomerManager.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	public Page<Customer> findAllMerchant(GridPageRequest page, String where) {
        return (Page<Customer>) super.findAll(where, page);
    }
	
	public Long countByPhoneOrMobile(String name,String phone,String mobile) {
		Long rtn = 0L;
		if(!StringUtils.isEmpty(phone))
			rtn += customerDao.countByPhone(name,phone);
		if(!StringUtils.isEmpty(mobile))
			rtn += customerDao.countByMobile(name, mobile);
		return rtn;
	}
	
	@Transactional(readOnly = false)
    public void save(Customer customer) {
		customerDao.save(customer);
    }
	
	public Customer find(String fldId) {
        return customerDao.findOne(fldId);
    }
	
	@Transactional(readOnly = false)
    public void delete(String fldId) {
        Customer customer = customerDao.findOne(fldId);
        customer.setFldStatus(Constant.CUSTOMER_STATUS_DIABLED);
        customer.setFldOperateDate(new Date());
        customerDao.save(customer);
    }
}
