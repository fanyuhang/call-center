package com.redcard.telephone.service;

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
import com.redcard.telephone.common.TelephoneAssignFinishStatusEnum;
import com.redcard.telephone.common.TelephoneTaskStatusEnum;
import com.redcard.telephone.dao.*;
import com.redcard.telephone.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public long getCount() {
        return telephoneTaskDao.count();
    }

    public Page<TelephoneTask> listTask(GridPageRequest page, String where) {
        return (Page<TelephoneTask>) super.findAll(where, page);
    }

    public List<TelephoneTask> listByAssignDtlId(String assignDtlId) {
        return telephoneTaskDao.listByAssignDetailId(assignDtlId);
    }

    @Transactional(readOnly = false)
    public void save(TelephoneRecord telephoneRecord) {
        TelephoneTask oldTelephoneTask = telephoneTaskDao.findOne(telephoneRecord.getFldTaskId());
        Integer oldStatus = oldTelephoneTask.getFldCallStatus();
        Integer oldTaskStatus = oldTelephoneTask.getFldTaskStatus();

        oldTelephoneTask.setFldResultType(telephoneRecord.getFldResultType());
        oldTelephoneTask.setFldOperateDate(new Date());
        oldTelephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_ED);
        oldTelephoneTask.setFldCallDate(new Date());
        oldTelephoneTask.setFldTaskStatus(telephoneRecord.getFldTaskStatus());
        telephoneTaskDao.save(oldTelephoneTask);

        telephoneRecord.setFldCustomerName(oldTelephoneTask.getFldCustomerName());
        telephoneRecord.setFldCallType(Constant.TELEPHONE_CALL_TYPE_OUT);
        telephoneRecord.setFldCreateDate(new Date());
        telephoneRecord.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        telephoneRecord.setFldOperateDate(new Date());

        String callId = telephoneRecord.getCallId();
        if (!StringUtils.isBlank(callId)) {
            Calllog calllog = calllogDao.findOne(Long.valueOf(callId));
            telephoneRecord.setFldCallBeginTime(calllog.getAnsweredTime());
            telephoneRecord.setFldCallEndTime(calllog.getHangUpTime());
            telephoneRecord.setFldCallLong(calllog.getTalkDuration());
            telephoneRecord.setFldCallDate(calllog.getInboundCallTime());
            Talklog talkLog = talklogDao.findByCallId(Long.valueOf(callId));
            telephoneRecord.setFldRecordFilePath(talkLog.getIispath());
        }

        telephoneRecordDao.save(telephoneRecord);

        String assignDetailId = oldTelephoneTask.getFldAssignDetailId();
        TelephoneAssignDetail telephoneAssignDetail = telephoneAssignDetailDao.findOne(assignDetailId);

        //更新话务分配明细表的已拨打数
        if (TelephoneTaskStatusEnum.DONE_FINISH.getCode().equals(oldTelephoneTask.getFldTaskStatus())
                && !TelephoneTaskStatusEnum.DONE_FINISH.getCode().equals(oldTaskStatus)) {
            if (TelephoneTaskStatusEnum.WAITING_FINISH.getCode().equals(oldTaskStatus)) {
                if (telephoneAssignDetail.getFldFollowNumber() != null && telephoneAssignDetail.getFldFollowNumber().intValue() > 0) {
                    telephoneAssignDetail.setFldFinishNumber(telephoneAssignDetail.getFldFollowNumber() - 1);
                } else {
                    telephoneAssignDetail.setFldFinishNumber(0);
                }
            }
            telephoneAssignDetail.setFldFinishNumber(telephoneAssignDetail.getFldFinishNumber() + 1);
            if (telephoneAssignDetail.getFldTaskNumber().intValue() == telephoneAssignDetail.getFldFinishNumber().intValue()) {
                telephoneAssignDetail.setFldFinishStatus(TelephoneAssignFinishStatusEnum.DONE_FINISH.getCode());
            }
        } else {
            if (TelephoneTaskStatusEnum.WAITING_FINISH.getCode().equals(oldTelephoneTask.getFldTaskStatus())
                    && !TelephoneTaskStatusEnum.WAITING_FINISH.getCode().equals(oldTaskStatus)) {
                telephoneAssignDetail.setFldFinishNumber(telephoneAssignDetail.getFldFollowNumber() == null ? 0 : telephoneAssignDetail.getFldFollowNumber() + 1);
            }
        }

        telephoneAssignDetailDao.save(telephoneAssignDetail);

        //更新话单原始表中的最近拨打时间
        TelephoneImportDetail telephoneImportDetail = telephoneImportDetailDao.findOne(oldTelephoneTask.getFldCustomerId());
        TelephoneCustomer telephoneCustomer = telephoneCustomerDao.findOne(telephoneImportDetail.getFldTelephoneId());
        telephoneCustomer.setFldLatestCallDate(telephoneRecord.getFldCallDate());
        telephoneCustomer.setFldCallUserNo(SecurityUtil.getCurrentUserLoginName());
        telephoneCustomerDao.save(telephoneCustomer);
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
        tmpTelephoneCustomer.setFldFinancialUserNo(telephoneCustomer.getFldFinancialUserNo());
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
