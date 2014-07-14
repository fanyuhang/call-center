package com.redcard.customer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.dao.ContractDao;
import com.redcard.customer.dao.CustomerProductDetailDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerContract;

@Component
@Transactional(readOnly = true)
public class ContractManager extends GenericPageHQLQuery<CustomerContract> {
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private CustomerProductDetailDao customerProductDetailDao;
	
	public Page<CustomerContract> findAllContract(GridPageRequest page, String where) {
        return (Page<CustomerContract>) super.findAll(where, page);
    }
	
	@Transactional(readOnly = false)
    public void save(CustomerContract customerContract) {
		String fldProductId = customerProductDetailDao.findOne(customerContract.getFldProductDetailId()).getFldProductId();
		customerContract.setFldProductId(fldProductId);
		contractDao.save(customerContract);
    }
	
	public CustomerContract find(String fldId) {
        return contractDao.findOne(fldId);
    }
	
	@Transactional(readOnly = false)
    public void delete(String fldId) {
		CustomerContract customerContract = contractDao.findOne(fldId);
		customerContract.setFldStatus(Constant.CONTRACT_STATUS_DIABLED);
		customerContract.setFldOperateDate(new Date());
        contractDao.save(customerContract);
    }
}
