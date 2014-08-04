package com.redcard.customer.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.common.core.util.DateUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.entity.CustomerContract;
import com.redcard.customer.entity.Performance;
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
	
	@RequestMapping(value = "isExist")
    @ResponseBody
    public AsyncResponse isExist(String fldId) {
        AsyncResponse result = new AsyncResponse(true, "合同已存在");
        CustomerContract customerContract = contractManager.find(fldId);
        if (null == customerContract) {
            return new AsyncResponse(false, "合同不存在");
        }
        return result;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(CustomerContract customerContract) {
        AsyncResponse result = new AsyncResponse(false, "保存合同成功");
        customerContract.setFldCustomerUserNo(SecurityUtil.getCurrentUserLoginName());
        customerContract.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        customerContract.setFldCreateDate(new Date());
        customerContract.setFldOperateDate(new Date());
        customerContract.setFldStatus(Constant.CONTRACT_STATUS_NORMAL);
        contractManager.save(customerContract);
        return result;
    }
	
	@RequestMapping(value = "edit")
    public String edit(String menuNo, String fldId, Model model) {
		CustomerContract customerContract = contractManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customerContract", customerContract);
        return "customer/contract/edit";
    }
	
	@RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(CustomerContract customerContract) {
        AsyncResponse result = new AsyncResponse(false, "修改合同信息成功");
        customerContract.setFldOperateDate(new Date());
        
        CustomerContract oldCustomerContract = contractManager.find(customerContract.getFldId());
        customerContract.setFldStatus(oldCustomerContract.getFldStatus());
        customerContract.setFldCreateUserNo(oldCustomerContract.getFldCreateUserNo());
        customerContract.setFldCreateDate(oldCustomerContract.getFldCreateDate());
        contractManager.save(customerContract);
        return result;
    }
	
	@RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
		CustomerContract customerContract = contractManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("customerContract", customerContract);
        return "customer/contract/view";
    }
	
	@RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String fldId) {
        AsyncResponse result = new AsyncResponse(false, "删除合同成功");
        contractManager.delete(fldId);
        return result;
    }
	
	@RequestMapping(value = "initPerformance")
	public String initPerformance(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
        return "customer/contract/performance";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
    @ResponseBody
    public AsyncResponse export(String where, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出绩效列表成功");
        try {
        	List<CustomerContract> listContract = contractManager.findAllContract(null, where).getContent();
        	List<Performance> listPerformance = new ArrayList<Performance>();
        	if(null != listContract) {
        		for(CustomerContract customerContract : listContract) {
        			Performance performance = new Performance();
        			BeanUtils.copyProperties(performance, customerContract);
        			performance.setFldSignDate(DateUtil.getFormatDate(customerContract.getFldSignDate()));
        			performance.setFldFullName(customerContract.getCustomerProduct().getFldFullName());
        			performance.setFldClearDays(customerContract.getCustomerProductDetail().getFldClearDays());
                    performance.setFldAnnualizedRate(customerContract.getFldAnnualizedRate()+"%");
        			listPerformance.add(performance);
        		}
        	}
        	
            ExcelExportUtil<Performance> listToExcel = new ExcelExportUtil<Performance>(listPerformance);
            
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
