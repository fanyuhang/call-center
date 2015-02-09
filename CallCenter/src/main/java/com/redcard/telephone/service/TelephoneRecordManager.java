package com.redcard.telephone.service;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.phone.dao.CalllogDao;
import com.phone.dao.TalklogDao;
import com.phone.entity.Calllog;
import com.phone.entity.Talklog;
import com.redcard.telephone.dao.TelephoneCustomerDao;
import com.redcard.telephone.dao.TelephoneRecordDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.entity.TelephoneTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class TelephoneRecordManager extends GenericPageHQLQuery<TelephoneRecord> {
    @Autowired
    private TelephoneRecordDao telephoneRecordDao;
    @Autowired
    private CalllogDao calllogDao;
    @Autowired
    private TalklogDao talklogDao;
    @Autowired
    private TelephoneTaskDao telephoneTaskDao;

    @Autowired
    private TelephoneCustomerDao telephoneCustomerDao;

    public List<TelephoneRecord> findByNameAndPhone(String name, String phone, String mobile) {
        List<TelephoneRecord> list = new ArrayList<TelephoneRecord>();
        if (!StringUtils.isBlank(mobile))
            list = telephoneRecordDao.findByNameAndPhone(name, mobile);
        List<TelephoneRecord> tmpList = telephoneRecordDao.findByNameAndPhone(name, phone);
        if (null != tmpList && tmpList.size() > 0) {
            list.addAll(tmpList);
        }

        return list;
    }

    @Transactional(readOnly = false)
    public void save(TelephoneRecord telephoneRecord) {
        String callId = telephoneRecord.getCallId();
        Calllog calllog = calllogDao.findOne(Long.valueOf(callId));
        if (calllog != null) {
            telephoneRecord.setFldCallBeginTime(calllog.getAnsweredTime());
            telephoneRecord.setFldCallEndTime(calllog.getHangUpTime());
            telephoneRecord.setFldCallLong(0);
            telephoneRecord.setFldCallDate(calllog.getInboundCallTime());
        }
        Talklog talkLog = talklogDao.findByCallId(Long.valueOf(callId));
        if (talkLog != null) {
            calllog = calllogDao.findOne(Long.valueOf(callId));
            telephoneRecord.setFldRecordFilePath(talkLog.getIispath());
            telephoneRecord.setFldCallLong(calllog.getTalkDuration());
        }
        telephoneRecordDao.save(telephoneRecord);
    }

    @Transactional(readOnly = false)
    public TelephoneRecord save(String telephone) {
        TelephoneRecord record = new TelephoneRecord();
        record.setFldOperateDate(new Date());
        record.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
        record.setFldCreateDate(new Date());
        record.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        record.setFldPhone(telephone);
        record.setFldCallType(Constant.TELEPHONE_CALL_TYPE_OUT);
        record.setFldCallDate(new Date());
        TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findByMobile(telephone);
        if (telephoneCustomer == null) {
            List<TelephoneCustomer> telephoneCustomers = telephoneCustomerDao.findByPhone(telephone);
            if (telephoneCustomers != null && telephoneCustomers.size() > 0) {
                telephoneCustomer = telephoneCustomers.get(0);
            }
        }

        if (telephoneCustomer != null) {
            record.setFldCustomerName(telephoneCustomer.getFldCustomerName());
        }

        telephoneRecordDao.save(record);
        return record;
    }

    @Transactional(readOnly = false)
    public void updateWithTelephone(String recordId, String callId) {
        TelephoneRecord telephoneRecord = telephoneRecordDao.findOne(Long.valueOf(recordId));
        Calllog calllog = calllogDao.findOne(Long.valueOf(callId));
        if (calllog != null) {
            telephoneRecord.setFldCallBeginTime(calllog.getAnsweredTime());
            telephoneRecord.setFldCallEndTime(calllog.getHangUpTime());
            telephoneRecord.setFldCallLong(0);
            telephoneRecord.setFldCallDate(calllog.getInboundCallTime());
        }
        Talklog talkLog = talklogDao.findByCallId(Long.valueOf(callId));

        if (talkLog != null) {
            calllog = calllogDao.findOne(Long.valueOf(callId));
            telephoneRecord.setFldRecordFilePath(talkLog.getIispath());
            telephoneRecord.setFldCallLong(calllog.getTalkDuration());
        }

        telephoneRecordDao.save(telephoneRecord);
    }

    public Page<TelephoneRecord> findAllTelephoneRecord(GridPageRequest page, String where) {
        return (Page<TelephoneRecord>) super.findAll(where, page);
    }

    public TelephoneRecord findById(String id) {
        return telephoneRecordDao.findOne(Long.valueOf(id));
    }

    public void saveAudti(String id, String taskId, String fldAuditFraction, String fldAuditComment) {
        //更新呼叫记录中的审查信息
        TelephoneRecord tmpTelephoneRecord = telephoneRecordDao.findOne(Long.valueOf(id));
        tmpTelephoneRecord.setFldAuditStatus(Constant.TELEPHONE_TASK_AUDIT_STATUS_ED);
        tmpTelephoneRecord.setFldAuditUserNo(SecurityUtil.getCurrentUserLoginName());
        tmpTelephoneRecord.setFldAuditFraction(Integer.valueOf(fldAuditFraction));
        tmpTelephoneRecord.setFldAuditComment(fldAuditComment);
        telephoneRecordDao.save(tmpTelephoneRecord);

        //更新话务任务中的审查信息
        TelephoneTask tmpTelephoneTask = telephoneTaskDao.findOne(Long.valueOf(taskId));
        tmpTelephoneTask.setFldAuditStatus(Constant.TELEPHONE_TASK_AUDIT_STATUS_ED);
        tmpTelephoneTask.setFldAuditUserNo(SecurityUtil.getCurrentUserLoginName());
        tmpTelephoneTask.setFldAuditFraction(Integer.valueOf(fldAuditFraction));
        tmpTelephoneTask.setFldAuditComment(fldAuditComment);
        telephoneRecordDao.save(tmpTelephoneRecord);
    }
}
