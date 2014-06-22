package com.redcard.system.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.system.entity.Dictionary;
import com.redcard.system.service.DictionaryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据字典管理
 * User: Allen
 * Date: 10/28/12
 */
@Controller
@RequestMapping(value = "/system/dictionary")
public class DictionaryController {

    private static Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryManager dictionaryManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "system/dictionary/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Dictionary> list(String where, GridPageRequest pageRequest) {
        Page<Dictionary> dictionaries = dictionaryManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<Dictionary>(dictionaries);
        return dataResponse;
    }


    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Dictionary dictionary) {
        AsyncResponse result = new AsyncResponse(false, "保存字典成功");
        if (dictionaryManager.isExistDictionary(dictionary)) {
            return new AsyncResponse(true, "该字典已存在");
        }
        dictionaryManager.save(dictionary);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Dictionary dictionary) {
        AsyncResponse result = new AsyncResponse(false, "保存字典成功");
        if (dictionaryManager.isExistDictionary(dictionary)) {
            return new AsyncResponse(true, "该字典已存在");
        }
        dictionaryManager.save(dictionary);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除字典成功");
        dictionaryManager.delete(id);
        return result;
    }

    @RequestMapping(value = "isExistDictionary")
    @ResponseBody
    public boolean isExistDictionary(Dictionary dictionary) {
        return dictionaryManager.isExistDictionary(dictionary);
    }
}
