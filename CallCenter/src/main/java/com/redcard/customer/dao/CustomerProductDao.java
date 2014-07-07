package com.redcard.customer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerProduct;

public interface CustomerProductDao extends PagingAndSortingRepository <CustomerProduct,String> {
	@Query("select count(m) from CustomerProduct m where m.fldId = ?1 ")
    public Long countById(String id);
}
