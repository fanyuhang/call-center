package com.redcard.telephone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.FilterGroupUtil;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneAssignDetailDao;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.entity.TelephoneExchange;

@Component
@Transactional(readOnly = true)
public class TelephoneExchangeManager extends GenericPageHQLQuery<TelephoneExchange> {
	@Autowired
	private TelephoneAssignDetailManager telephoneAssignDetailManager;
	@Autowired
	private TelephoneAssignDetailDao telephoneAssignDetailDao;
	
	@Transactional(readOnly = false)
	public void saveExchange(TelephoneExchange telephoneExchange) {
		//1.更新话务分配明细的话务员
		int count = 0;
		GridPageRequest pageRequest = new GridPageRequest();
		String where = FilterGroupUtil.addRule("", "fldCallUserNo", telephoneExchange.getFldOldCallUserNo(), "string", "equal");
		where = FilterGroupUtil.addRule(where, "fldFinishStatus", Constant.TASK_FINISH_STATUS_UNFINISH.toString(), "int", "equal");
		List<TelephoneAssignDetail> list = telephoneAssignDetailManager.findDetail(pageRequest,where).getContent();
		for(TelephoneAssignDetail telephoneAssignDetail : list) {
			count += telephoneAssignDetail.getFldTaskNumber() - telephoneAssignDetail.getFldFinishNumber();
			telephoneAssignDetail.setFldCallUserNo(telephoneExchange.getFldNewCallUserNo());
		}
		telephoneAssignDetailDao.save(list);
	}
}
