package com.common.security.dao;

import com.common.security.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 9/2/12
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

    @Query("select r from Role r, UserRole ur where ur.roleId=r.id and ur.userId=?1")
    public List<Role> findRoleByUser(Integer userId);

    @Query("select r.roleName from Role r, UserRole ur where ur.roleId=r.id and ur.userId=?1")
    public List<String> findRoleNameByUser(Integer userId);

    public List<Role> findRoleByRoleName(String roleName);

}
