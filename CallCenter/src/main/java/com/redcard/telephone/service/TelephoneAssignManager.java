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
import com.redcard.telephone.entity.TelephoneCustomer;
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
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	@Autowired
	private TelephoneImportManager telephoneImportManager;
	
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
		for(int i = 0;i < telephoneAssign.getFldDayNumber();i++) {
			TelephoneAssignDetail telephoneAssignDetail = new TelephoneAssignDetail();
			telephoneAssignDetail.setFldTaskDate(DateUtil.getDateAfterDays(telephoneAssign.getFldBeginDate(),i));
			telephoneAssignDetail.setFldFinishNumber(0);
			telephoneAssignDetail.setFldFollowNumber(0);
			telephoneAssignDetail.setFldExchangeNumber(0);
			telephoneAssignDetail.setFldFinishStatus(Constant.TASK_FINISH_STATUS_UNFINISH);
			telephoneAssignDetail.setFldCreateDate(new Date());
			telephoneAssignDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
			telephoneAssignDetail.setFldOperateDate(new Date());
			assignDetailList.add(telephoneAssignDetail);
			for(String callUserNo : callUserNos) {
				telephoneAssignDetail.setFldId(EntityUtil.getId());
				telephoneAssignDetail.setFldAssignId(telephoneAssign.getFldId());
				telephoneAssignDetail.setFldCallUserNo(callUserNo);
				telephoneAssignDetail.setFldAssignUserNo(SecurityUtil.getCurrentUserLoginName());
				telephoneAssignDetail.setFldAssignDate(new Date());
				telephoneAssignDetail.setFldTaskNumber(telephoneAssign.getFldAverageNumber());
				
				//3.话务任务表
				if(telephoneAssign.getFldSource() == Constant.TELEPHONE_SOURCE_IMPORT) {//来源：导入话务
					//从话务明细中获取指定数量的话单
					GridPageRequest page = new GridPageRequest();
					page.setPagesize(telephoneAssign.getFldAverageNumber());
					String where = "{\"op\":\"and\",\"rules\":[{\"op\":\"eq\",\"field\":\"fldAssignStatus\",\"value\":\"0\",\"type\":\"int\"},{\"op\":\"eq\",\"field\":\"fldImportId\",\"value\":\""+telephoneAssign.getImportId()+"\",\"type\":\"string\"}]};";
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
						telephoneTask.setFldTaskDate(new Date());
						telephoneTask.setFldAuditStatus(Constant.TELEPHONE_TASK_AUDIT_STATUS_UN);
						telephoneTask.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
						telephoneTask.setFldOperateDate(new Date());
						taskList.add(telephoneTask);
						
						telephoneImportDetail.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_ASSIGNED);
						newDetailList.add(telephoneImportDetail);
					}
					//修改话单的分配状态
					telephoneImportDetailDao.save(newDetailList);
					
					telephoneTaskDao.save(taskList);
				} else {//来源：已有话务
					//从原始话单中获取指定数量的话单
					GridPageRequest page = new GridPageRequest();
					page.setPagesize(telephoneAssign.getFldAverageNumber());
					List<TelephoneCustomer> list = telephoneCustomerManager.findAllTelephoneCustomer(page,null).getContent();
					List<TelephoneCustomer> newList = new ArrayList<TelephoneCustomer>();
					
					List<TelephoneTask> taskList = new ArrayList<TelephoneTask>();
					for(TelephoneCustomer telephoneCustomer : list) {
						TelephoneTask telephoneTask = new TelephoneTask();	
						telephoneTask.setFldAssignDetailId(telephoneAssignDetail.getFldId());
						telephoneTask.setFldCallUserNo(callUserNo);
						telephoneTask.setFldCustomerName(telephoneCustomer.getFldCustomerName());
						telephoneTask.setFldAssignDate(new Date());
						telephoneTask.setFldAssignDate(telephoneAssignDetail.getFldTaskDate());
						telephoneTask.setFldTaskType(Constant.TASK_TYPE_AUTO);
						telephoneTask.setFldCallStatus(Constant.TASK_CALL_STATUS_UN);
						telephoneTask.setFldTaskStatus(Constant.TASK_FINISH_STATUS_UNFINISH);
						telephoneTask.setFldTaskDate(new Date());
						telephoneTask.setFldAuditStatus(Constant.TELEPHONE_TASK_AUDIT_STATUS_UN);
						telephoneTask.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
						telephoneTask.setFldOperateDate(new Date());
						taskList.add(telephoneTask);
						
						telephoneCustomer.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_ASSIGNED);
						telephoneCustomer.setFldAssignDate(new Date());
						newList.add(telephoneCustomer);
					}
					
					//修改话单的分配状态
					telephoneCustomerManager.save(newList);
					
					telephoneTaskDao.save(taskList);
				}
				telephoneAssignDetailDao.save(assignDetailList);
			}
		}
		telephoneImportManager.updateAssignNumber(telephoneAssign.getFldAssignNumber(),telephoneAssign.getImportId());
	}
	
	@Transactional(readOnly = false)
	public void recover(TelephoneAssign telephoneAssign) {
		//1.获取话务分配明细
		List<TelephoneAssignDetail> assignDetailList = telephoneAssignDetailDao.listByAssignId(telephoneAssign.getFldId());
		
		for(TelephoneAssignDetail telephoneAssignDetail : assignDetailList) {
			//2.获取话务任务
			List<TelephoneTask> taskList = telephoneTaskDao.listByAssignDetailId(telephoneAssignDetail.getFldId());
			//3.未拨打的任务删除
			int count = 0;
			for(TelephoneTask telephoneTask : taskList) {
				if(telephoneTask.getFldCallStatus() == Constant.TASK_CALL_STATUS_UN) {
					telephoneTaskDao.delete(telephoneTask);
					count++;
				}
			}
			
			//4.话务明细中的话务数减少
			if(count > 0) {
				telephoneAssignDetail.setFldTaskNumber(telephoneAssignDetail.getFldTaskNumber()-count);
				telephoneAssignDetailDao.save(telephoneAssignDetail);
			}
		}
	}
	
	public TelephoneAssignDetail getCount(String fldCallUserNo) {
		TelephoneAssignDetail telephoneAssignDetail = new TelephoneAssignDetail();
		
		telephoneAssignDetail.setFldTaskNumber(telephoneAssignDetailDao.countTaskNumber(fldCallUserNo).intValue());
		telephoneAssignDetail.setFldFinishNumber(telephoneAssignDetailDao.countFinishNumber(fldCallUserNo).intValue());
		
		return telephoneAssignDetail;
	}
}
