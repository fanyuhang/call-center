package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.util.SecurityUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.CustomerManager;
import com.redcard.telephone.common.TelephoneTaskStatusEnum;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.entity.TelephoneTask;
import com.redcard.telephone.service.TelephoneAssignDetailManager;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneRecordManager;
import com.redcard.telephone.service.TelephoneTaskManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private TelephoneAssignDetailManager telephoneAssignDetailManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/dial/list";
    }

    @RequestMapping(value = "listTask")
    @ResponseBody
    public DataResponse<TelephoneTask> list(GridPageRequest pageRequest) {
        return (new DataResponse<TelephoneTask>(telephoneTaskManager.listTask(SecurityUtil.getCurrentUserLoginName(),pageRequest)));
    }

    @RequestMapping(value = "check")
    @ResponseBody
    public AsyncResponse check() {
        return new AsyncResponse(false,"成功");
    }

    @RequestMapping(value = "findCustomer")
    @ResponseBody
    public AsyncResponse findCustomer(String customerName, String mobile, String phone) {
        AsyncResponse result = new AsyncResponse();
        try {
            TelephoneCustomer telephoneCustomer = telephoneCustomerManager.findByPhoneOrMobile(customerName, phone, mobile);
            List list = new ArrayList();
            list.add(telephoneCustomer);
            Customer customer = customerManager.findCustomerByMobileOrPhone(customerName, mobile, phone);
            list.add(customer);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "dialHis")
    @ResponseBody
    public DataResponse<TelephoneRecord> dialHis(String customerName, String phone, String mobile) {
        DataResponse<TelephoneRecord> response = new DataResponse<TelephoneRecord>();
        try {
            List<TelephoneRecord> list = telephoneRecordManager.findByNameAndPhone(customerName, phone, mobile);
            response.setRows(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(TelephoneRecord telephoneRecord) {
        AsyncResponse result = new AsyncResponse();
        try {
            String detailId = telephoneTaskManager.save(telephoneRecord);
            //@TODO 通过定时任务做
//            if (StringUtils.isNotBlank(detailId)) {
//                telephoneAssignDetailManager.updateFinishStatusByDetailId(detailId);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "saveCust")
    @ResponseBody
    public AsyncResponse saveCust(String taskId, String telephoneCustomer, String customer) {
        AsyncResponse result = new AsyncResponse(false, "保存客户信息成功");
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            TelephoneCustomer telephoneCustomerObject = gson.fromJson(telephoneCustomer, TelephoneCustomer.class);
            Customer customerObject = gson.fromJson(customer, Customer.class);
            telephoneTaskManager.updateCust(taskId, telephoneCustomerObject, customerObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "newTask")
    @ResponseBody
    public AsyncResponse newTask(String customerId, String date, String comment) {
        AsyncResponse result = new AsyncResponse();
        try {
            telephoneAssignDetailManager.newTask(customerId, date, comment);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理人员");
        }
        return result;
    }
}
