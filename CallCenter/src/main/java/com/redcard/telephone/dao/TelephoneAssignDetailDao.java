package com.redcard.telephone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneAssignDetail;

public interface TelephoneAssignDetailDao extends PagingAndSortingRepository<TelephoneAssignDetail,String> {
	@Query("select m from TelephoneAssignDetail m where m.fldAssignId = ?1")
    public List<TelephoneAssignDetail> listByAssignId(String fldAssignId);
	
	@Query("select sum(m.fldTaskNumber) from TelephoneAssignDetail m where m.fldCallUserNo = ?1")
    public Long countTaskNumber(String fldCallUserNo);
	
	@Query("select sum(m.fldFinishNumber) from TelephoneAssignDetail m where m.fldCallUserNo = ?1")
    public Long countFinishNumber(String fldCallUserNo);
}
