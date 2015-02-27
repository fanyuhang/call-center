package com.redcard.telephone.dao;

import com.redcard.telephone.entity.TelephoneProduct;
import com.redcard.telephone.entity.TelephoneTrace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 11/25/12
 */
public interface TelephoneTraceDao extends PagingAndSortingRepository<TelephoneTrace, Long> {

    @Query("select t from TelephoneTrace t where t.fldId in (?1)")
    public List<TelephoneTrace> findByIds(List<Long> ids);

}
