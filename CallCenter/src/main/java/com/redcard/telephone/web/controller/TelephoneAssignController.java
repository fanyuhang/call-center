package com.redcard.telephone.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.telephone.entity.TelephoneAssign;
import com.redcard.telephone.entity.TelephoneAssignDetail;
import com.redcard.telephone.service.TelephoneAssignDetailManager;
import com.redcard.telephone.service.TelephoneAssignManager;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneImportDetailManager;
import com.redcard.telephone.service.TelephoneImportManager;
import com.redcard.telephone.service.TelephoneTaskManager;

@Controller
@RequestMapping(value = "/telephone/assign")
public class TelephoneAssignController {
	private static Logger log = LoggerFactory.getLogger(TelephoneAssignController.class);
	
	@Autowired
	private TelephoneAssignManager telephoneAssignManager;
	@Autowired
	private TelephoneImportDetailManager telephoneImportDetailManager;
	@Autowired
	private TelephoneTaskManager telephoneTaskManager;
	@Autowired
	private TelephoneAssignDetailManager telephoneAssignDetailManager;
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	@Autowired
	private TelephoneImportManager telephoneImportManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/assign/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneAssign> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<TelephoneAssign>(telephoneAssignManager.findAllTelephoneAssign(pageRequest, where)));
    }
	
	@RequestMapping(value = "assign")
    public String assign(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/assign/assign";
    }
	
	@RequestMapping(value = "countImport")
    @ResponseBody
    public AsyncResponse countImport() {
		AsyncResponse result = new AsyncResponse();
        Long num = telephoneImportDetailManager.countByAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_UNASSIGN);
        List<Long> data = new ArrayList<Long>();
        data.add(num);
        result.setData(data);
        return result;
    }
	
	@RequestMapping(value = "countTask")
    @ResponseBody
    public AsyncResponse countTask() {
		AsyncResponse result = new AsyncResponse();
        Long num = telephoneTaskManager.getCount();
        List<Long> data = new ArrayList<Long>();
        data.add(num);
        result.setData(data);
        return result;
    }
	
	@RequestMapping(value = "countImportCustomer")
    @ResponseBody
	public AsyncResponse countImportCustomer() {
		AsyncResponse result = new AsyncResponse();
		Integer count = telephoneCustomerManager.countCustomer();
		List<Integer> data = new ArrayList<Integer>();
        data.add(count);
        result.setData(data);
        return result;
	}
	
	@RequestMapping(value = "countImportCustomerById")
    @ResponseBody
	public AsyncResponse countImportCustomerById(String id) {
		AsyncResponse result = new AsyncResponse();
		Integer count = telephoneImportManager.countById(id);
		List<Integer> data = new ArrayList<Integer>();
        data.add(count);
        result.setData(data);
        return result;
	}
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(TelephoneAssign telephoneAssign) {
        AsyncResponse result = new AsyncResponse(false, "分配话务成功");
        telephoneAssignManager.saveAssign(telephoneAssign);
        return result;
    }
	
	@RequestMapping(value = "assignDetail")
    public String assignDetail(String id, String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("detailId", id);
        return "telephone/assign/detail";
    }
	
	@RequestMapping(value = "listDetail")
    @ResponseBody
    public DataResponse<TelephoneAssignDetail> listDetail(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldCallUserNo", "desc");
        return (new DataResponse<TelephoneAssignDetail>(telephoneAssignDetailManager.findDetail(pageRequest, where)));
    }
	
	@RequestMapping(value = "recover")
    @ResponseBody
    public AsyncResponse recover(TelephoneAssign telephoneAssign) {
        AsyncResponse result = new AsyncResponse(false, "话务回收成功");
        try {
			telephoneAssignManager.recover(telephoneAssign);
		} catch (Exception e) {
			result.setIsError(true);
			result.setMessage("话务回收失败");
			log.error(e.toString());
		}
        return result;
    }
}
