package com.redcard.customer.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerExchange;
import com.redcard.customer.entity.CustomerExchangeCustomer;
import com.redcard.customer.service.CustomerManager;
import com.redcard.customer.service.ExchangeManager;

@Controller
@RequestMapping(value = "/customer/exchange")
public class ExchangeController {
	@Autowired
	private ExchangeManager exchangeManager;
	@Autowired
	private CustomerManager customerManager;
	
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
	
	@RequestMapping(value = "exchange")
    public String exchange(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/exchange/exchange";
    }
	
	@RequestMapping(value = "getCount")
    @ResponseBody
    public AsyncResponse getCount(String serviceUserNo) {
        AsyncResponse result = new AsyncResponse(false,"");
        CustomerExchange customerExchange = exchangeManager.getCustomerAndContractCount(serviceUserNo);
        List<CustomerExchange> list = new ArrayList<CustomerExchange>();
        list.add(customerExchange);
        result.setData(list);
        return result;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(CustomerExchange customerExchange) {
        AsyncResponse result = new AsyncResponse(false, "保存交接客户信息成功");
        customerExchange.setFldId(EntityUtil.getId());
        customerExchange.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerExchange.setFldCreateDate(new Date());
        customerExchange.setFldOperateDate(new Date());
        customerExchange.setFldCustomerNum(customerExchange.getFldOldCustomerNum());
        customerExchange.setFldContractNum(customerExchange.getFldContractNum());
        exchangeManager.save(customerExchange);
        return result;
    }
	
	@RequestMapping(value = "detail")
    public String detail(String menuNo, String fldId, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("exchangeId", fldId);
        return "customer/exchange/detail";
    }
	
	@RequestMapping(value = "listCustomer")
    @ResponseBody
    public DataResponse<Customer> listCustomer(String exchangeId) {
		DataResponse<Customer> data = new DataResponse<Customer>();
		List<CustomerExchangeCustomer> list = exchangeManager.listCustomerByExchangeId(exchangeId);
		List<Customer> listCustomer = new ArrayList<Customer>();
		for(CustomerExchangeCustomer customerExchangeCustomer : list) {
			listCustomer.add(customerExchangeCustomer.getCustomer());
		}
		data.setRows(listCustomer);
		data.setTotal(listCustomer.size());
        return data;
    }
}
