package com.phone.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.phone.entity.Calllog;

public interface CalllogDao extends PagingAndSortingRepository<Calllog, Long> {

}
