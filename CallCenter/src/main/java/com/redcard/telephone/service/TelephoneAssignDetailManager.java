package com.redcard.telephone.service;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.common.TelephoneAssignFinishStatusEnum;
import com.redcard.telephone.common.TelephoneTaskStatusEnum;
import com.redcard.telephone.common.TelephoneTaskTypeEnum;
import com.redcard.telephone.dao.TelephoneAssignDetailDao;
import com.redcard.telephone.dao.TelephoneCustomerDao;
import com.redcard.telephone.dao.TelephoneImportDetailDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneImportDetail;
import com.redcard.telephone.entity.TelephoneTask;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class TelephoneAssignDetailManager extends GenericPageHQLQuery<TelephoneAssignDetail> {
    @Autowired
    private TelephoneAssignDetailDao telephoneAssignDetailDao;
    @Autowired
    private TelephoneTaskDao telephoneTaskDao;
    @Autowired
    private TelephoneImportDetailDao telephoneImportDetailDao;

    public Page<TelephoneAssignDetail> findDetail(GridPageRequest page, String where) {
        return (Page<TelephoneAssignDetail>) super.findAll(where, page);
    }

    public List<TelephoneTask> listTaskByAssignDetailId(String fldAssignDetailId) {
        return telephoneTaskDao.listByAssignDetailId(fldAssignDetailId);
    }

    @Transactional(readOnly = false)
    public void newTask(String customerId, String date, String comment) throws Exception {
        try {
            Date taskDate = org.apache.commons.lang3.time.DateUtils.parseDate(date, "yyyy-MM-dd");
            List<TelephoneAssignDetail> telephoneAssignDetailList = telephoneAssignDetailDao.findByCallUserNoAndDate(SecurityUtil.getCurrentUserLoginName(),
                    taskDate, TelephoneTaskTypeEnum.RESERVE.getCode());

            TelephoneAssignDetail telephoneAssignDetail = new TelephoneAssignDetail();

            if (telephoneAssignDetailList != null && telephoneAssignDetailList.size() > 0) {
                telephoneAssignDetail = telephoneAssignDetailList.get(0);
                telephoneAssignDetail.setFldTaskNumber(telephoneAssignDetail.getFldTaskNumber() + 1);
                telephoneAssignDetail.setFldFinishStatus(TelephoneTaskStatusEnum.NOT_FINISH.getCode());
                telephoneAssignDetail.setFldOperateDate(new Date());
                telephoneAssignDetail.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            } else {
                telephoneAssignDetail.setFldId(EntityUtil.getId());
                telephoneAssignDetail.setFldOperateDate(new Date());
                telephoneAssignDetail.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneAssignDetail.setFldCreateDate(new Date());
                telephoneAssignDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneAssignDetail.setFldCallUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneAssignDetail.setFldFinishStatus(0);
                telephoneAssignDetail.setFldAssignDate(new Date());
                telephoneAssignDetail.setFldAssignUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneAssignDetail.setFldExchangeNumber(0);
                telephoneAssignDetail.setFldFinishNumber(0);
                telephoneAssignDetail.setFldTaskDate(taskDate);
                telephoneAssignDetail.setFldTaskNumber(1);
                telephoneAssignDetail.setFldFollowNumber(0);
                telephoneAssignDetail.setFldTaskType(TelephoneTaskTypeEnum.RESERVE.getCode());
            }

            TelephoneImportDetail telephoneImportDetail = telephoneImportDetailDao.findOne(customerId);

            TelephoneTask telephoneTask = new TelephoneTask();
            telephoneTask.setFldCustomerId(customerId);
            telephoneTask.setFldAssignDetailId(telephoneAssignDetail.getFldId());
            telephoneTask.setFldCallUserNo(SecurityUtil.getCurrentUserLoginName());
            if (telephoneImportDetail != null) {
                telephoneTask.setFldCustomerName(telephoneImportDetail.getFldCustomerName());
            }
            telephoneTask.setFldAssignDate(new Date());
            telephoneTask.setFldTaskType(TelephoneTaskTypeEnum.RESERVE.getCode());
            telephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_UN);
            telephoneTask.setFldTaskStatus(Constant.TASK_FINISH_STATUS_UNFINISH);
            telephoneTask.setFldTaskDate(telephoneAssignDetail.getFldTaskDate());
            telephoneTask.setFldAuditStatus(Constant.TELEPHONE_TASK_AUDIT_STATUS_UN);
            telephoneTask.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTask.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            telephoneTask.setFldOperateDate(new Date());
            telephoneTask.setFldCreateDate(new Date());
            telephoneTask.setFldComment(comment);
            telephoneAssignDetailDao.save(telephoneAssignDetail);
            telephoneTaskDao.save(telephoneTask);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(readOnly = false)
    public void updateFinishStatusByDetailId(String fldAssignDetailId) {
        TelephoneAssignDetail telephoneAssignDetail = telephoneAssignDetailDao.findOne(fldAssignDetailId);

        //更新话务分配明细表的已拨打数
        Long countOfFinish = telephoneTaskDao.countByDateAndTaskStatus(telephoneAssignDetail.getFldId(), TelephoneTaskStatusEnum.DONE_FINISH.getCode(), DateUtils.truncate(new Date(), Calendar.DATE));
        if (countOfFinish != null) {
            telephoneAssignDetail.setFldFinishNumber(countOfFinish.intValue());

            if (countOfFinish.intValue() == telephoneAssignDetail.getFldTaskNumber().intValue()) {
                telephoneAssignDetail.setFldFinishStatus(TelephoneAssignFinishStatusEnum.DONE_FINISH.getCode());
            }
        }

        Long countOfFollow = telephoneTaskDao.countByDateAndTaskStatus(telephoneAssignDetail.getFldId(), TelephoneTaskStatusEnum.WAITING_FINISH.getCode(), DateUtils.truncate(new Date(), Calendar.DATE));

        if (countOfFollow != null) {
            telephoneAssignDetail.setFldFollowNumber(countOfFollow.intValue());
        }

        telephoneAssignDetailDao.save(telephoneAssignDetail);

    }
}
