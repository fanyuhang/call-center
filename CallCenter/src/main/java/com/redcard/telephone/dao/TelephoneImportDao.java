package com.redcard.telephone.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.telephone.entity.TelephoneImport;

public interface TelephoneImportDao extends PagingAndSortingRepository <TelephoneImport,String> {

}
