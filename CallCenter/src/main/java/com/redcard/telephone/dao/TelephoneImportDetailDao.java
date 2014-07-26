package com.redcard.telephone.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneImportDetail;

public interface TelephoneImportDetailDao extends PagingAndSortingRepository <TelephoneImportDetail,String> {

}
