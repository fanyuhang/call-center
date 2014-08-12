package com.phone.dao;

import com.phone.entity.Operator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OperatorDao extends PagingAndSortingRepository<Operator, Integer> {

    @Query("select o from Operator o where o.uid = ?1")
    public Operator findByUid(String uid);

    @Modifying
    @Query("delete from Operator o where o.uid = ?1")
    public void deleteByUid(String uid);

}
