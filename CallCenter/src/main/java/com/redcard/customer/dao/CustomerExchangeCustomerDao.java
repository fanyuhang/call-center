package com.redcard.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerExchangeCustomer;

public interface CustomerExchangeCustomerDao extends PagingAndSortingRepository<CustomerExchangeCustomer,Integer> {
	@Query("select c from CustomerExchangeCustomer c where c.fldCustomerExchangeId = ?1")
    public List<CustomerExchangeCustomer> listCustomerByExchangeId(String exchangeId);
}
