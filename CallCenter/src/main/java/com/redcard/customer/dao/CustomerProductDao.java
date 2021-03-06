package com.redcard.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerProduct;

public interface CustomerProductDao extends PagingAndSortingRepository<CustomerProduct,String> {
	@Query("select count(m) from CustomerProduct m where m.fldFullName = ?1 ")
    public Long countByFullName(String fldFullName);
	
	@Query("select m from CustomerProduct m where m.fldFullName = ?1 ")
    public List<CustomerProduct> findByName(String name);
}
