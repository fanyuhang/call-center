package com.redcard.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.customer.entity.CustomerProductDetail;

public interface CustomerProductDetailDao extends PagingAndSortingRepository<CustomerProductDetail,String> {
	@Query("select count(m) from CustomerProductDetail m where m.fldDayUnit = ?1 and m.fldClearDays = ?2 and m.fldAnnualizedRate = ?3 and m.fldStatus = 0")
    public Long countByCondition(Integer dayUnit,Integer clearDays,Double annualizedRate);
	
	@Query(" from CustomerProductDetail m where m.fldProductId = ?1")
    public List<CustomerProductDetail> findByProductId(String productId);
	
	@Query(" from CustomerProductDetail m where m.fldProductId = ?1 and m.fldClearDays = ?2")
    public CustomerProductDetail findByProductIdAndDayUnit(String productId,Integer clearDays);
	
	@Modifying
    @Query("update CustomerProductDetail m set m.fldStatus=?1 where m.fldProductId = ?2")
    public void updateProductDetailByProductId(Integer status,String productId);
}
