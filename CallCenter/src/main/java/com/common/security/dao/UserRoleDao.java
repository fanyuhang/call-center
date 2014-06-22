package com.common.security.dao;

import com.common.security.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 9/16/12
 */
public interface UserRoleDao extends PagingAndSortingRepository<UserRole, Integer> {

    @Modifying
    @Query("delete from UserRole ur where ur.userId = ?1")
    public void deleteUserRoleByUser(Integer userId);

    @Query("select ur from UserRole ur where ur.userId=?1")
    public List<UserRole> findUserRoleByUser(Integer userId);

    @Query("select ur from UserRole ur where ur.roleId=?1")
    public List<UserRole> findUserRoleByRole(Integer roleId);
}
