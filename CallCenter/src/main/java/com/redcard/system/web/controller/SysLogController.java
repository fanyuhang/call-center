package com.redcard.system.web.controller;

import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
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

/**
 * User: Allen
 * Date: 11/25/12
 */
@Controller
@RequestMapping(value = "/system/sysLog")
public class SysLogController {

    private static Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogManager sysLogManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        Date now = new Date();
        Date startDate = DateUtils.truncate(now, Calendar.MONTH);
        Date endDate = DateUtils.addDays(DateUtils.truncate(now, Calendar.DAY_OF_MONTH), 1);

        model.addAttribute("startDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
        model.addAttribute("endDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
        model.addAttribute("menuNo", menuNo);
        return "system/sysLog/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<SysLog> list(String where, GridPageRequest pageRequest) {
        Page<SysLog> logs = sysLogManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<SysLog>(logs);
        return dataResponse;
    }
}
