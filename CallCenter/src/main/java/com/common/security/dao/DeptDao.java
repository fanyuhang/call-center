package com.common.security.dao;

import com.common.security.entity.Dept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * User: Allen
 * Date: 9/2/12
 */
public interface DeptDao extends PagingAndSortingRepository<Dept, String> {

    @Query("select d from Dept d order by d.deptCode")
    public List<Dept> findAllDept();

    @Query("select max(d.deptCode) from Dept d where d.parent=?1")
    public String findMaxChildCode(String parent);

    @Query("select d from Dept d, UserDept ud where d.deptCode=ud.deptCode and ud.loginName=?1")
    public List<Dept> findDeptByUser(String loginName);

    @Query("select d from Dept d where d.parent = ?1")
    public List<Dept> findChildren(String code);

	public List<Dept> findByDeptCodeIn(Set<String> codeSet);
}
