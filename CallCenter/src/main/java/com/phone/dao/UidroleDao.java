package com.phone.dao;

import com.phone.entity.Roleright;
import com.phone.entity.Uidrole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UidroleDao extends PagingAndSortingRepository<Uidrole, Integer> {

    @Query("select u from Uidrole u where u.uid = ?1")
    public List<Uidrole> findByUid(String uid);

    @Modifying
    @Query("delete from Uidrole u where u.uid = ?1")
    public void deleteByUid(String uid);

}
