package com.redcard.system.dao;

import com.redcard.system.entity.Dictionary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * User: Allen
 * Date: 10/28/12
 */
public interface DictionaryDao extends PagingAndSortingRepository<Dictionary, Integer> {

    @Query("select d from Dictionary d order by d.type asc")
    public List<Dictionary> findAllDictionary();

    @Query("from Dictionary d order by d.type asc ")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<Dictionary> findCachedDictionary();

    public List<Dictionary> findDictionaryByType(Integer type);

    public List<Dictionary> findDictionaryByTypeAndName(Integer type, String name);

    public List<Dictionary> findDictionaryByTypeAndValue(Integer type, String value);
}
