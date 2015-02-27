package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.telephone.common.TelephoneProductStatusEnum;
import com.redcard.telephone.entity.TelephoneProduct;
import com.redcard.telephone.service.TelephoneProductManager;
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
@RequestMapping(value = "/telephone/product")
public class TelephoneProductController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneProductController.class);

    @Autowired
    private TelephoneProductManager telephoneProductManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/product/list";
    }

    @RequestMapping(value = "queryInit")
    public String queryInit(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/product/query";
    }

    @RequestMapping(value = "query")
    @ResponseBody
    public DataResponse<TelephoneProduct> query(String where, GridPageRequest pageRequest) {

        FilterTranslator filterTranslator = telephoneProductManager.createFilter(where);
        filterTranslator.addFilterRule("fldStatus", TelephoneProductStatusEnum.ENABLE.getCode(), Constant.FILTER_OP_EQUAL, Constant.FILTER_TYPE_INT);

        Page<TelephoneProduct> dictionaries = telephoneProductManager.findAll(filterTranslator, pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneProduct>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneProduct> list(String where, GridPageRequest pageRequest) {
        Page<TelephoneProduct> dictionaries = telephoneProductManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneProduct>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "edit")
    public String edit(String menuNo, Integer id, Model model) {
        TelephoneProduct telephoneProduct = telephoneProductManager.find(id);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("telephoneProduct", telephoneProduct);
        return "telephone/product/edit";
    }

    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/product/add";
    }

    @RequestMapping(value = "find")
    @ResponseBody
    public AsyncResponse find(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            TelephoneProduct telephoneProduct = telephoneProductManager.find(id);
            result.getData().add(telephoneProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(TelephoneProduct telephoneProduct) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneProductManager.save(telephoneProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(TelephoneProduct telephoneProduct) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneProductManager.save(telephoneProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除成功");
        telephoneProductManager.delete(id);
        return result;
    }
}
