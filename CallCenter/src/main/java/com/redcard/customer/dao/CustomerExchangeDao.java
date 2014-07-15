package com.redcard.customer.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerExchange;

public interface CustomerExchangeDao extends PagingAndSortingRepository <CustomerExchange,String> {

}
