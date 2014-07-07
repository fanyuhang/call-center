package com.redcard.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.dao.CustomerProductDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerProduct;

@Component
@Transactional(readOnly = true)
public class ProductManager extends GenericPageHQLQuery<CustomerProduct> {
	@Autowired
	private CustomerProductDao customerProductDao;
	
	public Page<CustomerProduct> findAllProduct(GridPageRequest page, String where) {
        return (Page<CustomerProduct>) super.findAll(where, page);
    }
	
	public Long countById(String id) {
		return customerProductDao.countById(id);
	}
	
	@Transactional(readOnly = false)
    public void save(CustomerProduct product) {
		customerProductDao.save(product);
    }
}
