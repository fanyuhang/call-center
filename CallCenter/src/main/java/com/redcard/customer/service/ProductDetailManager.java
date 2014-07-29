package com.redcard.customer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.customer.dao.CustomerProductDetailDao;
import com.redcard.customer.entity.CustomerProductDetail;

@Component
@Transactional(readOnly = true)
public class ProductDetailManager extends GenericPageHQLQuery<CustomerProductDetail> {
	@Autowired
	private CustomerProductDetailDao customerProductDetailDao;
	
	public Page<CustomerProductDetail> findAllProductDetail(GridPageRequest page, String where) {
        return (Page<CustomerProductDetail>) super.findAll(where, page);
    }
	
	public CustomerProductDetail find(String fldId) {
        return customerProductDetailDao.findOne(fldId);
    }
	
	public CustomerProductDetail findByProductIdAndClearDays(String productId,Integer clearDays) {
		return customerProductDetailDao.findByProductIdAndDayUnit(productId, clearDays);
	}
	
	public Long countByCondition(Integer dayUnit,Integer clearDays,Double annualizedRate,String fldProductId) {
		return customerProductDetailDao.countByCondition(dayUnit,clearDays,annualizedRate,fldProductId);
	}
	
	public CustomerProductDetail findByCondition(Integer dayUnit,Integer clearDays,Double annualizedRate,String fldProductId) {
		return customerProductDetailDao.findByCondition(dayUnit,clearDays,annualizedRate,fldProductId);
	}
	
	public Long countByConditionById(Integer dayUnit,Integer clearDays,Double annualizedRate,String fldProductId,String fldId) {
		return customerProductDetailDao.countByConditionById(dayUnit,clearDays,annualizedRate,fldProductId,fldId);
	}
	
	@Transactional(readOnly = false)
    public void save(CustomerProductDetail customerProductDetail) {
		customerProductDetailDao.save(customerProductDetail);
    }
	
	@Transactional(readOnly = false)
    public void delete(String detId) {
		CustomerProductDetail customerProductDetail = customerProductDetailDao.findOne(detId);
		customerProductDetail.setFldStatus(Constant.PRODUCT_DETAIL_STATUS_DIABLED);
		customerProductDetail.setFldOperateDate(new Date());
		customerProductDetailDao.save(customerProductDetail);
    }
}
