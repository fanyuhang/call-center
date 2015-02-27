package com.redcard.telephone.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.dao.TelephoneTraceLogDao;
import com.redcard.telephone.entity.TelephoneTraceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: Allen
 * Date: 10/28/12
 */

@Component
public class TelephoneTraceLogManager extends GenericPageHQLQuery<TelephoneTraceLog> {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceLogManager.class);

    @Autowired
    private TelephoneTraceLogDao telephoneTraceLogDao;

    public TelephoneTraceLog find(Long id) {
        return telephoneTraceLogDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(TelephoneTraceLog entity) {
        entity.setFldOperateDate(new Date());
        entity.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
        entity.setFldCreateDate(new Date());
        entity.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        telephoneTraceLogDao.save(entity);
    }

}
