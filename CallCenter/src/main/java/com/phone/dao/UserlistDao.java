package com.phone.dao;

import com.phone.entity.Operator;
import com.phone.entity.Userlist;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserlistDao extends PagingAndSortingRepository<Userlist, Integer> {

}
