package com.redcard.telephone.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneExchange;

public interface TelephoneExchangeDao extends PagingAndSortingRepository<TelephoneExchange,String> {

}
