package com.redcard.telephone.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.telephone.entity.TelephoneAssign;
import com.redcard.telephone.service.TelephoneAssignManager;

@Controller
@RequestMapping(value = "/telephone/assign")
public class TelephoneAssignController {
	@Autowired
	private TelephoneAssignManager telephoneAssignManager;
	
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
}
