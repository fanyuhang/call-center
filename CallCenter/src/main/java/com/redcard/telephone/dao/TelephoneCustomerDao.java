package com.redcard.telephone.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneCustomer;

public interface TelephoneCustomerDao extends PagingAndSortingRepository<TelephoneCustomer,String> {
	@Query("select count(m) from TelephoneCustomer m where m.fldCustomerName = ?1 and m.fldPhone = ?2")
    public Long countByPhone(String name,String phone);
	
	@Query("select count(m) from TelephoneCustomer m where m.fldMobile = ?1")
    public Long countByMobile(String mobile);
	
	@Query("select m from TelephoneCustomer m where m.fldCustomerName = ?1 and m.fldPhone = ?2")
    public TelephoneCustomer findByPhone(String name,String phone);
	
	@Query("select m from TelephoneCustomer m where m.fldMobile = ?1")
    public TelephoneCustomer findByMobile(String mobile);
}
