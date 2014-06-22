package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.security.entity.Dept;
import com.common.security.entity.Role;
import com.common.security.entity.User;
import com.common.security.service.DeptManager;
import com.common.security.service.RoleManager;
import com.common.security.service.UserManager;
import com.common.security.util.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限操作处理类
 * User: Allen
 * Date: 9/13/12
 */
@Controller
@RequestMapping(value = "/security/select")
public class SelectController {

    private static Logger logger = LoggerFactory.getLogger(SelectController.class);

    @Autowired
    private DeptManager deptManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserManager userManager;


    @RequestMapping(value = "selectDept")
    @ResponseBody
    public List<Dept> selectDeptTree() {
        //构造部门列表中的tree grid数据
        List<Dept> result = new ArrayList<Dept>();
        result.add(TreeUtil.buildDeptTree(deptManager.findAllDept()));
        return result;
    }

    @RequestMapping(value = "selectRole")
    @ResponseBody
    public List<Role> selectRole() {
        //构造部门列表中的tree grid数据
        return roleManager.findAll();
    }

    @RequestMapping(value = "selectUser")
    @ResponseBody
    public List<User> selectUser() {
        //构造部门列表中的tree grid数据
        return userManager.findAllUser();
    }

    @RequestMapping(value = "selectIcon")
    @ResponseBody
    public AsyncResponse selectIcon() {
        AsyncResponse result = new AsyncResponse(false, "获取图标成功");
        List<String> icons = new ArrayList<String>();
        icons.add("static\\ligerUI\\icons\\32X32\\address.gif");
        result.setData(icons);

        return result;
    }

    @RequestMapping(value = "selectCN")
    @ResponseBody
    public AsyncResponse selectCN() {
        AsyncResponse result = new AsyncResponse(false, "获取CN成功");
        result.setData(userManager.findAvailableCert());

        return result;
    }
}
