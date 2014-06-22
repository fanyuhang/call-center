package com.redcard.system.web.controller;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.system.entity.SysParameter;
import com.redcard.system.service.SysParameterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统参数管理
 * User: Allen
 * Date: 1/19/13
 */
@Controller
@RequestMapping(value = "/system/parameter")
public class SysParameterController {

    private static Logger logger = LoggerFactory.getLogger(SysParameterController.class);

    @Autowired
    private SysParameterManager sysParameterManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "system/parameter/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<SysParameter> list(String where, GridPageRequest pageRequest) {
        Page<SysParameter> dictionaries = sysParameterManager.findAll(sysParameterManager.createFilter(where).addDefaultFilterRule("display", Constant.SYSTEM_ENABLE, Constant.FILTER_OP_EQUAL), pageRequest);
        DataResponse dataResponse = new DataResponse<SysParameter>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(SysParameter sysParameter) {
        AsyncResponse result = new AsyncResponse(false, "系统参数成功");
        SysParameter entity = sysParameterManager.findParameter(sysParameter.getId());
        entity.setValue(sysParameter.getValue());
        entity.setComment(sysParameter.getComment());
        entity.setUnit(sysParameter.getUnit());
        sysParameterManager.save(entity);
        return result;
    }
}
