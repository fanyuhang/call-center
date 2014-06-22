package com.redcard.system.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.system.dao.OperateLogDao;
import com.redcard.system.entity.OperateLog;
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
public class OperateLogManager extends GenericPageHQLQuery<OperateLog> {
    private static Logger logger = LoggerFactory.getLogger(OperateLogManager.class);
    @Autowired
    private OperateLogDao operateLogDao;

    public List<OperateLog> findAll() {
        return (List<OperateLog>) operateLogDao.findAll();
    }

    public Page<OperateLog> find(String where, GridPageRequest page) {
        return (Page<OperateLog>) super.findAll(where, page);
    }

    public OperateLog find(String id) {
        return operateLogDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<OperateLog> jiFenExchanges) {
        operateLogDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(OperateLog entity) {
        operateLogDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        operateLogDao.delete(id);
    }
}
