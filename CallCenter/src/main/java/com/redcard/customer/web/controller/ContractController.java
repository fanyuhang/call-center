package com.redcard.customer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.customer.entity.CustomerContract;
import com.redcard.customer.service.ContractManager;

@Controller
@RequestMapping(value = "/customer/contract")
public class ContractController {
	@Autowired
	private ContractManager contractManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/contract/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CustomerContract> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<CustomerContract>(contractManager.findAllContract(pageRequest, where)));
    }
	
	@RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/contract/add";
    }
}
