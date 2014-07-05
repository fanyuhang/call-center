package com.redcard.customer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
