package com.redcard.telephone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneRecord;

public interface TelephoneRecordDao extends PagingAndSortingRepository<TelephoneRecord,Long> {
	@Query("select m from TelephoneRecord m where m.fldCustomerName = ?1 and m.fldPhone like ?2")
	public List<TelephoneRecord> findByNameAndPhone(String name,String phone);
}
