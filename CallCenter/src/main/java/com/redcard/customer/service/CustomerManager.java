package com.redcard.customer.service;

import java.util.ArrayList;
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

    public List<Customer> findCustomerByMobile(String mobile) {
        return customerDao.findCustomerByMobile(mobile);
    }

    public Customer findByCustNameAndPhone(String customerName, String phone) {
        return customerDao.findByCustNameAndPhone(customerName, phone);
    }

    public Customer findByIdentityNo(String identityNo){
        return customerDao.findByIdentityNo(identityNo);
    }
    
    public Customer findByMobile(String mobile) {
    	return customerDao.findByMobile(mobile);
    }

    public Customer findByCustNameAndMobile(String customerName, String mobile) {
        return customerDao.findByCustNameAndMobile(customerName, mobile);
    }

    public List<Customer> findCustomerByPhone(String phone) {
        return customerDao.findCustomerByPhone(phone);
    }
    
    public Customer findCustomerByMobileOrPhone(String customerName,String mobile,String phone) {
    	Customer customer = customerDao.findByMobile(mobile);
    	if(null != customer)
    		return customer;
    	else {
    		return customerDao.findByCustNameAndPhone(customerName, phone);
    	}
    }
    
    public List<Customer> findByMobileOrPhone(String num) {
    	List<Customer> list = new ArrayList<Customer>();
    	Customer customer = customerDao.findByMobile(num);
    	if(null != customer) {
    		list.add(customer);
    		return list;
    	}
    	return customerDao.findCustomerByPhone(num);
    }

    public Page<Customer> findAllCustomer(GridPageRequest page, String where) {
        return (Page<Customer>) super.findAll(where, page);
    }

    public Long countByPhoneOrMobile(String name, String phone, String mobile) {
        Long rtn = 0L;
        if (StringUtils.isNotBlank(phone))
            rtn += customerDao.countByPhone(name, phone);
        if (StringUtils.isNotBlank(mobile))
            rtn += customerDao.countByMobile(mobile);
        return rtn;
    }

    public Long countByPhoneOrMobileOrIdentityNo(String name, String phone, String mobile,String identityNo) {
        Long rtn = 0L;
        if (StringUtils.isNotBlank(phone))
            rtn += customerDao.countByPhone(name, phone);
        if (StringUtils.isNotBlank(mobile))
            rtn += customerDao.countByMobile(mobile);
        if(StringUtils.isNotBlank(identityNo))
            rtn+=customerDao.countByIdentityNo(identityNo);
        return rtn;
    }

    public Customer findByCustName(String customerName){
        return customerDao.findByCustName(customerName);
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

        Customer oldCustomer = customerDao.findOne(customer.getFldId());
        String oldServiceUser = oldCustomer.getFldServiceUserNo();

        //2.更新合同
        contractDao.updateFinancialUser(customer.getNewServiceUserNo(), oldServiceUser);

        //3.记录交接历史
        CustomerExchange customerExchange = new CustomerExchange();
        customerExchange.setFldId(EntityUtil.getId());
        customerExchange.setFldOldUserNo(oldServiceUser);//原客服
        customerExchange.setFldNewUserNo(customer.getNewServiceUserNo());//新客服
        customerExchange.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerExchange.setFldCreateDate(new Date());
        customerExchange.setFldOperateDate(new Date());
        customerExchange.setFldCustomerNum(1);
        customerExchange.setFldContractNum(contractDao.countByCustomerId(customer.getFldId()).intValue());
        customerExchange.setFldOldCustomerNum(customerDao.countByServiceUserNo(oldServiceUser).intValue());//原客服客户数
        customerExchange.setFldNewCustomerNum(customerDao.countByServiceUserNo(customer.getNewServiceUserNo()).intValue());//新客服原客户数
        customerExchange.setFldOldContractNum(contractDao.countByServiceUserNo(oldServiceUser).intValue());//原客服合同数
        customerExchange.setFldNewContractNum(contractDao.countByServiceUserNo(customer.getNewServiceUserNo()).intValue());//新客服原合同数
        customerExchangeDao.save(customerExchange);

        CustomerExchangeCustomer customerExchangeCustomer = new CustomerExchangeCustomer();
        customerExchangeCustomer.setFldCustomerExchangeId(customerExchange.getFldId());
        customerExchangeCustomer.setFldCustomerId(customer.getFldId());
        customerExchangeCustomer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerExchangeCustomer.setFldCreateDate(new Date());
        customerExchangeCustomer.setFldOperateDate(new Date());
        customerExchangeCustomerDao.save(customerExchangeCustomer);

        //1.更新客户信息
        oldCustomer.setFldServiceUserNo(customer.getNewServiceUserNo());
        oldCustomer.setFldOperateDate(new Date());
        oldCustomer.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerDao.save(oldCustomer);
    }
}
