package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.telephone.common.TelephoneTraceAssignStatusEnum;
import com.redcard.telephone.entity.TelephoneTrace;
import com.redcard.telephone.service.TelephoneTraceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据管理
 * User: Allen
 * Date: 10/28/12
 */
@Controller
@RequestMapping(value = "/telephone/traceAudit")
public class TelephoneTraceAuditController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceAuditController.class);

    @Autowired
    private TelephoneTraceManager telephoneTraceManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/traceAudit/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneTrace> list(String where, GridPageRequest pageRequest) {
        FilterTranslator filterTranslator = telephoneTraceManager.createFilter(where);
        filterTranslator.addFilterRule("fldAssignStatus", TelephoneTraceAssignStatusEnum.DONE_ASSIGN.getCode(), Constant.FILTER_OP_EQUAL,Constant.FILTER_TYPE_INT);
        Page<TelephoneTrace> dictionaries = telephoneTraceManager.findAll(filterTranslator,pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneTrace>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(String ids) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.pass(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "cancel")
    @ResponseBody
    public AsyncResponse update(String ids) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.nopass(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

}
