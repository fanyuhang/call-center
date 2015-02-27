package com.redcard.telephone.dao;

import com.redcard.telephone.entity.TelephoneTrace;
import com.redcard.telephone.entity.TelephoneTraceLog;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: Allen
 * Date: 11/25/12
 */
public interface TelephoneTraceLogDao extends PagingAndSortingRepository<TelephoneTraceLog, Long> {
}
