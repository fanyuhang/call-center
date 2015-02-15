package com.redcard.system.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.redcard.system.common.NoticeStatusEnum;
import com.redcard.system.entity.Notice;
import com.redcard.system.service.NoticeManager;
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
@RequestMapping(value = "/system/notice")
public class NoticeController {

    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeManager noticeManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "system/notice/list";
    }

    @RequestMapping(value = "monitor")
    @ResponseBody
    public DataResponse<Notice> monitor(String where, GridPageRequest pageRequest) {

        FilterTranslator filterTranslator = noticeManager.createFilter(where);
        filterTranslator.addFilterRule("fldStatus", NoticeStatusEnum.ENABLE.getCode(), Constant.FILTER_OP_EQUAL, Constant.FILTER_TYPE_INT);

        Page<Notice> dictionaries = noticeManager.findAll(filterTranslator, pageRequest);
        DataResponse dataResponse = new DataResponse<Notice>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Notice> list(String where, GridPageRequest pageRequest) {
        Page<Notice> dictionaries = noticeManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<Notice>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "edit")
    public String edit(String menuNo, Integer id, Model model) {
        Notice notice = noticeManager.find(id);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("notice", notice);
        return "system/notice/edit";
    }

    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("sysjob", new Notice());
        return "system/notice/add";
    }

    @RequestMapping(value = "find")
    @ResponseBody
    public AsyncResponse find(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            Notice notice = noticeManager.find(id);
            result.getData().add(notice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Notice notice) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            noticeManager.save(notice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Notice notice) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            noticeManager.save(notice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除成功");
        noticeManager.delete(id);
        return result;
    }
}
