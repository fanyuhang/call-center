package com.redcard.telephone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneAssignDao;
import com.redcard.telephone.entity.TelephoneAssign;

@Component
@Transactional(readOnly = true)
public class TelephoneAssignManager extends GenericPageHQLQuery<TelephoneAssign> {
	@Autowired
	private TelephoneAssignDao telephoneAssignDao;
	
	public Page<TelephoneAssign> findAllTelephoneAssign(GridPageRequest page, String where) {
        return (Page<TelephoneAssign>) super.findAll(where, page);
    }
}
