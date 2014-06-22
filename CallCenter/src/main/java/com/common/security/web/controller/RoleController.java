package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.entity.Role;
import com.common.security.service.RoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/security/role")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleManager roleManager;

    @RequestMapping(value = "init")
    public String init() {
        return "security/role/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Role> list(String where, GridPageRequest pageRequest) {
        Page<Role> roles = roleManager.findAll(where,pageRequest);
        DataResponse dataResponse = new DataResponse<Role>(roles);
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Role role) {
        AsyncResponse result = new AsyncResponse(false, "保存角色成功");
        if (roleManager.isExistRole(role)) {
            return new AsyncResponse(true, "该角色已存在");
        }
        roleManager.save(role);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Role role) {
        AsyncResponse result = new AsyncResponse(false, "保存角色成功");
        if (roleManager.isExistRole(role)) {
            return new AsyncResponse(true, "该角色已存在");
        }
        roleManager.save(role);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除角色成功");
        roleManager.delete(id);
        return result;
    }

    @RequestMapping(value = "isExistRole")
    @ResponseBody
    public boolean isExistRole(Role role) {
        return roleManager.isExistRole(role);
    }
}
