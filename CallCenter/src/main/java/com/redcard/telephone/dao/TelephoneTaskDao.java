package com.redcard.telephone.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneTask;

public interface TelephoneTaskDao extends PagingAndSortingRepository<TelephoneTask,Long> {
	
}
