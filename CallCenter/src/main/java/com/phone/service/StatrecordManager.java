package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.core.util.GenericPageHQLQueryForPhone;
import com.phone.dao.StatrecordDao;
import com.phone.dao.mybatis.StatrecordMapper;
import com.phone.entity.Statrecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * .
 * User: Administrator
 * Date: 12-9-6
 * Time: 上午12:09
 */
@Component
@Transactional(readOnly = true)
public class StatrecordManager extends GenericPageHQLQueryForPhone<Statrecord> {
    private static Logger logger = LoggerFactory.getLogger(StatrecordManager.class);

    @Autowired
    private StatrecordDao statrecordDao;

    @Autowired
    private StatrecordMapper statrecordMapper;

    public List<Statrecord> monitor(){
        return statrecordMapper.operatorStat();
    }

    public List<Statrecord> findAll() {
        return (List<Statrecord>) statrecordDao.findAll();
    }

    public Page<Statrecord> find(String where, GridPageRequest page) {
        return (Page<Statrecord>) super.findAll(where, page);
    }

    public Statrecord find(long id) {
        return statrecordDao.findOne(id);
    }
}
