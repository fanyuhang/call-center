package com.common.security.dao;

import com.common.security.entity.Privilege;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 9/12/12
 */
public interface PrivilegeDao extends PagingAndSortingRepository<Privilege, Integer> {

    @Query("select p from Privilege p where p.master=?1 and p.masterValue=?2")
    public List<Privilege> findMasterPrivilege(String master, Integer masterValue);

    @Modifying
    @Query("delete from Privilege p where p.master=?1 and p.masterValue=?2")
    public void deleteMasterPrivilege(String master, Integer masterValue);

    @Query("select p from Privilege p where p.masterValue = ?1 and p.accessValue = ?2 ")
    public List<Privilege> findPrivilegeByUserIdAndCode(Integer userId, String code);

    @Query("select p from Privilege p,UserRole ur where p.master='ROLE' and p.masterValue=ur.roleId and ur.userId=?1 ")
    public List<Privilege> findUserRolePrivilege(Integer userId);
}
