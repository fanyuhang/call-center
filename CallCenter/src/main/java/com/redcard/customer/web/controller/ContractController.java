package com.redcard.customer.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
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
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(CustomerContract customerContract) {
        AsyncResponse result = new AsyncResponse(false, "保存合同成功");
        customerContract.setFldId(EntityUtil.getId());
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
	
}
