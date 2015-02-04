package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.core.util.GenericPageHQLQueryForPhone;
import com.phone.dao.UserlistDao;
import com.phone.entity.Userlist;
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
public class UserlistManager extends GenericPageHQLQueryForPhone<Userlist> {
    private static Logger logger = LoggerFactory.getLogger(UserlistManager.class);
    @Autowired
    private UserlistDao rolerightDao;

    public List<Userlist> findAll() {
        return (List<Userlist>) rolerightDao.findAll();
    }

    public Page<Userlist> find(String where, GridPageRequest page) {
        return (Page<Userlist>) super.findAll(where, page);
    }

    public Userlist find(int id) {
        return rolerightDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<Userlist> jiFenExchanges) {
        rolerightDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Userlist entity) {
        rolerightDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        rolerightDao.delete(id);
    }
}
