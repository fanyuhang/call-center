package com.redcard.system.dao;

import com.redcard.system.entity.SysParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * User: Allen
 * Date: 1/19/13
 */
public interface SysParameterDao extends PagingAndSortingRepository<SysParameter, Integer> {

    @Query("from SysParameter p order by p.name asc ")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<SysParameter> findCachedParameter();

}
