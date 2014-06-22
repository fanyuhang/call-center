package com.redcard.system.dao;

import com.redcard.system.entity.OperateLog;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: Allen
 * Date: 11/25/12
 */
public interface OperateLogDao extends PagingAndSortingRepository<OperateLog, String> {

}
