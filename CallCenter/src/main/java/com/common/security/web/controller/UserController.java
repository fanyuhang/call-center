package com.common.security.web.controller;

import com.common.AppContext;
import com.common.Constant;
import com.common.core.filter.FilterRule;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.SystemEnum;
import com.common.security.entity.CertActive;
import com.common.security.entity.User;
import com.common.security.entity.UserDept;
import com.common.security.entity.UserRole;
import com.common.security.service.UserManager;
import com.common.security.util.Md5PasswordEncoder;
import com.common.security.util.SecurityUtil;
import com.phone.constants.PhoneTypeEnum;
import com.phone.entity.Operator;
import com.phone.entity.Uidrole;
import com.phone.service.OperatorManager;
import com.phone.service.UidroleManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/security/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private AppContext appContext;

    @Autowired
    private OperatorManager operatorManager;

    @Autowired
    private UidroleManager uidroleManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "security/user/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<User> list(String where, GridPageRequest pageRequest) {
        FilterTranslator translator = userManager.createFilter(where);
        Page<User> users = userManager.findAll(where, pageRequest);
        List<String> roleNames = null;
        for (User user : users.getContent()) {
            roleNames = userManager.findRoleNameByUser(user.getId());
            user.setRoleNames(StringUtils.join(roleNames, ","));
        }
        DataResponse dataResponse = new DataResponse<User>(users);
        return dataResponse;
    }

    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        User user = new User();
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("user", user);

        return "security/user/add";
    }

    @RequestMapping(value = "edit")
    public String edit(String menuNo, Integer id, Model model) {
        User user = userManager.findUser(id);
        UserDept userDept = userManager.findUserDeptByUser(id);
        List<UserRole> userRoles = userManager.findUserRoleByUser(id);

        List<String> roleIds = new ArrayList<String>(userRoles.size());
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId().toString());
        }
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("user", user);
        model.addAttribute("userDept", userDept);
        model.addAttribute("roleIds", StringUtils.join(roleIds, ","));

        return "security/user/edit";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(User user, String deptCode, String roleIds) {
        AsyncResponse result = new AsyncResponse(false, "保存用户成功");
        if (StringUtils.isNotBlank(user.getCertCN())) {
            if (userManager.existUserCert(user.getCertCN(), user.getId())) {
                return new AsyncResponse(true, "该CN已被绑定");
            }
        }
        if (userManager.existUser(user.getLoginName())) {
            return new AsyncResponse(true, "该用户已存在");
        }

        user.setPassword(Md5PasswordEncoder.encodePassword(user.getPassword(), user.getLoginName()));
        user.setLoginStatus(Constant.LOGIN_STATUS_LOGOUT);
        user.setUserStatus(Constant.USER_STATUS_NORMAL);
        user.setSystem(SystemEnum.SYSTEM_SELF.toString());
        user.setLoginErrCount(0);
        user.setModifyPwdCount(0);
        userManager.saveUser(user, deptCode, roleIds);
        user = userManager.findUserByLoginName(user.getLoginName());
        SecurityUtil.getCurrentUserContext().setUser(user);

        try {
            operatorManager.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new AsyncResponse(true, "话务系统用户创建失败，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(User user, String deptCode, String roleIds) {
        AsyncResponse result = new AsyncResponse(false, "保存用户成功");
        if (StringUtils.isNotBlank(user.getCertCN())) {
            if (userManager.existUserCert(user.getCertCN(), user.getId())) {
                return new AsyncResponse(true, "该CN已被绑定");
            }
        }
        userManager.saveUser(user, deptCode, roleIds);
        user = userManager.findUserByLoginName(user.getLoginName());
        SecurityUtil.getCurrentUserContext().setUser(user);
        try {
            operatorManager.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new AsyncResponse(true, "话务系统用户创建失败，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除用户成功");
//        userManager.deleteUser(id);
        User user = userManager.findUser(id);
        userManager.updateUserStatus(Constant.USER_STATUS_NOT_VALID, id);
        try {
            if (user != null) {
                operatorManager.delete(user.getLoginName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new AsyncResponse(true, "话务系统用户创建失败，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "cancel")
    @ResponseBody
    public AsyncResponse cancel(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "注销用户成功");
        userManager.updateUserStatus(Constant.USER_STATUS_NOT_VALID, id);
        User user = userManager.findUser(id);
        userManager.updateUserStatus(Constant.USER_STATUS_NOT_VALID, id);
        try {
            if (user != null) {
                operatorManager.delete(user.getLoginName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new AsyncResponse(true, "话务系统用户创建失败，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "view")
    public String view(Integer id, Model model) {
        User user = userManager.findUser(id);
        UserDept userDept = userManager.findUserDeptByUser(id);
        List<UserRole> userRoles = userManager.findUserRoleByUser(id);

        List<String> roleIds = new ArrayList<String>(userRoles.size());
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId().toString());
        }

        model.addAttribute("user", user);
        model.addAttribute("userDept", userDept);
        model.addAttribute("roleIds", StringUtils.join(roleIds, ","));

        return "security/user/view";
    }

    @RequestMapping(value = "resetPassword")
    @ResponseBody
    public AsyncResponse resetPassword(Integer userId, String loginPassword) {
        User user = userManager.findUser(userId);
        if (user == null) {
            return new AsyncResponse(true, "用户已不存在");
        }
        AsyncResponse result = new AsyncResponse(false, "密码重置成功");
        userManager.updatePassword(Md5PasswordEncoder.encodePassword(loginPassword, user.getLoginName()), userId);
        return result;
    }

    @RequestMapping(value = "getCertCN")
    @ResponseBody
    public AsyncResponse getCertCN(HttpServletRequest request) {
        String cn = SecurityUtil.getRequestCertCN(request);
        if (StringUtils.isBlank(cn)) {
            return new AsyncResponse(true, "未找到证书");
        }
        AsyncResponse asyncResponse = new AsyncResponse(false, "读取证书信息成功");
        asyncResponse.getData().add(cn);
        return asyncResponse;
    }

    @RequestMapping(value = "activeCert")
    @ResponseBody
    public AsyncResponse activeCert(Integer userId, String certCN, HttpServletRequest request) {
        //TODO 暂时保留userId参数
        /*if (userId == null) {
            return new AsyncResponse(true, "请选择用户");
        }*/
        if (StringUtils.isBlank(certCN)) {
            return new AsyncResponse(true, "未找到证书");
        }
        CertActive certActive = SecurityUtil.getRequestCert(request);
        if (userManager.existCert(certActive.getCertCN())) {
            return new AsyncResponse(true, "该USB Key已激活");
        }
        userManager.activeCert(certActive);

        return new AsyncResponse(false, "激活USB Key成功");
    }

    @RequestMapping(value = "unlock")
    @ResponseBody
    public AsyncResponse unlock(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "解锁用户成功");
        userManager.unlockUser(id);
        return result;
    }

    @RequestMapping(value = "signoff")
    @ResponseBody
    public AsyncResponse signoff(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "用户签退成功");
        HttpSession session = appContext.getSession(id);
        try {
            if (session != null) {
                session.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        appContext.signOff(id);
        userManager.updateLoginStatus(Constant.LOGIN_STATUS_LOGOUT, id);
        return result;
    }
}
