package com.redcard.telephone.service;

import com.common.AppContext;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.message.service.MessageOperateManager;
import com.redcard.telephone.common.*;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.dao.TelephoneTraceDao;
import com.redcard.telephone.dao.TelephoneTraceLogDao;
import com.redcard.telephone.entity.TelephoneTrace;
import com.redcard.telephone.entity.TelephoneTraceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Allen
 * Date: 10/28/12
 */

@Component
public class TelephoneTraceManager extends GenericPageHQLQuery<TelephoneTrace> {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceManager.class);

    @Autowired
    private TelephoneTraceDao telephoneTraceDao;

    @Autowired
    private TelephoneTraceLogDao telephoneTraceLogDao;

    @Autowired
    private TelephoneTaskDao telephoneTaskDao;

    public TelephoneTrace find(Long id) {
        return telephoneTraceDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(TelephoneTrace entity) {
        if (entity.getFldId() != null) {
            TelephoneTrace oldTelephoneTrace = telephoneTraceDao.findOne(entity.getFldId());
            oldTelephoneTrace.setFldStatus(entity.getFldStatus());
            oldTelephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            oldTelephoneTrace.setFldOperateDate(new Date());
            telephoneTraceDao.save(oldTelephoneTrace);
        } else {
            entity.setFldOperateDate(new Date());
            entity.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            entity.setFldCreateDate(new Date());
            entity.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceDao.save(entity);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        TelephoneTrace telephoneTrace = telephoneTraceDao.findOne(id);
        telephoneTrace.setFldStatus(TelephoneTraceStatusEnum.INVALID.getCode());
        telephoneTraceDao.save(telephoneTrace);
    }

    @Transactional(readOnly = false)
    public void update(String ids, String financialUserNo) {
        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTrace.setFldAssignUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldAssignStatus(TelephoneTraceAssignStatusEnum.DONE_ASSIGN.getCode());
            telephoneTrace.setFldFinancialUserNo(financialUserNo);
            telephoneTrace.setFldAssignDate(new Date());
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
        }

        telephoneTraceDao.save(telephoneTraceList);
    }

    @Transactional(readOnly = false)
    public void reset(String ids) {
        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);

        List<TelephoneTraceLog> telephoneTraceLogList = new ArrayList<TelephoneTraceLog>();
        TelephoneTraceLog telephoneTraceLog = null;
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTrace.setFldAuditStatus(TelephoneTraceAuditStatusEnum.WAIT_AUDIT.getCode());
            telephoneTrace.setFldAssignStatus(TelephoneTraceAssignStatusEnum.WAIT_ASSIGN.getCode());
            telephoneTrace.setFldFinancialUserNo(null);
            telephoneTrace.setFldAssignDate(null);
            telephoneTrace.setFldAuditDate(null);
            telephoneTrace.setFldAuditUserNo(null);
            telephoneTrace.setFldAssignUserNo(null);
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog = new TelephoneTraceLog();
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(telephoneTrace.getFldComment());
            telephoneTraceLog.setFldStatusDesc("重置");
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());
            telephoneTraceLogList.add(telephoneTraceLog);
        }
        telephoneTraceDao.save(telephoneTraceList);
        telephoneTraceLogDao.save(telephoneTraceLogList);
    }

    @Transactional(readOnly = false)
    public void assign(String ids, String financialUserNo) {
        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);

        List<TelephoneTraceLog> telephoneTraceLogList = new ArrayList<TelephoneTraceLog>();
        TelephoneTraceLog telephoneTraceLog = null;
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTraceLog = new TelephoneTraceLog();
            telephoneTrace.setFldAssignUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldAssignStatus(TelephoneTraceAssignStatusEnum.DONE_ASSIGN.getCode());
            telephoneTrace.setFldFinancialUserNo(financialUserNo);
            telephoneTrace.setFldAssignDate(new Date());
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(telephoneTrace.getFldComment());
            telephoneTraceLog.setFldStatusDesc(AppContext.getInstance().getDictName(35, telephoneTrace.getFldAssignStatus() + ""));
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());
            telephoneTraceLogList.add(telephoneTraceLog);
        }

        telephoneTraceDao.save(telephoneTraceList);
        telephoneTraceLogDao.save(telephoneTraceLogList);

    }

    @Transactional(readOnly = false)
    public List<TelephoneTrace> pass(String ids) {
        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);

        List<TelephoneTraceLog> telephoneTraceLogList = new ArrayList<TelephoneTraceLog>();
        TelephoneTraceLog telephoneTraceLog = null;
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTrace.setFldAuditStatus(TelephoneTraceAuditStatusEnum.DONE_AUDIT.getCode());
            telephoneTrace.setFldAuditDate(new Date());
            telephoneTrace.setFldAuditUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog = new TelephoneTraceLog();
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(telephoneTrace.getFldComment());
            telephoneTraceLog.setFldStatusDesc(AppContext.getInstance().getDictName(36,telephoneTrace.getFldAuditStatus()+""));
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());
            telephoneTraceLogList.add(telephoneTraceLog);
        }
        telephoneTraceDao.save(telephoneTraceList);
        telephoneTraceLogDao.save(telephoneTraceLogList);
        return telephoneTraceList;
    }

    @Transactional(readOnly = false)
    public void nopass(String ids) {
        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);

        List<TelephoneTraceLog> telephoneTraceLogList = new ArrayList<TelephoneTraceLog>();
        TelephoneTraceLog telephoneTraceLog = null;
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTrace.setFldAuditStatus(TelephoneTraceAuditStatusEnum.NOT_AUDIT.getCode());
            telephoneTrace.setFldAuditDate(new Date());
            telephoneTrace.setFldAuditUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog = new TelephoneTraceLog();
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(telephoneTrace.getFldComment());
            telephoneTraceLog.setFldStatusDesc(AppContext.getInstance().getDictName(36,telephoneTrace.getFldAuditStatus()+""));
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());
            telephoneTraceLogList.add(telephoneTraceLog);
        }
        telephoneTraceDao.save(telephoneTraceList);
        telephoneTraceLogDao.save(telephoneTraceLogList);
    }

    @Transactional(readOnly = false)
    public void updateResultStatus(Integer resultStatus, String ids, String comment){

        String[] idStrs = ids.split("\\,");
        List<Long> list = new ArrayList<Long>();
        for (String id : idStrs) {
            list.add(Long.valueOf(id));
        }

        List<TelephoneTrace> telephoneTraceList = telephoneTraceDao.findByIds(list);

        List<TelephoneTraceLog> telephoneTraceLogList = new ArrayList<TelephoneTraceLog>();
        TelephoneTraceLog telephoneTraceLog = null;
        List<Long> taskIds = new ArrayList<Long>();
        for (TelephoneTrace telephoneTrace : telephoneTraceList) {
            telephoneTrace.setFldResultStatus(resultStatus);
            if(TelephoneTraceFinishStatusEnum.DONE_FINISH.getCode().compareTo(resultStatus)==0
                    ||TelephoneTraceFinishStatusEnum.NOT_FINISH.getCode().compareTo(resultStatus)==0){
                telephoneTrace.setFldFinishDate(new Date());
            }
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldComment(telephoneTrace.getFldComment()+";"+comment);
            telephoneTraceLog = new TelephoneTraceLog();
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(comment);
            telephoneTraceLog.setFldStatusDesc(AppContext.getInstance().getDictName(37,telephoneTrace.getFldResultStatus()+""));
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());
            telephoneTraceLogList.add(telephoneTraceLog);
            taskIds.add(telephoneTrace.getFldTaskId());
        }
        telephoneTraceDao.save(telephoneTraceList);
        telephoneTraceLogDao.save(telephoneTraceLogList);
        telephoneTaskDao.updateTaskStatusByIds(TelephoneTaskStatusEnum.DONE_FINISH.getCode(),taskIds);
    }
}
