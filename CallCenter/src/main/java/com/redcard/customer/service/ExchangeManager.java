package com.redcard.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.entity.CustomerExchange;

@Component
@Transactional(readOnly = true)
public class ExchangeManager extends GenericPageHQLQuery<CustomerExchange> {
	public Page<CustomerExchange> findAllCustomerExchange(GridPageRequest page, String where) {
        return (Page<CustomerExchange>) super.findAll(where, page);
    }
}
