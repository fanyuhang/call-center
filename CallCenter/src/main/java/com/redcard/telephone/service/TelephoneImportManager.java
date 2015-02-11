package com.redcard.telephone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneImportDao;
import com.redcard.telephone.dao.TelephoneImportDetailDao;
import com.redcard.telephone.entity.TelephoneImport;
import com.redcard.telephone.entity.TelephoneImportDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class TelephoneImportManager extends GenericPageHQLQuery<TelephoneImport> {
    @Autowired
    private TelephoneImportDao telephoneImportDao;
    @Autowired
    private TelephoneImportDetailDao telephoneImportDetailDao;

    public void save(TelephoneImport telephoneImport) {
        telephoneImportDao.save(telephoneImport);
    }

    public Page<TelephoneImport> findAllTelephoneImport(GridPageRequest page, String where) {
        return (Page<TelephoneImport>) super.findAll(where, page);
    }

    public Page<TelephoneImport> findTelephoneImportByNameAndLoginName(String name, String loginName,Pageable page){
        if(StringUtils.isNotBlank(name)){
            return telephoneImportDao.findTelephoneImportByNameAndLoginName("%"+name+"%",loginName,page);
        }else{
            return telephoneImportDao.findTelephoneImportByNameAndLoginName("%%",loginName,page);
        }
    }

    public TelephoneImport findById(String id) {
        return telephoneImportDao.findOne(id);
    }

    public List<TelephoneImport> listAllUnAssignTelephone() {
        return (List<TelephoneImport>) telephoneImportDao.findAll();
    }

    public Integer countById(String id) {
        TelephoneImport telephoneImport = telephoneImportDao.findOne(id);
        return telephoneImport.getFldImportTotalNumber() - telephoneImport.getFldAssignTotalNumber();
    }

    public void updateAssignNumber(Integer fldAssignTotalNumber, String fldId) {
        telephoneImportDao.updateAssignNumber(fldAssignTotalNumber, fldId);
    }

    public List<TelephoneImportDetail> viewDtl(String id) {
        return telephoneImportDetailDao.queryByImportId(id);
    }
}
