package com.redcard.customer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.customer.entity.CustomerExchange;
import com.redcard.customer.service.ExchangeManager;

@Controller
@RequestMapping(value = "/customer/exchange")
public class ExchangeController {
	@Autowired
	private ExchangeManager exchangeManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/exchange/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CustomerExchange> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<CustomerExchange>(exchangeManager.findAllCustomerExchange(pageRequest, where)));
    }
}
