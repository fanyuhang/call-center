package com.common.security.web.controller;

import com.common.AppContext;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.security.entity.DataPrivilege;
import com.common.security.entity.Resource;
import com.common.security.service.DataPrivilegeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据权限操作处理类
 * User: Allen
 * Date: 9/13/12
 */
@Controller
@RequestMapping(value = "/security/dataPrivilege")
public class DataPrivilegeController {

    private static Logger logger = LoggerFactory.getLogger(DataPrivilegeController.class);

    @Autowired
    private DataPrivilegeManager dataPrivilegeManager;

    @Autowired
    private AppContext appContext;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "security/dataPrivilege/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<DataPrivilege> list() {
        List<DataPrivilege> privileges = dataPrivilegeManager.findAllDataPrivilege();
        DataResponse dataResponse = new DataResponse<DataPrivilege>(privileges);
        return dataResponse;
    }


    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        DataPrivilege dataPrivilege = new DataPrivilege();
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("dataPrivilege", dataPrivilege);

        return "security/dataPrivilege/add";
    }

    @RequestMapping(value = "edit")
    public String edit(String menuNo, Integer id, Model model) {
        DataPrivilege dataPrivilege = dataPrivilegeManager.findDataPrivilege(id);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("dataPrivilege", dataPrivilege);

        return "security/dataPrivilege/edit";
    }

    @RequestMapping(value = "validate")
    @ResponseBody
    public boolean validate(String resourceName) {
        //校验传入的资源是否已设置数据权限
        //校验通过时，返回true给前台，校验失败时，返回false给前台
        return !dataPrivilegeManager.isMasterExist(resourceName);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(DataPrivilege dataPrivilege) {
        AsyncResponse result = new AsyncResponse(false, "保存数据权限成功");
        dataPrivilegeManager.saveDataPrivilege(dataPrivilege);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(DataPrivilege dataPrivilege) {
        AsyncResponse result = new AsyncResponse(false, "保存数据权限成功");
        dataPrivilegeManager.saveDataPrivilege(dataPrivilege);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除数据权限成功");
        dataPrivilegeManager.deleteDataPrivilege(id);
        return result;
    }

    @RequestMapping(value = "getResources")
    @ResponseBody
    public List<Resource> getResources() {
        return appContext.getResources();
    }
}
