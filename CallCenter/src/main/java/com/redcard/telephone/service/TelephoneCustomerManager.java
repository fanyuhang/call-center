package com.redcard.telephone.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.telephone.dao.TelephoneCustomerDao;
import com.redcard.telephone.entity.TelephoneCustomer;

@Component
@Transactional(readOnly = true)
public class TelephoneCustomerManager extends GenericPageHQLQuery<TelephoneCustomer> {
	@Autowired
	private TelephoneCustomerDao telephoneCustomerDao;
	
	public Long countByPhoneOrMobile(String name,String phone,String mobile) {
		Long rtn = 0L;
		if(!StringUtils.isEmpty(phone))
			rtn += telephoneCustomerDao.countByPhone(name,phone);
		if(!StringUtils.isEmpty(mobile))
			rtn += telephoneCustomerDao.countByMobile(name, mobile);
		return rtn;
	}
	
	public void save(List<TelephoneCustomer> telephoneCustomerList) {
		telephoneCustomerDao.save(telephoneCustomerList);
	}
}
