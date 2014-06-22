package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.security.service.FavoriteManager;
import com.common.security.service.PrivilegeManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 功能权限操作处理类
 * User: Allen
 * Date: 9/11/12
 */
@Controller
@RequestMapping(value = "/security/privilege")
public class PrivilegeController {

    private static Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeManager privilegeManager;

    @Autowired
    private FavoriteManager favoriteManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "security/privilege/privilege";
    }

    @RequestMapping(value = "getRolePermission")
    @ResponseBody
    public AsyncResponse getRolePermission(Integer roleId) {
        if (roleId == null) {
            return new AsyncResponse(true, "获取角色权限失败，角色ID为空");
        }

        return new AsyncResponse(false, "获取角色权限成功", privilegeManager.findRolePrivilege(roleId));
    }

    @RequestMapping(value = "getUserPermission")
    @ResponseBody
    public AsyncResponse getUserPermission(Integer userId) {
        if (userId == null) {
            return new AsyncResponse(true, "获取用户权限失败，用户ID为空");
        }

        return new AsyncResponse(false, "获取用户权限成功", privilegeManager.findUserPrivilege(userId));
    }

    @RequestMapping(value = "saveRolePrivilege")
    @ResponseBody
    public AsyncResponse saveRolePrivilege(Integer roleId, String privilegeData) {
        ObjectMapper mapper = new ObjectMapper();
        if (roleId == null) {
            return new AsyncResponse(false, "保存角色权限失败，角色ID为空");
        }
        try {
            List<Map> permissions = mapper.readValue(privilegeData, List.class);
            privilegeManager.saveRolePrivilege(roleId, permissions);
            favoriteManager.updateByRoleId(roleId);
        } catch (IOException e) {
            return new AsyncResponse(false, "解析权限数据失败");
        }

        return new AsyncResponse(false, "保存角色权限成功");
    }

    @RequestMapping(value = "saveUserPrivilege")
    @ResponseBody
    public AsyncResponse saveUserPrivilege(Integer userId, String privilegeData) {
        ObjectMapper mapper = new ObjectMapper();
        if (userId == null) {
            return new AsyncResponse(true, "保存用户权限失败，用户ID为空");
        }
        try {
            List<Map> permissions = mapper.readValue(privilegeData, List.class);
            privilegeManager.saveUserPrivilege(userId, permissions);
            favoriteManager.updateByUserId(userId);
        } catch (IOException e) {
            return new AsyncResponse(true, "解析权限数据失败");
        }
        return new AsyncResponse(false, "保存用户权限成功");

    }
}
