package com.redcard.customer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerProductDetail;

public interface CustomerProductDetailDao extends PagingAndSortingRepository <CustomerProductDetail,String> {
	@Query("select count(m) from CustomerProductDetail m where m.fldId = ?1 ")
    public Long countById(String id);
}
