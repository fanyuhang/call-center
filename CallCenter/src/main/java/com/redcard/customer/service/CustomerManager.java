package com.redcard.customer.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.dao.ContractDao;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.dao.CustomerExchangeCustomerDao;
import com.redcard.customer.dao.CustomerExchangeDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerExchange;
import com.redcard.customer.entity.CustomerExchangeCustomer;

@Component
@Transactional(readOnly = true)
public class CustomerManager extends GenericPageHQLQuery<Customer> {
	private static Logger log = LoggerFactory.getLogger(CustomerManager.class);
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private CustomerExchangeDao customerExchangeDao;
	@Autowired
	private CustomerExchangeCustomerDao customerExchangeCustomerDao;

    public List<Customer> findCustomerByMobile(String mobile){
        return customerDao.findCustomerByMobile(mobile);
    }
    
    public Customer findByCustNameAndPhone(String customerName,String phone) {
    	return customerDao.findByCustNameAndPhone(customerName, phone);
    }
    
    public Customer findByCustNameAndMobile(String customerName,String mobile) {
    	return customerDao.findByCustNameAndMobile(customerName, mobile);
    }

    public List<Customer> findCustomerByPhone(String phone){
        return customerDao.findCustomerByPhone(phone);
    }
	
	public Page<Customer> findAllCustomer(GridPageRequest page, String where) {
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
	
	@Transactional(readOnly = false)
    public void save(List<Customer> customerList) {
		customerDao.save(customerList);
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
	
	@Transactional(readOnly = false)
    public void updateFinancialUser(Customer customer) {
		//1.更新客户信息
		customer.setFldServiceUserNo(customer.getNewServiceUserNo());
		customerDao.save(customer);
		
		//2.更新合同
		contractDao.updateFinancialUser(customer.getNewServiceUserNo(), customer.getFldServiceUserNo());
		
		//3.记录交接历史
		CustomerExchange customerExchange = new CustomerExchange();
		customerExchange.setFldId(EntityUtil.getId());
		customerExchange.setFldOldUserNo(customer.getFldServiceUserNo());//原客服
		customerExchange.setFldNewUserNo(customer.getNewServiceUserNo());//新客服
		customerExchange.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		customerExchange.setFldCreateDate(new Date());
		customerExchange.setFldOperateDate(new Date());
		customerExchangeDao.save(customerExchange);
		
		CustomerExchangeCustomer customerExchangeCustomer = new CustomerExchangeCustomer();
		customerExchangeCustomer.setFldCustomerExchangeId(customerExchange.getFldId());
		customerExchangeCustomer.setFldCustomerId(customer.getFldId());
		customerExchangeCustomer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		customerExchangeCustomer.setFldCreateDate(new Date());
		customerExchangeCustomer.setFldOperateDate(new Date());
		customerExchangeCustomerDao.save(customerExchangeCustomer);
    }
}
