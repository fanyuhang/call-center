package com.common.security.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.dao.CertActiveDao;
import com.common.security.dao.UserDao;
import com.common.security.entity.CertActive;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: Allen
 * Date: 11/4/12
 */
@Component
@Transactional(readOnly = true)
public class CertActiveManager extends GenericPageHQLQuery<CertActive> {

    private static Logger logger = LoggerFactory.getLogger(CertActiveManager.class);

    @Autowired
    private CertActiveDao certActiveDao;

    @Autowired
    private UserDao userDao;

    public void setCertActiveDao(CertActiveDao certActiveDao) {
        this.certActiveDao = certActiveDao;
    }

    public CertActive findCertActive(String certCN) {
        return certActiveDao.findOne(certCN);
    }

    @Transactional(readOnly = false)
    public void saveCertActive(CertActive certActive) {
        certActiveDao.save(certActive);
    }

    @Transactional(readOnly = false)
    public void deleteCertActive(String certCN) {
        certActiveDao.delete(certCN);
    }

    @Transactional(readOnly = false)
    public void deleteCertActives(String certCNs) {
        List<String> list = Arrays.asList(certCNs.split("\\,"));
        List<User> users = userDao.findByCertCN(list);
        for(User user:users){
            user.setCertCN(null);
            user.setOperateDate(new Date());
            user.setOperateId(SecurityUtil.getCurrentUserId());
        }
        userDao.save(users);
        certActiveDao.deleteByCertCN(list);
    }
}
