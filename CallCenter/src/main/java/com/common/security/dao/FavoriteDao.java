package com.common.security.dao;

import com.common.security.entity.Favorite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FavoriteDao extends PagingAndSortingRepository<Favorite, Integer> {

    @Modifying
    @Query("delete Favorite f where f.userId = ?1 ")
    public void deleteByUserId(Integer userId);

    @Modifying
    @Query("delete Favorite f where f.node.code = ?1")
    public void deleteByNodeCode(String nodeCode);

    @Query("select f from Favorite f where f.userId = ?1 order by f.addTime")
    public List<Favorite> findByUserId(Integer userId);

    @Modifying
    @Query("delete Favorite f where f.userId = ?1 and f.node.code = ?2")
    public void deleteByUserIdAndCode(Integer userId, String code);

}
