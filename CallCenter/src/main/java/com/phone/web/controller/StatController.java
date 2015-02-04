package com.phone.web.controller;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.phone.entity.Statrecord;
import com.phone.service.CalllogManager;
import com.phone.service.StatrecordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/phone/stat")
public class StatController {

    private static Logger logger = LoggerFactory.getLogger(StatController.class);

    @Autowired
    private StatrecordManager statrecordManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "phone/stat/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Statrecord> list() {
        return (new DataResponse<Statrecord>(statrecordManager.monitor()));
    }

}
