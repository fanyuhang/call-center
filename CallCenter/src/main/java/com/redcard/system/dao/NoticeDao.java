package com.redcard.system.dao;

import com.redcard.system.entity.Notice;
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
public interface NoticeDao extends PagingAndSortingRepository<Notice, Integer> {
}
