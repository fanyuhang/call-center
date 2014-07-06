package com.redcard.customer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.Customer;

public interface CustomerDao extends PagingAndSortingRepository <Customer,String> {
	@Query("select count(m) from Customer m where m.fldName = ?1 and m.fldPhone = ?2")
    public Long countByPhone(String name,String phone);
	
	@Query("select count(m) from Customer m where m.fldName = ?1 and m.fldMobile = ?2")
    public Long countByMobile(String name,String mobile);
}
