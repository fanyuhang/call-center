package com.phone.dao;

import com.phone.entity.Operator;
import com.phone.entity.Roleright;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RolerightDao extends PagingAndSortingRepository<Roleright, Integer> {

}
