package com.redcard.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.entity.CustomerContract;

@Component
@Transactional(readOnly = true)
public class ContractManager extends GenericPageHQLQuery<CustomerContract> {
	public Page<CustomerContract> findAllContract(GridPageRequest page, String where) {
        return (Page<CustomerContract>) super.findAll(where, page);
    }
}
