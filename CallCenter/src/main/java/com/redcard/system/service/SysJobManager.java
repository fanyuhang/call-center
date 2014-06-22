package com.redcard.system.service;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.system.dao.SysJobDao;
import com.redcard.system.entity.SysJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Allen
 * Date: 12/6/12
 */
@Component
@Transactional(readOnly = true)
public class SysJobManager extends GenericPageHQLQuery<SysJob> {

    private static Logger logger = LoggerFactory.getLogger(SysJobManager.class);

    @Autowired
    private SysJobDao sysJobDao;

    public void setSysJobDao(SysJobDao sysJobDao) {
        this.sysJobDao = sysJobDao;
    }

    public List<SysJob> findAvailableJob() {
        return this.sysJobDao.findSysJobByStatus(Constant.JOB_ENABLE);
    }

    public Page<SysJob> find(String where, GridPageRequest page) {
        return (Page<SysJob>) super.findAll(where, page);
    }

    public SysJob find(Integer id) {
        return sysJobDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(SysJob entity) {
        sysJobDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        sysJobDao.delete(id);
    }
}
