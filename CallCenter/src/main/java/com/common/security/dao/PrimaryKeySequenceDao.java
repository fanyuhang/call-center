package com.common.security.dao;

import com.common.security.entity.PrimaryKeySequence;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: xqingli
 * Date: 1/29/13
 * Time: 10:20 PM
 */
public interface PrimaryKeySequenceDao extends PagingAndSortingRepository<PrimaryKeySequence, Integer> {
}
