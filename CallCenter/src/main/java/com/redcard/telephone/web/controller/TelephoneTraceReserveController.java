package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.telephone.common.TelephoneTraceAssignStatusEnum;
import com.redcard.telephone.common.TelephoneTraceAuditStatusEnum;
import com.redcard.telephone.common.TelephoneTraceFinishStatusEnum;
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
@RequestMapping(value = "/telephone/traceReserve")
public class TelephoneTraceReserveController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceReserveController.class);

    @Autowired
    private TelephoneTraceManager telephoneTraceManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/traceReserve/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneTrace> list(String where, GridPageRequest pageRequest) {
        FilterTranslator filterTranslator = telephoneTraceManager.createFilter(where);
        filterTranslator.addFilterRule("fldAuditStatus", TelephoneTraceAuditStatusEnum.DONE_AUDIT.getCode(), Constant.FILTER_OP_EQUAL,Constant.FILTER_TYPE_INT);
        Page<TelephoneTrace> dictionaries = telephoneTraceManager.findAll(filterTranslator,pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneTrace>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "updateResultStatus")
    @ResponseBody
    public AsyncResponse updateResultStatus(Integer status, String ids, String comment) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.updateResultStatus(status,ids,comment);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

}
