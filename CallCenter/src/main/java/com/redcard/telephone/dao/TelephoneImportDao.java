package com.redcard.telephone.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneImport;

public interface TelephoneImportDao extends PagingAndSortingRepository<TelephoneImport,String> {
	@Modifying
    @Query("update TelephoneImport m set m.fldAssignTotalNumber=m.fldAssignTotalNumber+?1 where m.fldId = ?2")
    public void updateAssignNumber(Integer fldAssignTotalNumber,String fldId);
}
