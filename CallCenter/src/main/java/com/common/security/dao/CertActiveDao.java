package com.common.security.dao;

import com.common.security.entity.CertActive;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Allen
 * Date: 11/4/12
 */
public interface CertActiveDao extends PagingAndSortingRepository<CertActive, String> {
    @Query("select ca from CertActive ca where not exists (select 1 from User u where u.certCN=ca.certCN)")
    public List<CertActive> findAvailableCert();

    @Modifying
    @Query("delete from CertActive ca where ca.certCN in (?1)")
    public void deleteByCertCN(List<String> certCNs);
}
