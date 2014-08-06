package com.redcard.telephone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.FilterGroupUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.dao.TelephoneAssignDetailDao;
import com.redcard.telephone.dao.TelephoneExchangeDao;
import com.redcard.telephone.dao.TelephoneExchangeDetailDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.entity.TelephoneExchange;
import com.redcard.telephone.entity.TelephoneExchangeDetail;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneExchangeManager extends GenericPageHQLQuery<TelephoneExchange> {
	@Autowired
	private TelephoneAssignDetailManager telephoneAssignDetailManager;
	@Autowired
	private TelephoneAssignDetailDao telephoneAssignDetailDao;
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	@Autowired
	private TelephoneExchangeDao telephoneExchangeDao;
	@Autowired
	private TelephoneExchangeDetailDao telephoneExchangeDetailDao;
	
	@Transactional(readOnly = false)
	public void saveExchange(TelephoneExchange telephoneExchange) {
		//1.更新话务分配明细的交接数
		int count = 0;
		GridPageRequest pageRequest = new GridPageRequest();
		String where = FilterGroupUtil.addRule("{}", "fldCallUserNo", telephoneExchange.getFldOldCallUserNo(), "string", "equal");
		where = FilterGroupUtil.addRule(where, "fldFinishStatus", Constant.TASK_FINISH_STATUS_UNFINISH.toString(), "int", "equal");
		List<TelephoneAssignDetail> list = telephoneAssignDetailManager.findDetail(pageRequest,where).getContent();
		List<TelephoneAssignDetail> detailList = new ArrayList<TelephoneAssignDetail>();
		List<TelephoneTask> taskList = new ArrayList<TelephoneTask>();
		for(TelephoneAssignDetail telephoneAssignDetail : list) {
			int detailNum = telephoneAssignDetail.getFldTaskNumber() - telephoneAssignDetail.getFldFinishNumber();
			if((count+detailNum) <= telephoneExchange.getFldExchangeNumber()) {
				count += detailNum;
				telephoneAssignDetail.setFldCallUserNo(telephoneExchange.getFldNewCallUserNo());
				detailList.add(telephoneAssignDetail);
				
				taskList.addAll(telephoneTaskDao.listByAssignDetailIdAndCallStatus(telephoneAssignDetail.getFldId(),Constant.TASK_CALL_STATUS_UN,telephoneExchange.getFldOldCallUserNo()));
			}
		}
		telephoneAssignDetailDao.save(detailList);
		
		//2.修改话务任务表的话务员
		for(TelephoneTask telephoneTask : taskList) {
			telephoneTask.setFldCallUserNo(telephoneExchange.getFldNewCallUserNo());
		}
		telephoneTaskDao.save(taskList);
		
		//3.记录话务交接
		TelephoneExchange tmpTelephoneExchange = new TelephoneExchange();
		tmpTelephoneExchange.setFldId(EntityUtil.getId());
		tmpTelephoneExchange.setFldExchangeDate(new Date());
		tmpTelephoneExchange.setFldExchangeNumber(detailList.size());
		tmpTelephoneExchange.setFldOldCallUserNo(telephoneExchange.getFldOldCallUserNo());
		tmpTelephoneExchange.setFldNewCallUserNo(telephoneExchange.getFldNewCallUserNo());
		tmpTelephoneExchange.setFldOldBeforeNumber(telephoneExchange.getFldOldBeforeNumber());
		tmpTelephoneExchange.setFldOldAfterNumber(telephoneExchange.getFldOldBeforeNumber() - tmpTelephoneExchange.getFldExchangeNumber());
		tmpTelephoneExchange.setFldNewBeforeNumber(telephoneExchange.getFldNewBeforeNumber());
		tmpTelephoneExchange.setFldNewAfterNumber(telephoneExchange.getFldNewBeforeNumber() + tmpTelephoneExchange.getFldExchangeNumber());
		tmpTelephoneExchange.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		tmpTelephoneExchange.setFldCreateDate(new Date());
		tmpTelephoneExchange.setFldOperateDate(new Date());
		
		telephoneExchangeDao.save(tmpTelephoneExchange);
		
		//4.话务交接明细
		List<TelephoneExchangeDetail> exchangeDetailList = new ArrayList<TelephoneExchangeDetail>();
		for(TelephoneTask telephoneTask : taskList) {
			TelephoneExchangeDetail telephoneExchangeDetail = new TelephoneExchangeDetail();
			telephoneExchangeDetail.setFldExchangeId(tmpTelephoneExchange.getFldId());
			telephoneExchangeDetail.setFldTaskId(telephoneTask.getFldId());
			telephoneExchangeDetail.setFldCreateDate(new Date());
			telephoneExchangeDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
			telephoneExchangeDetail.setFldOperateDate(new Date());
			exchangeDetailList.add(telephoneExchangeDetail);
		}
		telephoneExchangeDetailDao.save(exchangeDetailList);
	}
	
	@Transactional(readOnly = false)
	public void saveRecover(TelephoneExchange telephoneExchange) {
		//1.获取话务分配明细
		List<TelephoneAssignDetail> assignDetailList = telephoneAssignDetailDao.listByCallUser(telephoneExchange.getFldOldCallUserNo());
		
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
}
