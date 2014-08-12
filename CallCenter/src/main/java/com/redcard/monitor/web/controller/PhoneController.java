package com.redcard.monitor.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.phone.entity.ExtMonitor;
import com.phone.service.mybatis.PhoneMybatisManager;
import com.redcard.system.entity.SysLog;
import com.redcard.system.service.SysLogManager;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/monitor/phone")
public class PhoneController {

    private static Logger logger = LoggerFactory.getLogger(PhoneController.class);

    @Autowired
    private PhoneMybatisManager phoneMybatisManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "monitor/phone/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public AsyncResponse list(){
        AsyncResponse asyncResponse = new AsyncResponse(false,"成功");
        asyncResponse.setData(phoneMybatisManager.queryExtMonitor());
        return asyncResponse;
    }

}
