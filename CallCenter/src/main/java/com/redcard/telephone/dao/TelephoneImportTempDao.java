package com.redcard.telephone.dao;

import com.redcard.telephone.entity.TelephoneAssign;
import com.redcard.telephone.entity.TelephoneImportTemp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TelephoneImportTempDao extends PagingAndSortingRepository<TelephoneImportTemp,Long> {

    @Query("select c from TelephoneImportTemp c where c.fldBatchNo = ?1 and c.fldDuplicateStatus = ?2")
    public List<TelephoneImportTemp> findByDuplicateStatus(String batchNo, Integer duplicateStatus);

}
