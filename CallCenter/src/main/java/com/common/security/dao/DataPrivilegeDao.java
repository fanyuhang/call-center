package com.common.security.dao;

import com.common.security.entity.DataPrivilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * User: Allen
 * Date: 9/13/12
 */
public interface DataPrivilegeDao extends PagingAndSortingRepository<DataPrivilege, Integer> {

    public List<DataPrivilege> findByMaster(String master);

    @Query("from DataPrivilege")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<DataPrivilege> findCachedDataPrivilege();
}
