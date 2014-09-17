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
import com.common.core.util.EntityUtil;
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
	
	public Long countByFullName(String fullName) {
		return customerProductDao.countByFullName(fullName);
	}
	
	@Transactional(readOnly = false)
	public void saveProductInfo(CustomerProduct product) {
		customerProductDao.save(product);
	}
	
	@Transactional(readOnly = false)
    public void save(CustomerProduct product,List<CustomerProductDetail> productDetailList) {
		product.setFldId(EntityUtil.getId());
		product.setFldStatus(Constant.PRODUCT_STATUS_NORMAL);
		customerProductDao.save(product);
		
		if(null != productDetailList && productDetailList.size() > 0) {
			List<CustomerProductDetail> list = new ArrayList<CustomerProductDetail>();
			for(int i=0;i<productDetailList.size();i++) {
				CustomerProductDetail productDetail = productDetailList.get(i);
				productDetail.setFldId(EntityUtil.getId());
				productDetail.setFldProductId(product.getFldId());
				productDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
				productDetail.setFldCreateDate(new Date());
				productDetail.setFldOperateDate(new Date());
				productDetail.setFldStatus(Constant.PRODUCT_DETAIL_STATUS_NORMAL);
				list.add(productDetail);
			}
			customerProductDetailDao.save(list);
		}
    }
	
	@Transactional(readOnly = false)
    public void saveProduct(CustomerProduct product) {
		//如果产品的成立日期有修改，则修改对应产品明细的到期日期
		customerProductDao.save(product);
    }
	
	public CustomerProduct find(String fldId) {
        return customerProductDao.findOne(fldId);
    }
	
	public List<CustomerProduct> findByName(String name) {
		return customerProductDao.findByName(name);
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
