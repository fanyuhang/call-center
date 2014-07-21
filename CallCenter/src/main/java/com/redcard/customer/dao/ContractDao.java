package com.redcard.customer.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerContract;

import java.util.List;

public interface ContractDao extends PagingAndSortingRepository <CustomerContract,String> {
	@Modifying
    @Query("update CustomerContract m set m.fldServiceUserNo=?1 where m.fldServiceUserNo = ?2")
    public void updateFinancialUser(String newServiceUser,String serviceUser);

    @Query("select c from CustomerContract c where c.fldCustomerId in (?1)")
    public List<CustomerContract> findAllContractByCustomerId(List<String> customerId);

    }
