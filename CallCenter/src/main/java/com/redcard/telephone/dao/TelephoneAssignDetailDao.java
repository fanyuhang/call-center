package com.redcard.telephone.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneAssignDetail;

public interface TelephoneAssignDetailDao extends PagingAndSortingRepository<TelephoneAssignDetail,String> {
	@Query("select m from TelephoneAssignDetail m where m.fldAssignId = ?1")
    public List<TelephoneAssignDetail> listByAssignId(String fldAssignId);
	
	@Query("select m from TelephoneAssignDetail m where m.fldCallUserNo = ?1")
    public List<TelephoneAssignDetail> listByCallUser(String fldCallUserNo);
	
	@Query("select sum(m.fldTaskNumber) from TelephoneAssignDetail m where m.fldCallUserNo = ?1")
    public Long countTaskNumber(String fldCallUserNo);
	
	@Query("select sum(m.fldFinishNumber) from TelephoneAssignDetail m where m.fldCallUserNo = ?1")
    public Long countFinishNumber(String fldCallUserNo);

    @Query("select m from TelephoneAssignDetail m where m.fldCallUserNo = ?1 and m.fldTaskDate = ?2 and m.fldTaskType = ?3")
    public List<TelephoneAssignDetail> findByCallUserNoAndDate(String userNo, Date callDate, Integer taskType);

    @Query("select m from TelephoneAssignDetail m where m.fldId in (?1)")
    public List<TelephoneAssignDetail> findByIds(List<String> ids);
}
