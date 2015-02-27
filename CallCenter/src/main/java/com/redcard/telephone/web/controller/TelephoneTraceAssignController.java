package com.redcard.telephone.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
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
@RequestMapping(value = "/telephone/traceAssign")
public class TelephoneTraceAssignController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceAssignController.class);

    @Autowired
    private TelephoneTraceManager telephoneTraceManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/traceAssign/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneTrace> list(String where, GridPageRequest pageRequest) {
        Page<TelephoneTrace> dictionaries = telephoneTraceManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneTrace>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(String ids, String financialUserNo) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.assign(ids, financialUserNo);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(String ids, String financialUserNo) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.update(ids, financialUserNo);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "reset")
    @ResponseBody
    public AsyncResponse reset(String ids) {
        AsyncResponse result = new AsyncResponse(false, "删除成功");
        try {
            telephoneTraceManager.reset(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }
}
