package com.redcard.customer.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerExchangeCustomer;

public interface CustomerExchangeCustomerDao extends PagingAndSortingRepository <CustomerExchangeCustomer,Integer> {

}
