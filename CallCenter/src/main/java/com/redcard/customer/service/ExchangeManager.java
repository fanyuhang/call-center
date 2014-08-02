package com.redcard.customer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.dao.ContractDao;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.dao.CustomerExchangeContractDao;
import com.redcard.customer.dao.CustomerExchangeCustomerDao;
import com.redcard.customer.dao.CustomerExchangeDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerContract;
import com.redcard.customer.entity.CustomerExchange;
import com.redcard.customer.entity.CustomerExchangeContract;
import com.redcard.customer.entity.CustomerExchangeCustomer;

@Component
@Transactional(readOnly = true)
public class ExchangeManager extends GenericPageHQLQuery<CustomerExchange> {
	@Autowired
	private CustomerExchangeDao customerExchangeDao;
	@Autowired
	private CustomerExchangeCustomerDao customerExchangeCustomerDao;
	@Autowired
	private CustomerExchangeContractDao customerExchangeContractDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ContractDao contractDao;
	
	public List<CustomerExchangeCustomer> listCustomerByExchangeId(String exchangeId) {
		return customerExchangeCustomerDao.listCustomerByExchangeId(exchangeId);
	}
	
	public Page<CustomerExchange> findAllCustomerExchange(GridPageRequest page, String where) {
        return (Page<CustomerExchange>) super.findAll(where, page);
    }
	
	public CustomerExchange getCustomerAndContractCount(String serviceUser) {
		Integer customerCount = customerExchangeDao.countCustomerByServiceUser(serviceUser).intValue();
		Integer contractCount = customerExchangeDao.countContractByServiceUser(serviceUser).intValue();
		
		CustomerExchange customerExchange = new CustomerExchange();
		customerExchange.setCustomerNum(customerCount);
		customerExchange.setContractNum(contractCount);
		return customerExchange;
	}
	
	@Transactional(readOnly = false)
    public void save(CustomerExchange customerExchange) {
		customerExchangeDao.save(customerExchange);
		
		//保存交接客户信息
		List<Customer> customerList = customerDao.findByServiceUser(customerExchange.getFldOldUserNo());
		if(null != customerList) {
			List<CustomerExchangeCustomer> list = new ArrayList<CustomerExchangeCustomer>();
			for(Customer customer : customerList) {
				CustomerExchangeCustomer customerExchangeCustomer = new CustomerExchangeCustomer();
				customerExchangeCustomer.setFldCustomerExchangeId(customerExchange.getFldId());
				customerExchangeCustomer.setFldCustomerId(customer.getFldId());
				customerExchangeCustomer.setFldCreateDate(new Date());
				customerExchangeCustomer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
				customerExchangeCustomer.setFldOperateDate(new Date());
				list.add(customerExchangeCustomer);
			}
			
			customerExchangeCustomerDao.save(list);
		}
		
		//保存交接合同信息
		List<CustomerContract> contractList = contractDao.findBuServiceUser(customerExchange.getFldOldUserNo());
		if(null != contractList) {
			List<CustomerExchangeContract> list = new ArrayList<CustomerExchangeContract>();
			
			for(CustomerContract customerContract : contractList) {
				CustomerExchangeContract customerExchangeContract = new CustomerExchangeContract();
				customerExchangeContract.setFldCustomerExchangeId(customerExchange.getFldId());
				customerExchangeContract.setFldContractId(customerContract.getFldId());
				customerExchangeContract.setFldCreateDate(new Date());
				customerExchangeContract.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
				customerExchangeContract.setFldOperateDate(new Date());
				list.add(customerExchangeContract);
			}	
			
			customerExchangeContractDao.save(list);
		}
    }
}
