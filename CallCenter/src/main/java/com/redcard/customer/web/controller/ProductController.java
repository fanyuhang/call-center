package com.redcard.customer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
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
}
