package com.common.security.dao;

import com.common.security.entity.Node;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * User: Allen
 * Date: 9/2/12
 */
public interface NodeDao extends PagingAndSortingRepository<Node, String> {

    @Query("select n from Node n order by n.parent asc, n.position asc ")
    public List<Node> findAllNode();

    @Query("select n from Node n where n.parent=?1 order by n.position asc")
    public List<Node> findChildren(String parent);

    @Query("select max(n.code) from Node n where n.parent=?1")
    public String findMaxChildCode(String parent);

    @Query("from Node n order by n.parent asc, n.position asc")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<Node> findCachedNode();

}
