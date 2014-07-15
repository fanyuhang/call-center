package com.redcard.customer.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerExchangeContract;

public interface CustomerExchangeContractDao extends PagingAndSortingRepository <CustomerExchangeContract,Integer> {

}
