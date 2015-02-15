package com.redcard.telephone.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select count(*) from TelephoneTask m where m.fldAssignDetailId = ?1 and m.fldTaskStatus=?2 and m.fldTaskDate=?3")
    public Long countByDateAndTaskStatus(String assignDetailId,Integer taskStatus, Date date);

    @Query("select m from TelephoneTask m where m.fldCallUserNo = ?1 and m.fldTaskStatus < ?2 ")
    Page<TelephoneTask> findByCallUserNoAndTaskStatus(String callUserNo, Integer taskStatus, Pageable page);

}
