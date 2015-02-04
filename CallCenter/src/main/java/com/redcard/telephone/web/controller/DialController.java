package com.redcard.telephone.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.common.TelephoneTaskStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.entity.TelephoneTask;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneRecordManager;
import com.redcard.telephone.service.TelephoneTaskManager;

@Controller
@RequestMapping(value = "/telephone/dial")
public class DialController {
	private static Logger log = LoggerFactory.getLogger(DialController.class);
	@Autowired
	private TelephoneTaskManager telephoneTaskManager;
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private TelephoneRecordManager telephoneRecordManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/dial/list";
    }
	
	@RequestMapping(value = "listTask")
    @ResponseBody
    public DataResponse<TelephoneTask> list(GridPageRequest pageRequest, String where) {
        FilterTranslator translator = telephoneTaskManager.createFilter(where);
        translator.addFilterRule("fldTaskStatus", TelephoneTaskStatusEnum.DONE_FINISH.getCode(), Constant.FILTER_OP_LESS, Constant.FILTER_TYPE_INT);
        translator.addFilterRule("fldCallUserNo", SecurityUtil.getCurrentUserLoginName(),Constant.FILTER_OP_EQUAL,Constant.FILTER_TYPE_STRING);
        return (new DataResponse<TelephoneTask>(telephoneTaskManager.findAll(translator,pageRequest)));
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "findCustomer")
    @ResponseBody
	public AsyncResponse findCustomer(String customerName,String mobile,String phone) {
		AsyncResponse result = new AsyncResponse();
		TelephoneCustomer telephoneCustomer = telephoneCustomerManager.findByPhoneOrMobile(customerName, phone, mobile);
		List list = new ArrayList();
		list.add(telephoneCustomer);
		Customer customer = customerManager.findCustomerByMobileOrPhone(customerName, mobile, phone);
		list.add(customer);
		result.setData(list);
        return result;
	}
	
	@RequestMapping(value = "dialHis")
    @ResponseBody
	public DataResponse<TelephoneRecord> dialHis(String customerName,String phone,String mobile) {
		List<TelephoneRecord> list = telephoneRecordManager.findByNameAndPhone(customerName, phone,mobile);
		DataResponse<TelephoneRecord> response = new DataResponse<TelephoneRecord>();
		response.setRows(list);
        return response;
	}
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(TelephoneRecord telephoneRecord) {
        AsyncResponse result = new AsyncResponse();
        telephoneTaskManager.save(telephoneRecord);
        return result;
    }
	
	@RequestMapping(value = "saveCust")
    @ResponseBody
    public AsyncResponse saveCust(String taskId,String telephoneCustomer,String customer) {
        AsyncResponse result = new AsyncResponse(false,"保存客户信息成功");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
        TelephoneCustomer telephoneCustomerObject = gson.fromJson(telephoneCustomer, TelephoneCustomer.class);
        Customer customerObject = gson.fromJson(customer, Customer.class);
        telephoneTaskManager.updateCust(taskId, telephoneCustomerObject, customerObject);
        return result;
    }
}
