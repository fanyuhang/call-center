package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.phone.dao.RolerightDao;
import com.phone.entity.Roleright;
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
public class RolerightManager extends GenericPageHQLQuery<Roleright> {
    private static Logger logger = LoggerFactory.getLogger(RolerightManager.class);
    @Autowired
    private RolerightDao rolerightDao;

    public List<Roleright> findAll() {
        return (List<Roleright>) rolerightDao.findAll();
    }

    public Page<Roleright> find(String where, GridPageRequest page) {
        return (Page<Roleright>) super.findAll(where, page);
    }

    public Roleright find(int id) {
        return rolerightDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<Roleright> jiFenExchanges) {
        rolerightDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Roleright entity) {
        rolerightDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        rolerightDao.delete(id);
    }
}
