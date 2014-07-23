package com.redcard.customer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerExchange;

public interface CustomerExchangeDao extends PagingAndSortingRepository <CustomerExchange,String> {
	@Query("select count(m) from Customer m where m.fldServiceUserNo = ?1 ")
    public Long countCustomerByServiceUser(String serviceUserNo);
	
	@Query("select count(m) from CustomerContract m where m.fldServiceUserNo = ?1 ")
    public Long countContractByServiceUser(String serviceUserNo);
}
