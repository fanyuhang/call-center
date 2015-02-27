package com.redcard.telephone.service;

import com.common.AppContext;
import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.phone.dao.CalllogDao;
import com.phone.dao.TalklogDao;
import com.phone.entity.Calllog;
import com.phone.entity.Talklog;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.entity.Customer;
import com.redcard.telephone.common.*;
import com.redcard.telephone.dao.*;
import com.redcard.telephone.entity.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Sort;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class TelephoneTaskManager extends GenericPageHQLQuery<TelephoneTask> {
    @Autowired
    private TelephoneTaskDao telephoneTaskDao;
    @Autowired
    private TelephoneRecordDao telephoneRecordDao;
    @Autowired
    private TelephoneCustomerDao telephoneCustomerDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TelephoneAssignDetailDao telephoneAssignDetailDao;
    @Autowired
    private CalllogDao calllogDao;
    @Autowired
    private TalklogDao talklogDao;
    @Autowired
    private TelephoneImportDetailDao telephoneImportDetailDao;
    @Autowired
    private TelephoneTraceDao telephoneTraceDao;
    @Autowired
    private TelephoneTraceLogDao telephoneTraceLogDao;

    public long getCount() {
        return telephoneTaskDao.count();
    }

    public Page<TelephoneTask> listTask(GridPageRequest page, String where) {
        return (Page<TelephoneTask>) super.findAll(where, page);
    }

    public List<TelephoneTask> listByAssignDtlId(String assignDtlId) {
        return telephoneTaskDao.listByAssignDetailId(assignDtlId);
    }

    public Page<TelephoneTask> listTask(String callUserNo,GridPageRequest page) {
        org.springframework.data.domain.Sort.Order order1 = new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.DESC, "fldTaskType");
        org.springframework.data.domain.Sort.Order order2 = new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.ASC, "fldTaskDate");
        org.springframework.data.domain.Sort sort = new org.springframework.data.domain.Sort(order1,order2);
        page.setSort(sort);
        return telephoneTaskDao.findByCallUserNoAndTaskStatus(callUserNo,TelephoneTaskStatusEnum.DONE_FINISH.getCode(),page);
    }

    @Transactional(readOnly = false)
    public String save(TelephoneRecord telephoneRecord) {
        TelephoneTask oldTelephoneTask = telephoneTaskDao.findOne(telephoneRecord.getFldTaskId());
        Integer oldStatus = oldTelephoneTask.getFldCallStatus();
        Integer oldTaskStatus = oldTelephoneTask.getFldTaskStatus();

        oldTelephoneTask.setFldResultType(telephoneRecord.getFldResultType());
        oldTelephoneTask.setFldOperateDate(new Date());
        oldTelephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_ED);
        oldTelephoneTask.setFldCallDate(new Date());
        oldTelephoneTask.setFldTaskStatus(telephoneRecord.getFldTaskStatus());
        oldTelephoneTask.setFldComment(telephoneRecord.getFldComment());
        telephoneTaskDao.save(oldTelephoneTask);

        telephoneRecord.setFldCustomerName(oldTelephoneTask.getFldCustomerName());
        telephoneRecord.setFldCallType(Constant.TELEPHONE_CALL_TYPE_OUT);
        telephoneRecord.setFldCreateDate(new Date());
        telephoneRecord.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        telephoneRecord.setFldOperateDate(new Date());
        telephoneRecord.setFldCallBeginTime(new Date());
        telephoneRecord.setFldCallEndTime(new Date());
        telephoneRecord.setFldCallLong(0);
        telephoneRecord.setFldCallLong(0);
        telephoneRecord.setFldTotalDuration(0);
        telephoneRecord.setFldWaitTime(0);
        telephoneRecord.setFldCallDate(new Date());

        String callId = telephoneRecord.getCallId();
        if (!StringUtils.isBlank(callId)) {
            Calllog calllog = calllogDao.findOne(Long.valueOf(callId));
            if(calllog!=null){
                telephoneRecord.setFldCallBeginTime(calllog.getAnsweredTime());
                telephoneRecord.setFldCallEndTime(calllog.getHangUpTime());
                telephoneRecord.setFldCallLong(calllog.getTalkDuration());
                telephoneRecord.setFldCallDate(calllog.getInboundCallTime());
                telephoneRecord.setFldCallLong(calllog.getTalkDuration());
                telephoneRecord.setFldTotalDuration(calllog.getTotalDuration());
                telephoneRecord.setFldWaitTime(calllog.getWaitTime());
            }
            Talklog talkLog = talklogDao.findByCallId(Long.valueOf(callId));
            if(talkLog!=null){
                telephoneRecord.setFldRecordFilePath(talkLog.getIispath());
            }
        }

        telephoneRecordDao.save(telephoneRecord);

        //更新话单原始表中的最近拨打时间
        TelephoneImportDetail telephoneImportDetail = telephoneImportDetailDao.findOne(oldTelephoneTask.getFldCustomerId());
        if (telephoneImportDetail != null && telephoneImportDetail.getFldTelephoneId() != null) {
            TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findOne(telephoneImportDetail.getFldTelephoneId());
            if(telephoneCustomer!=null){
                telephoneCustomer.setFldLatestCallDate(telephoneRecord.getFldCallDate());
                telephoneCustomer.setFldCallUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneCustomerDao.save(telephoneCustomer);
            }
        }

        //记录待约访客户
        if(TelephoneTaskResultStatusEnum.RESERVE.getCode().compareTo(telephoneRecord.getFldResultType())==0){
            TelephoneTrace telephoneTrace = new TelephoneTrace();
            telephoneTrace.setFldCustomerId(oldTelephoneTask.getFldCustomerId());
            telephoneTrace.setFldImportId(oldTelephoneTask.getFldImportId());
            telephoneTrace.setFldPhone(oldTelephoneTask.getFldPhone());
            telephoneTrace.setFldMobile(oldTelephoneTask.getFldMobile());
            telephoneTrace.setFldCustomerName(oldTelephoneTask.getFldCustomerName());
            telephoneTrace.setFldAssignStatus(TelephoneTraceAssignStatusEnum.WAIT_ASSIGN.getCode());
            telephoneTrace.setFldAuditStatus(TelephoneTraceAuditStatusEnum.WAIT_AUDIT.getCode());
            telephoneTrace.setFldResultStatus(TelephoneTraceFinishStatusEnum.NOT_FINISH.getCode());
            telephoneTrace.setFldStatus(TelephoneTraceStatusEnum.VALID.getCode());
            telephoneTrace.setFldCallUserNo(oldTelephoneTask.getFldCallUserNo());
            telephoneTrace.setFldComment(oldTelephoneTask.getFldComment());
            telephoneTrace.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldOperateDate(new Date());
            telephoneTrace.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTrace.setFldCreateDate(new Date());

            telephoneTraceDao.save(telephoneTrace);

            TelephoneTraceLog telephoneTraceLog = new TelephoneTraceLog();
            telephoneTraceLog.setFldTraceId(telephoneTrace.getFldId());
            telephoneTraceLog.setFldComment(telephoneTrace.getFldComment());
            telephoneTraceLog.setFldStatusDesc(AppContext.getInstance().getDictName(35,telephoneTrace.getFldAssignStatus()+""));
            telephoneTraceLog.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldOperateDate(new Date());
            telephoneTraceLog.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTraceLog.setFldCreateDate(new Date());

            telephoneTraceLogDao.save(telephoneTraceLog);
        }
        return oldTelephoneTask.getFldAssignDetailId();
    }

    @Transactional(readOnly = false)
    public void updateCust(String taskId, TelephoneCustomer telephoneCustomer, Customer customer) {
        //更新话单原始表
        TelephoneCustomer tmpTelephoneCustomer = telephoneCustomerDao.findOne(telephoneCustomer.getFldId());
        tmpTelephoneCustomer.setFldCustomerName(telephoneCustomer.getFldCustomerName());
        tmpTelephoneCustomer.setFldGender(telephoneCustomer.getFldGender());
        tmpTelephoneCustomer.setFldMobile(telephoneCustomer.getFldMobile());
        tmpTelephoneCustomer.setFldPhone(telephoneCustomer.getFldPhone());
        tmpTelephoneCustomer.setFldAddress(telephoneCustomer.getFldAddress());
        tmpTelephoneCustomer.setFldBirthday(customer.getFldBirthday());
        tmpTelephoneCustomer.setFldIdentityNo(customer.getFldIdentityNo());
        tmpTelephoneCustomer.setFldEmail(customer.getFldEmail());
//        tmpTelephoneCustomer.setFldFinancialUserNo(telephoneCustomer.getFldFinancialUserNo());
        telephoneCustomerDao.save(tmpTelephoneCustomer);

        //若客户存在，则更新客户表
        if (!StringUtils.isBlank(customer.getFldId())) {
            Customer tmpCustomer = customerDao.findOne(customer.getFldId());
            tmpCustomer.setFldName(telephoneCustomer.getFldCustomerName());
            tmpCustomer.setFldGender(telephoneCustomer.getFldGender());
            tmpCustomer.setFldMobile(telephoneCustomer.getFldMobile());
            tmpCustomer.setFldPhone(telephoneCustomer.getFldPhone());
            tmpCustomer.setFldBirthday(customer.getFldBirthday());
            tmpCustomer.setFldIdentityNo(customer.getFldIdentityNo());
            tmpCustomer.setFldAddress(telephoneCustomer.getFldAddress());
            tmpCustomer.setFldEmail(customer.getFldEmail());
            customerDao.save(tmpCustomer);
        }

        //更新话务任务表
        TelephoneTask tmpTelephoneTask = telephoneTaskDao.findOne(Long.valueOf(taskId));
        tmpTelephoneTask.setFldCustomerName(telephoneCustomer.getFldCustomerName());
        telephoneTaskDao.save(tmpTelephoneTask);
    }
}
