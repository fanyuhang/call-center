package com.redcard.telephone.dao;

import java.util.List;

import com.redcard.telephone.entity.TelephoneImport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneImportDetail;

public interface TelephoneImportDetailDao extends PagingAndSortingRepository<TelephoneImportDetail,String> {
	@Query("select count(m) from TelephoneImportDetail m where m.fldAssignStatus = ?1")
    public Long countByAssignStatus(Integer assignStatus);
	
	@Query("select m from TelephoneImportDetail m where m.fldImportId = ?1")
	public List<TelephoneImportDetail> queryByImportId(String id);
	
	@Query("select m from TelephoneImportDetail m where m.fldTelephoneId = ?1")
	public List<TelephoneImportDetail> queryByCustId(String custId);

    @Modifying
    @Query("update TelephoneImportDetail m set m.fldAssignStatus=0 where m.fldId in (?1)")
    public void updateAssignStatusByIds(List<String> ids);

    @Query("select m.telephoneImport from TelephoneImportDetail m where m.fldId in (?1)")
    public List<TelephoneImport> findByImportDetailIds(List<String> ids);

    @Query("select count(m) from TelephoneImportDetail m where m.fldAssignStatus = 1 and m.fldImportId = ?1")
    public Long countByImportId(String importId);

    @Modifying
    @Query("delete from TelephoneImportDetail t where t.fldImportId = ?1 and t.fldAssignStatus = 0")
    public void deleteByImportId(String id);

    @Query("select count(m) from TelephoneImportDetail m where m.fldImportId = ?1")
    public Long countTotalNumberByImportId(String importId);
}
