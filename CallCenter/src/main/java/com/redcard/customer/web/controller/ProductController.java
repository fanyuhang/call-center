package com.redcard.customer.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.DateEditor;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerProduct;
import com.redcard.customer.service.ProductManager;

@Controller
@RequestMapping(value = "/customer/product")
public class ProductController {
	@Autowired
	private ProductManager productManager;
	
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
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(CustomerProduct product) {
        AsyncResponse result = new AsyncResponse(false, "保存客户成功");
        product.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        product.setFldCreateDate(new Date());
        product.setFldOperateDate(new Date());
        productManager.save(product);
        return result;
    }
}
