package com.redcard.customer.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.excel.ExcelExportUtil;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.FilterGroupUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerContract;
import com.redcard.customer.service.ContractManager;
import com.redcard.customer.service.CustomerManager;

@Controller
@RequestMapping(value = "/customer/customer")
public class CustomerController {
	private static Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ContractManager contractManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/customer/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Customer> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<Customer>(customerManager.findAllCustomer(pageRequest, where)));
    }
	
	@RequestMapping(value = "listByStatus")
    @ResponseBody
    public DataResponse<Customer> listByStatus(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        if(StringUtils.isEmpty(where)) {
        	where = "{\"op\":\"and\",\"rules\":[{\"op\":\"equal\",\"field\":\"fldStatus\",\"value\":\"0\",\"type\":\"int\"}]}";
        } else {
        	where = FilterGroupUtil.addRule(where, "fldStatus", "0", "int", "equal");
        }
        return (new DataResponse<Customer>(customerManager.findAllCustomer(pageRequest, where)));
    }
	
	@RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "customer/customer/add";
    }
	
	@RequestMapping(value = "isExist")
    @ResponseBody
    public AsyncResponse isExist(String fldName,String phone,String mobile) {
        AsyncResponse result = new AsyncResponse(true, "客户已存在");
        Long num = customerManager.countByPhoneOrMobile(fldName,phone,mobile);
        if ((num == null) || (num == 0)) {
            return new AsyncResponse(false, "客户不存在");
        }
        return result;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Customer customer) {
        AsyncResponse result = new AsyncResponse(false, "保存客户成功");
        customer.setFldId(EntityUtil.getId());
        customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customer.setFldCreateDate(new Date());
        customer.setFldOperateDate(new Date());
        customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
        customer.setFldCardTotalMoney((double) 0);
        customerManager.save(customer);
        return result;
    }
	
	@RequestMapping(value = "edit")
    public String edit(String menuNo, String fldId, Model model) {
        Customer customer = customerManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customer", customer);
        return "customer/customer/edit";
    }
	
	@RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Customer customer) {
        AsyncResponse result = new AsyncResponse(false, "修改客户信息成功");
        customer.setFldOperateDate(new Date());
        
        Customer oldCustomer = customerManager.find(customer.getFldId());
        customer.setFldStatus(oldCustomer.getFldStatus());
        customer.setFldCreateUserNo(oldCustomer.getFldCreateUserNo());
        customer.setFldCreateDate(oldCustomer.getFldCreateDate());
        customer.setFldCardStatus(oldCustomer.getFldCardStatus());
        customer.setFldCardTotalMoney(oldCustomer.getFldCardTotalMoney());
        
        customerManager.save(customer);
        return result;
    }
	
	@RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String fldId) {
        AsyncResponse result = new AsyncResponse(false, "删除客户成功");
        customerManager.delete(fldId);
        return result;
    }
	
	@RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
        Customer customer = customerManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customer", customer);
        return "customer/customer/view";
    }
	
	@RequestMapping(value = "addContract")
    public String addContract(String menuNo, String fldId,String custName,Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("custId",fldId);
        model.addAttribute("custName",custName);
        
        Customer customer = customerManager.find(fldId);
        model.addAttribute("customer", customer);
        return "customer/customer/addContract";
    }
	
	@RequestMapping(value = "exchange")
    public String exchange(String menuNo, String fldId, Model model) {
        Customer customer = customerManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customer", customer);
        return "customer/customer/exchange";
    }
    
    @RequestMapping(value = "updateFinancialUser")
    @ResponseBody
    public AsyncResponse updateFinancialUser(Customer customer) {
        AsyncResponse result = new AsyncResponse(false, "客户交接成功");
        customerManager.updateFinancialUser(customer);
        return result;
    }
    
    @RequestMapping(value = "viewContract")
    public String viewContract(String menuNo, String fldId, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customerId", fldId);
        return "customer/customer/viewContract";
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "exportContract")
    @ResponseBody
    public AsyncResponse exportContract(String where, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出客户合同列表成功");
        try {
        	ExcelExportUtil<CustomerContract> listToExcel = new ExcelExportUtil<CustomerContract>(contractManager.findAllContract(null, where).getContent());
            String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
            String reportPath = request.getSession().getServletContext().getRealPath("/") + "/export/";
            String fileName = SecurityUtil.getCurrentUserId() + "_" + date + ".xls";
            File pathFile = new File(reportPath);
            if (!pathFile.exists())
                pathFile.mkdirs();
            result.getData().add(listToExcel.generate(reportPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
    @ResponseBody
    public AsyncResponse export(String where, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出客户列表成功");
        try {
            ExcelExportUtil<Customer> listToExcel = new ExcelExportUtil<Customer>(customerManager.findAllCustomer(null, where).getContent());
            String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
            String reportPath = request.getSession().getServletContext().getRealPath("/") + "/export/";
            String fileName = SecurityUtil.getCurrentUserId() + "_" + date + ".xls";
            File pathFile = new File(reportPath);
            if (!pathFile.exists())
                pathFile.mkdirs();
            result.getData().add(listToExcel.generate(reportPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }
}
