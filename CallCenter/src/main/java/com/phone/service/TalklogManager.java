package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.phone.dao.TalklogDao;
import com.phone.entity.Talklog;
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
public class TalklogManager extends GenericPageHQLQuery<Talklog> {
    private static Logger logger = LoggerFactory.getLogger(TalklogManager.class);
    @Autowired
    private TalklogDao talklogDao;

    public List<Talklog> findAll() {
        return (List<Talklog>) talklogDao.findAll();
    }

    public Page<Talklog> find(String where, GridPageRequest page) {
        return (Page<Talklog>) super.findAll(where, page);
    }

    public Talklog find(long id) {
        return talklogDao.findOne(id);
    }

    public Talklog findByCallId(Integer callId){
        return talklogDao.findByCallId(callId);
    }

    @Transactional(readOnly = false)
    public void save(List<Talklog> jiFenExchanges) {
        talklogDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Talklog entity) {
        talklogDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(long id) {
        talklogDao.delete(id);
    }
}
