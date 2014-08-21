package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.phone.dao.CalllogDao;
import com.phone.entity.Calllog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * .
 * User: Administrator
 * Date: 12-9-6
 * Time: 上午12:09
 */
@Component
@Transactional(readOnly = true)
public class CalllogManager extends GenericPageHQLQuery<Calllog> {
    private static Logger logger = LoggerFactory.getLogger(CalllogManager.class);
    @Autowired
    private CalllogDao calllogDao;

    public List<Calllog> findAll() {
        return (List<Calllog>) calllogDao.findAll();
    }

    public Page<Calllog> find(String where, GridPageRequest page) {
        return (Page<Calllog>) super.findAll(where, page);
    }

    public Calllog find(long id) {
        return calllogDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<Calllog> jiFenExchanges) {
        calllogDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Calllog entity) {
        calllogDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(long id) {
        calllogDao.delete(id);
    }
}
