package com.common.security.dao;

/**
 * User: Allen
 * Date: 9/16/12
 */

import com.common.security.entity.UserDept;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDeptDao extends PagingAndSortingRepository<UserDept, Integer> {

    @Modifying
    @Query("delete from UserDept ud where ud.loginName = ?1")
    public void deleteUserDeptByUser(String loginName);

    @Query("select ud from UserDept ud where ud.loginName=?1")
    public List<UserDept> findUserDeptByUser(String loginName);

}
