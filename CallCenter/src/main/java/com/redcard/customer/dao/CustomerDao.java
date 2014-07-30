package com.redcard.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.Customer;

public interface CustomerDao extends PagingAndSortingRepository<Customer,String> {
	@Query("select count(m) from Customer m where m.fldName = ?1 and m.fldPhone = ?2")
    public Long countByPhone(String name,String phone);
	
	@Query("select count(m) from Customer m where m.fldMobile = ?1")
    public Long countByMobile(String mobile);
	
	@Query("select count(m) from Customer m where m.fldServiceUserNo = ?1")
    public Long countByServiceUserNo(String serviceUserNo);

    @Query("select c from Customer c where c.fldMobile = ?1 and c.fldStatus = 0 ")
    public List<Customer> findCustomerByMobile(String mobile);

    @Query("select c from Customer c where c.fldPhone = ?1 and c.fldStatus = 0")
    public List<Customer> findCustomerByPhone(String phone);
    
    @Query("select c from Customer c where c.fldName = ?1 and c.fldPhone = ?2 ")
    public Customer findByCustNameAndPhone(String customerName,String phone);
    
    @Query("select c from Customer c where c.fldName = ?1 and c.fldMobile = ?2 ")
    public Customer findByCustNameAndMobile(String customerName,String mobile);
    
    @Query("select c from Customer c where c.fldServiceUserNo = ?1 ")
    public List<Customer> findByServiceUser(String serviceUserNo);
}
