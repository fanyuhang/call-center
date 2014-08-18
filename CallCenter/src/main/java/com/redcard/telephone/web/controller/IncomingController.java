package com.redcard.telephone.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;

@Controller
@RequestMapping(value = "/telephone/incoming")
public class IncomingController {
	@Autowired
	private CustomerManager customerManager;

	@RequestMapping(value = "init")
    public String init(String phone,Integer callId, String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("phone", phone);
        model.addAttribute("callId", callId);
        return "telephone/incoming/list";
    }
	
	@RequestMapping(value = "listCustomer")
    @ResponseBody
	public DataResponse<Customer> dialHis(String num) {
		List<Customer> list = customerManager.findByMobileOrPhone(num);
		DataResponse<Customer> response = new DataResponse<Customer>();
		response.setRows(list);
        return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "findCustomer")
    @ResponseBody
	public AsyncResponse findCustomer(String customerName,String mobile,String phone) {
		AsyncResponse result = new AsyncResponse();
		Customer customer = customerManager.findCustomerByMobileOrPhone(customerName,mobile,phone);
		List list = new ArrayList();
		list.add(customer);
		result.setData(list);
        return result;
	}
}
