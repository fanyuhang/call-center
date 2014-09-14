package com.redcard.telephone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneAssignDetailDao;
import com.redcard.telephone.dao.TelephoneTaskDao;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.entity.TelephoneTask;

@Component
@Transactional(readOnly = true)
public class TelephoneAssignDetailManager extends GenericPageHQLQuery<TelephoneAssignDetail> {
	@Autowired
	private TelephoneAssignDetailDao telephoneAssignDetailDao;
	@Autowired
	private TelephoneTaskDao telephoneTaskDao;
	
	public Page<TelephoneAssignDetail> findDetail(GridPageRequest page, String where) {
        return (Page<TelephoneAssignDetail>) super.findAll(where, page);
    }
	
	public List<TelephoneTask> listTaskByAssignDetailId(String fldAssignDetailId) {
		return telephoneTaskDao.listByAssignDetailId(fldAssignDetailId);
	}
}
