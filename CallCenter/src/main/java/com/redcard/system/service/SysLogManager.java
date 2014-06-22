package com.redcard.system.service;

import com.common.AppContext;
import com.common.core.tree.TreeNode;
import com.common.core.util.EntityUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;
import com.redcard.system.dao.SysLogDao;
import com.redcard.system.entity.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * User: Allen
 * Date: 11/25/12
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class SysLogManager extends GenericPageHQLQuery<SysLog> {

    private static Logger logger = LoggerFactory.getLogger(SysLogManager.class);
    @Autowired
    private SysLogDao  sysLogDao;
    @Autowired
    private AppContext appContext;

    public void setSysLogDao(SysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    @Transactional(readOnly = false)
    public void saveSysLog(SysLog entity) {
        sysLogDao.save(entity);
    }

    public Page<SysLog> findAll(Date startDate, Date endDate, Pageable page) {
        return sysLogDao.findAll(startDate, endDate, page);
    }

    @Transactional(readOnly = false)
    public void saveSysLog(Object target, String action) {
        Object id = EntityUtil.getEntityId(target);
        String comment = new StringBuffer().append(action).append(EntityUtil.getEntityComment(target)).append(", ID=").append(id == null ? "" : id.toString()).toString();
        String link = SecurityUtil.getRequestResource();
        TreeNode treeNode = appContext.findTreeNode(link);
        if (treeNode != null) {
            saveSysLog(action, treeNode == null ? link : treeNode.getFullPath(), comment);
        }
    }

    @Transactional(readOnly = false)
    public void saveSysLog(String action, String comment) {
        String link = SecurityUtil.getRequestResource();
        TreeNode treeNode = appContext.findTreeNode(link);
        if (treeNode != null) {
            saveSysLog(action, treeNode == null ? link : treeNode.getFullPath(), comment);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveSysLog(String action, String resource, String comment) {
        SysLog sysLog = new SysLog();
        User operator = SecurityUtil.getCurrentUser();

        Date now = new Date();

        if (operator != null) {
            sysLog.setOperateId(operator.getId());
            sysLog.setLoginName(operator.getLoginName());
        } else {
            return;
        }
        sysLog.setOperateDate(now);
        sysLog.setAction(action);
        sysLog.setResource(resource);
        if (StringUtils.isNotBlank(resource) && !resource.startsWith("/登陆")) {
            sysLog.setComment(comment);
        }

        sysLogDao.save(sysLog);
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
}
