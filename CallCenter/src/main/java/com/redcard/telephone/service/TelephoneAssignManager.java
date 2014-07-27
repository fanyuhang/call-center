package com.redcard.telephone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.DateUtil;
import com.common.core.util.EntityUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.dao.TelephoneAssignDao;
import com.redcard.telephone.dao.TelephoneAssignDetailDao;
import com.redcard.telephone.dao.TelephoneImportDetailDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneAssign;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.entity.TelephoneImportDetail;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneAssignManager extends GenericPageHQLQuery<TelephoneAssign> {
	@Autowired
	private TelephoneAssignDao telephoneAssignDao;
	@Autowired	
	private TelephoneAssignDetailDao telephoneAssignDetailDao;
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	@Autowired
	private TelephoneImportDetailManager telephoneImportDetailManager;
	@Autowired
	private TelephoneImportDetailDao telephoneImportDetailDao;
	
	public Page<TelephoneAssign> findAllTelephoneAssign(GridPageRequest page, String where) {
        return (Page<TelephoneAssign>) super.findAll(where, page);
    }
	
	@Transactional(readOnly = false)
	public void saveAssign(TelephoneAssign telephoneAssign) {
		//1.话务分配表
		telephoneAssign.setFldId(EntityUtil.getId());
		telephoneAssign.setFldCreateDate(new Date());
		telephoneAssign.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		telephoneAssign.setFldOperateDate(new Date());
		telephoneAssignDao.save(telephoneAssign);
		
		//2.话务分配明细表
		List<TelephoneAssignDetail> assignDetailList = new ArrayList<TelephoneAssignDetail>();
		String[] callUserNos = telephoneAssign.getFldCallUserNo().split(";");
		for(String callUserNo : callUserNos) {
			TelephoneAssignDetail telephoneAssignDetail = new TelephoneAssignDetail();
			telephoneAssignDetail.setFldAssignId(telephoneAssign.getFldId());
			telephoneAssignDetail.setFldCallUserNo(callUserNo);
			telephoneAssignDetail.setFldAssignUserNo(SecurityUtil.getCurrentUserLoginName());
			telephoneAssignDetail.setFldAssignDate(new Date());
			telephoneAssignDetail.setFldTaskNumber(telephoneAssign.getFldAverageNumber());
			for(int i = 0;i < telephoneAssign.getFldDayNumber();i++) {
				telephoneAssignDetail.setFldId(EntityUtil.getId());
				telephoneAssignDetail.setFldTaskDate(DateUtil.getDateAfterDays(telephoneAssign.getFldBeginDate(),i));
				telephoneAssignDetail.setFldFinishNumber(0);
				telephoneAssignDetail.setFldFollowNumber(0);
				telephoneAssignDetail.setFldExchangeNumber(0);
				telephoneAssignDetail.setFldFinishStatus(Constant.TASK_FINISH_STATUS_UNFINISH);
				telephoneAssignDetail.setFldCreateDate(new Date());
				telephoneAssignDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
				telephoneAssignDetail.setFldOperateDate(new Date());
				assignDetailList.add(telephoneAssignDetail);
				
				//3.话务任务表
				//获取指定数量的话单
				GridPageRequest page = new GridPageRequest();
				page.setPagesize(telephoneAssign.getFldAverageNumber());
				String where = "{\"op\":\"and\",\"rules\":[{\"op\":\"eq\",\"field\":\"fldAssignStatus\",\"value\":\"0\",\"type\":\"int\"}]};";
				List<TelephoneImportDetail> detailList = telephoneImportDetailManager.findAllDetail(page, where).getContent();
				List<TelephoneImportDetail> newDetailList = new ArrayList<TelephoneImportDetail>();
				
				List<TelephoneTask> taskList = new ArrayList<TelephoneTask>();
				for(TelephoneImportDetail telephoneImportDetail : detailList) {
					TelephoneTask telephoneTask = new TelephoneTask();	
					telephoneTask.setFldCustomerId(telephoneImportDetail.getFldId());
					telephoneTask.setFldAssignDetailId(telephoneAssignDetail.getFldId());
					telephoneTask.setFldCallUserNo(callUserNo);
					telephoneTask.setFldCustomerName(telephoneImportDetail.getFldCustomerName());
					telephoneTask.setFldAssignDate(new Date());
					telephoneTask.setFldAssignDate(telephoneAssignDetail.getFldTaskDate());
					telephoneTask.setFldTaskType(Constant.TASK_TYPE_AUTO);
					telephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_UN);
					telephoneTask.setFldTaskStatus(Constant.TASK_FINISH_STATUS_UNFINISH);
					telephoneTask.setFldCallDate(new Date());
					telephoneTask.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
					telephoneTask.setFldOperateDate(new Date());
					taskList.add(telephoneTask);
					
					telephoneImportDetail.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_ASSIGNED);
					newDetailList.add(telephoneImportDetail);
				}
				//修改话单的分配状态
				telephoneImportDetailDao.save(newDetailList);
				telephoneTaskDao.save(taskList);
			}
			
		}
		telephoneAssignDetailDao.save(assignDetailList);
	}
}
