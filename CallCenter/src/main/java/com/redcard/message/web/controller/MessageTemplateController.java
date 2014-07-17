package com.redcard.message.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.customer.entity.CustomerExchange;

@Controller
@RequestMapping(value = "/message/template")
public class MessageTemplateController {

	private static Logger log = LoggerFactory
			.getLogger(MessageTemplateController.class);

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/template/list";
	}
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CustomerExchange> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<CustomerExchange>(exchangeManager.findAllCustomerExchange(pageRequest, where)));
    }
}