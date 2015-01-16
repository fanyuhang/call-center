package com.redcard.telephone.web.controller;

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
import com.common.security.util.SecurityUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phone.entity.Calllog;
import com.phone.service.CalllogManager;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneRecordManager;

@Controller
@RequestMapping(value = "/telephone/incoming")
public class IncomingController {
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	@Autowired
	private TelephoneRecordManager telephoneRecordManager;
	@Autowired
	private CalllogManager calllogManager;

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
		if(null == list || list.size() < 1) {
			List<TelephoneCustomer> teleList = telephoneCustomerManager.findByMobileOrPhone(num);
			if(null != teleList && teleList.size() > 0) {
				list = new ArrayList<Customer>();
				for(TelephoneCustomer telephoneCustomer : teleList) {
					Customer customer = new Customer();
					customer.setFldName(telephoneCustomer.getFldCustomerName());
					customer.setFldGender(telephoneCustomer.getFldGender());
					customer.setFldMobile(telephoneCustomer.getFldMobile());
					customer.setFldPhone(telephoneCustomer.getFldPhone());
					list.add(customer);
				}
			}
		}
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
		TelephoneCustomer telephoneCustomer = telephoneCustomerManager.findByPhoneOrMobile(customerName, phone, mobile);
		list.add(telephoneCustomer);
		result.setData(list);
        return result;
	}
	
	@RequestMapping(value = "saveCust")
    @ResponseBody
    public AsyncResponse saveCust(String customer,String telephoneCustomerId,String callId) {
        AsyncResponse result = new AsyncResponse(false,"保存客户信息成功");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
        Customer customerObject = gson.fromJson(customer, Customer.class);
        telephoneCustomerManager.updateCust(customerObject,telephoneCustomerId,callId);
        return result;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(TelephoneRecord telephoneRecord) {
        AsyncResponse result = new AsyncResponse();
        telephoneRecord.setFldOperateDate(new Date());
        telephoneRecord.setFldCreateDate(new Date());
        telephoneRecord.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        telephoneRecord.setFldCallType(Constant.TELEPHONE_CALL_TYPE_IN);
        telephoneRecordManager.save(telephoneRecord);
        return result;
    }
	
	@RequestMapping(value = "findCall")
    @ResponseBody
    public AsyncResponse findCall(String callId) {
        AsyncResponse result = new AsyncResponse();
        if(StringUtils.isBlank(callId)){
            return new AsyncResponse(true,"无拨打历史");
        }
        Calllog calllog = calllogManager.find(Integer.valueOf(callId));
        List<Calllog> list = new ArrayList<Calllog>();
        list.add(calllog);
        result.setData(list);
        return result;
    }
}
