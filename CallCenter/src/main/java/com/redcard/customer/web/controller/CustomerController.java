package com.redcard.customer.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;

@Controller
@RequestMapping(value = "/customer/customer")
public class CustomerController {
	private static Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerManager customerManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/customer/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Customer> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<Customer>(customerManager.findAllMerchant(pageRequest, where)));
    }
	
	@RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        //model.addAttribute("merchant", new Merchant());
        return "customer/customer/add";
    }
}
