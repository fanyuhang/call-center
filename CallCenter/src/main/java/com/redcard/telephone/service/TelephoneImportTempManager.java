package com.redcard.telephone.service;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneImportTempDao;
import com.redcard.telephone.dao.mybatis.TelephoneImportTempMapper;
import com.redcard.telephone.entity.TelephoneImportTemp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: Allen
 * Date: 10/28/12
 */

@Component
public class TelephoneImportTempManager extends GenericPageHQLQuery<TelephoneImportTemp> {

    private static Logger logger = LoggerFactory.getLogger(TelephoneImportTempManager.class);

    @Autowired
    private TelephoneImportTempDao telephoneImportTempDao;

    @Autowired
    private TelephoneImportTempMapper telephoneImportTempMapper;

    public List<TelephoneImportTemp> findByDuplicateStatus(String batchNo, Integer duplicateStatus) {
        return telephoneImportTempDao.findByDuplicateStatus(batchNo, duplicateStatus);
    }

    @Transactional(readOnly = false)
    public void save(List<TelephoneImportTemp> list) {
        telephoneImportTempDao.save(list);
    }

    @Transactional(readOnly = false)
    public void updateDuplicateStatusWithPhone(Map<String, Object> param){
        telephoneImportTempMapper.updateDuplicateStatusWithPhone(param);
    }

    @Transactional(readOnly = false)
    public void updateDuplicateStatusWithMobile(Map<String, Object> param){
        telephoneImportTempMapper.updateDuplicateStatusWithMobile(param);
    }

    @Transactional(readOnly = false)
    public void telephoneImport(List<TelephoneImportTemp> list) {

    }


}
