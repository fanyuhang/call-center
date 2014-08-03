package com.phone.dao;

import com.phone.entity.Calllog;
import com.phone.entity.Userlist;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CalllogDao extends PagingAndSortingRepository<Calllog, Integer> {

}
