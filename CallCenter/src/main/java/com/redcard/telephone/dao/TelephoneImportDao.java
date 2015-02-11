package com.redcard.telephone.dao;

import com.redcard.telephone.entity.TelephoneImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TelephoneImportDao extends PagingAndSortingRepository<TelephoneImport, String> {
    @Modifying
    @Query("update TelephoneImport m set m.fldAssignTotalNumber=m.fldAssignTotalNumber+?1 where m.fldId = ?2")
    public void updateAssignNumber(Integer fldAssignTotalNumber, String fldId);

    @Query("select m from TelephoneImport m where m.fldName like ?1 and m.fldOperateUserNo = ?2 and m.fldAssignTotalNumber!=m.fldImportTotalNumber")
    public Page<TelephoneImport> findTelephoneImportByNameAndLoginName(String name, String loginName, Pageable page);
}
