package com.redcard.telephone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneTask;

public interface TelephoneTaskDao extends PagingAndSortingRepository<TelephoneTask,Long> {
	@Query("select m from TelephoneTask m where m.fldAssignDetailId = ?1")
    public List<TelephoneTask> listByAssignDetailId(String fldAssignDetailId);
	
	@Query("select m from TelephoneTask m where m.fldAssignDetailId = ?1 and fldCallStatus = ?2 and fldCallUserNo = ?3")
    public List<TelephoneTask> listByAssignDetailIdAndCallStatus(String fldAssignDetailId,Integer fldCallStatus,String fldCallUserNo);
	
	@Query("select m from TelephoneTask m where m.fldCustomerId = ?1")
    public List<TelephoneTask> listByCustomerId(String customerId);
}
