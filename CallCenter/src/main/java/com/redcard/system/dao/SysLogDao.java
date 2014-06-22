package com.redcard.system.dao;

import com.redcard.system.entity.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * User: Allen
 * Date: 11/25/12
 */
public interface SysLogDao extends PagingAndSortingRepository<SysLog, Long> {

    @Query("from SysLog l where l.operateDate >= ?1 and l.operateDate <= ?2")
    Page<SysLog> findAll(Date startDate, Date endDate, Pageable pageable);
}
