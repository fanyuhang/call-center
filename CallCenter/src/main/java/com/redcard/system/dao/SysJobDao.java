package com.redcard.system.dao;

import com.redcard.system.entity.SysJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 12/6/12
 */
public interface SysJobDao extends PagingAndSortingRepository<SysJob, Integer> {

    @Query("select j from SysJob j where j.status = ?1")
    public List<SysJob> findSysJobByStatus(Integer status);

}
