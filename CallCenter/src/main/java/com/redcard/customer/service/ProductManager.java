package com.redcard.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.entity.CustomerProduct;

@Component
@Transactional(readOnly = true)
public class ProductManager extends GenericPageHQLQuery<CustomerProduct> {
	public Page<CustomerProduct> findAllProduct(GridPageRequest page, String where) {
        return (Page<CustomerProduct>) super.findAll(where, page);
    }
}
