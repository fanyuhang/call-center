package com.redcard.customer.service;

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
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.dao.CustomerProductDao;
import com.redcard.customer.dao.CustomerProductDetailDao;
import com.redcard.customer.entity.CustomerProduct;
import com.redcard.customer.entity.CustomerProductDetail;

@Component
@Transactional(readOnly = true)
public class ProductManager extends GenericPageHQLQuery<CustomerProduct> {
	@Autowired
	private CustomerProductDao customerProductDao;
	@Autowired
	private CustomerProductDetailDao customerProductDetailDao;
	
	public Page<CustomerProduct> findAllProduct(GridPageRequest page, String where) {
        return (Page<CustomerProduct>) super.findAll(where, page);
    }
	
	public Long countById(String id) {
		return customerProductDao.countById(id);
	}
	
	@Transactional(readOnly = false)
    public void save(CustomerProduct product,List<CustomerProductDetail> productDetailList) {
		product.setFldStatus(Constant.PRODUCT_STATUS_NORMAL);
		customerProductDao.save(product);
		
		if(null != productDetailList && productDetailList.size() > 0) {
			List<CustomerProductDetail> list = new ArrayList<CustomerProductDetail>();
			for(int i=0;i<productDetailList.size();i++) {
				CustomerProductDetail productDetail = productDetailList.get(i);
				productDetail.setFldProductId(product.getFldId());
				productDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
				productDetail.setFldCreateDate(new Date());
				productDetail.setFldOperateDate(new Date());
				//计算到期日期
				if(productDetail.getFldDayUnit() == Constant.DAY_UNIT_DAY) {
					productDetail.setFldDueDate(DateUtil.getDateAfterDays(product.getFldEstablishDate(), productDetail.getFldClearDays()));
				} else if(productDetail.getFldDayUnit() == Constant.DAY_UNIT_MONTH) {
					productDetail.setFldDueDate(DateUtil.getDateAfterMonths(product.getFldEstablishDate(), productDetail.getFldClearDays()));
				}
				productDetail.setFldStatus(Constant.PRODUCT_DETAIL_STATUS_NORMAL);
				list.add(productDetail);
			}
			customerProductDetailDao.save(list);
		}
    }
	
	@Transactional(readOnly = false)
    public void saveProduct(CustomerProduct product) {
		//如果产品的成立日期有修改，则修改对应产品明细的到期日期
		CustomerProduct oldCustomerProduct = find(product.getFldId());
		if(product.getFldEstablishDate() != oldCustomerProduct.getFldEstablishDate()) {
			List<CustomerProductDetail> list = customerProductDetailDao.findByProductId(product.getFldId());
			
			List<CustomerProductDetail> tmpList = new ArrayList<CustomerProductDetail>();
			for(CustomerProductDetail customerProductDetail : list) {
				//计算到期日期
				if(customerProductDetail.getFldDayUnit() == Constant.DAY_UNIT_DAY) {
					customerProductDetail.setFldDueDate(DateUtil.getDateAfterDays(product.getFldEstablishDate(), customerProductDetail.getFldClearDays()));
				} else if(customerProductDetail.getFldDayUnit() == Constant.DAY_UNIT_MONTH) {
					customerProductDetail.setFldDueDate(DateUtil.getDateAfterMonths(product.getFldEstablishDate(), customerProductDetail.getFldClearDays()));
				}
				tmpList.add(customerProductDetail);
			}
			customerProductDetailDao.save(tmpList);
		}
		
		customerProductDao.save(product);
    }
	
	public CustomerProduct find(String fldId) {
        return customerProductDao.findOne(fldId);
    }
	
	@Transactional(readOnly = false)
    public void delete(String fldId) {
		CustomerProduct product = customerProductDao.findOne(fldId);
		product.setFldStatus(Constant.PRODUCT_STATUS_DIABLED);
		product.setFldOperateDate(new Date());
		customerProductDao.save(product);
		
		customerProductDetailDao.updateProductDetailByProductId(Constant.PRODUCT_DETAIL_STATUS_DIABLED, fldId);
    }
}
