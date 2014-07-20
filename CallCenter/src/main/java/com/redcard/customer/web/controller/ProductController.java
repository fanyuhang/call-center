package com.redcard.customer.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.util.SecurityUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.redcard.customer.entity.CustomerProduct;
import com.redcard.customer.entity.CustomerProductDetail;
import com.redcard.customer.service.ProductDetailManager;
import com.redcard.customer.service.ProductManager;

@Controller
@RequestMapping(value = "/customer/product")
public class ProductController {
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductDetailManager productDetailManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/product/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CustomerProduct> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<CustomerProduct>(productManager.findAllProduct(pageRequest, where)));
    }
	
	@RequestMapping(value = "listDetail")
    @ResponseBody
    public DataResponse<CustomerProductDetail> listDetail(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        where = "{\"op\":\"and\",\"rules\":[{\"op\":\"equal\",\"field\":\"fldStatus\",\"value\":\"0\",\"type\":\"int\"}]}";
        return (new DataResponse<CustomerProductDetail>(productDetailManager.findAllProductDetail(pageRequest, where)));
    }
	
	@RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/product/add";
    }
	
	@RequestMapping(value = "isExist")
    @ResponseBody
    public AsyncResponse isExist(String fldId) {
        AsyncResponse result = new AsyncResponse(true, "产品已存在");
        Long num = productManager.countById(fldId);
        if ((num == null) || (num == 0)) {
            return new AsyncResponse(false, "产品不存在");
        }
        return result;
    }
	
	@RequestMapping(value = "isDetailExist")
    @ResponseBody
    public AsyncResponse isDetailExist(String fldId) {
        AsyncResponse result = new AsyncResponse(true, "产品明细已存在");
        Long num = productDetailManager.countById(fldId);
        if ((num == null) || (num == 0)) {
            return new AsyncResponse(false, "产品明细不存在");
        }
        return result;
    }
	
	@RequestMapping(value = "saveProductDetail")
    @ResponseBody
    public AsyncResponse saveProductDetail(String productDetail) {
        AsyncResponse result = new AsyncResponse(false, "保存产品明细成功");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
        
        CustomerProductDetail customerProductDetail = new CustomerProductDetail();
        customerProductDetail = gson.fromJson(productDetail, CustomerProductDetail.class);
        
        customerProductDetail.setFldOperateDate(new Date());
        customerProductDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerProductDetail.setFldCreateDate(new Date());
        customerProductDetail.setFldStatus(Constant.PRODUCT_DETAIL_STATUS_NORMAL);
        productDetailManager.save(customerProductDetail);
        
        return result;
    }
	
	@RequestMapping(value = "updateProductDetail")
    @ResponseBody
    public AsyncResponse updateProductDetail(String productDetail) {
        AsyncResponse result = new AsyncResponse(false, "保存产品明细成功");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
        
        CustomerProductDetail customerProductDetail = new CustomerProductDetail();
        customerProductDetail = gson.fromJson(productDetail, CustomerProductDetail.class);
        
        CustomerProductDetail oldCustomerProductDetail = productDetailManager.find(customerProductDetail.getFldId());
        
        customerProductDetail.setFldStatus(oldCustomerProductDetail.getFldStatus());
        customerProductDetail.setFldCreateDate(oldCustomerProductDetail.getFldCreateDate());
        customerProductDetail.setFldCreateUserNo(oldCustomerProductDetail.getFldCreateUserNo());
        customerProductDetail.setFldOperateDate(new Date());
        productDetailManager.save(customerProductDetail);
        
        return result;
    }
	
	@RequestMapping(value = "deleteProductDetail")
    @ResponseBody
    public AsyncResponse deleteProductDetail(String dtlId) {
        AsyncResponse result = new AsyncResponse(false, "删除产品明细成功");
        
        productDetailManager.delete(dtlId);
        
        return result;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(String product,String productDetail) {
        AsyncResponse result = new AsyncResponse(false, "保存产品成功");
        
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
		
		CustomerProduct customreProduct = new CustomerProduct();
		customreProduct = gson.fromJson(product, CustomerProduct.class);
		
		List<CustomerProductDetail> productDetailList = new ArrayList<CustomerProductDetail>();;
		if(!StringUtils.isEmpty(productDetail)) {
			productDetailList = gson.fromJson(productDetail, new TypeToken<List<CustomerProductDetail>>(){}.getType());
		}
		
		customreProduct.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		customreProduct.setFldCreateDate(new Date());
		customreProduct.setFldOperateDate(new Date());
        productManager.save(customreProduct,productDetailList);
        return result;
    }
	
	@RequestMapping(value = "edit")
    public String edit(String menuNo, String fldId, Model model) {
		CustomerProduct customerProduct = productManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customerProduct", customerProduct);
        return "customer/product/edit";
    }
	
	@RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(CustomerProduct customerProduct) {
        AsyncResponse result = new AsyncResponse(false, "修改产品信息成功");
        customerProduct.setFldOperateDate(new Date());
        
        CustomerProduct oldCustomerProduct = productManager.find(customerProduct.getFldId());
        customerProduct.setFldStatus(oldCustomerProduct.getFldStatus());
        customerProduct.setFldCreateUserNo(oldCustomerProduct.getFldCreateUserNo());
        customerProduct.setFldCreateDate(oldCustomerProduct.getFldCreateDate());
        
        productManager.saveProduct(customerProduct);
        return result;
    }
	
	@RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
		CustomerProduct customerProduct = productManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customerProduct", customerProduct);
        return "customer/product/view";
    }
	
	@RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String fldId) {
        AsyncResponse result = new AsyncResponse(false, "删除产品成功");
        productManager.delete(fldId);
        return result;
    }
	
	@RequestMapping(value = "findProductDetail")
    @ResponseBody
    public AsyncResponse findProductDetail(String fldId) {
		AsyncResponse response = new AsyncResponse();
		List<CustomerProductDetail> list = new ArrayList<CustomerProductDetail>();
		CustomerProductDetail customerProductDetail = productDetailManager.find(fldId);
		list.add(customerProductDetail);
		response.addData(list);
		response.setIsError(false);
        return response;
    }
}
