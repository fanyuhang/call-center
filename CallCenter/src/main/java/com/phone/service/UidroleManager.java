package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.phone.dao.UidroleDao;
import com.phone.entity.Uidrole;
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
public class UidroleManager extends GenericPageHQLQuery<Uidrole> {
    private static Logger logger = LoggerFactory.getLogger(UidroleManager.class);
    @Autowired
    private UidroleDao uidroleDao;

    public List<Uidrole> findAll() {
        return (List<Uidrole>) uidroleDao.findAll();
    }

    public Page<Uidrole> find(String where, GridPageRequest page) {
        return (Page<Uidrole>) super.findAll(where, page);
    }

    public Uidrole find(int id) {
        return uidroleDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<Uidrole> jiFenExchanges) {
        uidroleDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Uidrole entity) {
        uidroleDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        uidroleDao.delete(id);
    }
}
