package com.redcard.telephone.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.common.TelephoneProductStatusEnum;
import com.redcard.telephone.dao.TelephoneProductDao;
import com.redcard.telephone.entity.TelephoneProduct;
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
public class TelephoneProductManager extends GenericPageHQLQuery<TelephoneProduct> {

    private static Logger logger = LoggerFactory.getLogger(TelephoneProductManager.class);

    @Autowired
    private TelephoneProductDao telephoneProductDao;

    public TelephoneProduct find(Integer id) {
        return telephoneProductDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(TelephoneProduct entity) {
        if (entity.getFldId() != null) {
            TelephoneProduct oldTelephoneProduct = telephoneProductDao.findOne(entity.getFldId());
            oldTelephoneProduct.setFldStatus(entity.getFldStatus());
            oldTelephoneProduct.setFldTitle(entity.getFldTitle());
            oldTelephoneProduct.setFldContent(entity.getFldContent());
            oldTelephoneProduct.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            oldTelephoneProduct.setFldOperateDate(new Date());
            telephoneProductDao.save(oldTelephoneProduct);
        } else {
            entity.setFldOperateDate(new Date());
            entity.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            entity.setFldCreateDate(new Date());
            entity.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneProductDao.save(entity);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        TelephoneProduct telephoneProduct = telephoneProductDao.findOne(id);
        telephoneProduct.setFldStatus(TelephoneProductStatusEnum.DISABLE.getCode());
        telephoneProductDao.save(telephoneProduct);
    }
}
