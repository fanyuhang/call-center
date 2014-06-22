package com.common.security.web.controller;

import com.common.AppContext;
import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.security.context.UserContext;
import com.common.security.entity.Dept;
import com.common.security.entity.Node;
import com.common.security.entity.User;
import com.common.security.service.DeptManager;
import com.common.security.service.NodeManager;
import com.common.security.service.UserManager;
import com.common.security.util.Md5PasswordEncoder;
import com.common.security.util.SecurityUtil;
import com.common.security.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private NodeManager nodeManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DeptManager deptManager;

    @Autowired
    private AppContext appContext;

    @RequestMapping(value = "/login")
    @ResponseBody
    public AsyncResponse login(HttpServletRequest request, String loginName, String password, String rememberMe, Model model) {
        //TODO deal with rememberMe
        User user = userManager.findUserByLoginName(loginName);

        if (user == null) {
            return new AsyncResponse(true, "用户不存在");
        }
        boolean isSysAdmin = SecurityUtil.isSysAdmin(user.getId());
        if(Constant.USER_STATUS_NOT_VALID == user.getUserStatus()&& !isSysAdmin){
            return new AsyncResponse(true, "用户已注销，不允许登录");
        }
        if(Constant.USER_STATUS_LOCKED == user.getUserStatus()&& !isSysAdmin){
            return new AsyncResponse(true, "用户已锁定，不允许登录");
        }
        if (Constant.USER_STATUS_NORMAL != user.getUserStatus() && !isSysAdmin) {
            return new AsyncResponse(true, "非正常状态用户，不允许登录");
        }

        //@TODO 暂时屏蔽该判断
//        if (Constant.LOGIN_STATUS_LOGIN == user.getLoginStatus() && !isSysAdmin) {
//            return new AsyncResponse(true, "用户已在其它地方登陆");
//        }

        if (!isSysAdmin && request.getRequestURL().toString().startsWith("https")) {
            String certCN = SecurityUtil.getRequestCertCN(request);
            if (certCN == null || !certCN.equals(user.getCertCN())) {
                return new AsyncResponse(true, "证书无效,请检查证书是否已在系统中激活");
            }
        }
        if (StringUtils.isEmpty(password)) {
            return new AsyncResponse(true, "密码不能为空");
        }


        if (!Md5PasswordEncoder.encodePassword(password, loginName).equals(user.getPassword())) {
            int errorTimes = 0;
            StringBuilder builder = new StringBuilder("登陆名密码不匹配");
            if (!isSysAdmin) {
                user.setLoginErrCount(user.getLoginErrCount() == null ? 1 : user.getLoginErrCount() + 1);
                errorTimes = appContext.getIntParameterValue(Constant.PARAM_MAX_ERROR_LOGIN, 5) - user.getLoginErrCount();
                if (errorTimes<=0) {
                    user.setUserStatus(Constant.USER_STATUS_LOCKED);
                    builder.append(",超过尝试次数，账户已锁定");
                }else{
                    builder.append(",还有").append(errorTimes).append("次机会");
                }
                userManager.saveUser(user);
            }

            return new AsyncResponse(true, builder.toString());
        }

        //构造UserContext
        UserContext userContext = new UserContext();
        userContext.setUser(user);
        userContext.setLastLoginTime(user.getLastLoginTime());
        Dept dept = deptManager.findDeptByUser(user.getLoginName());
        if (dept != null) {
            userContext.setDept(dept);
        }
        SecurityUtil.initUserContext(request, userContext);
        //also update login error count to 0
        userManager.updateLoginStatusAndTime(Constant.LOGIN_STATUS_LOGIN, new Date(), SecurityUtil.getCurrentUserId());

        appContext.signIn(user.getId(), request.getSession());
        return new AsyncResponse(false, "登陆成功");
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        appContext.signOff(SecurityUtil.getCurrentUserId());
        request.getSession().invalidate();
        return "login";
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        if (SecurityUtil.hasLogin(request)) {
            return "main";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/user/changePassword")
    @ResponseBody
    public AsyncResponse changePassword(HttpServletRequest request, String oldPassword, String loginPassword) {
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(loginPassword)) {
            return new AsyncResponse(true, "密码不能为空");
        }
        User user = userManager.findUser(SecurityUtil.getCurrentUserId(request));
        if (user == null) {
            return new AsyncResponse(true, "用户已不存在");
        }
        if (!Md5PasswordEncoder.encodePassword(oldPassword, user.getLoginName()).equals(user.getPassword())) {
            return new AsyncResponse(true, "原密码不正确");
        }
        userManager.updatePassword(Md5PasswordEncoder.encodePassword(loginPassword, user.getLoginName()), user.getId());

        return new AsyncResponse(false, "密码修改成功");
    }

    @RequestMapping(value = "/user/menu")
    @ResponseBody
    public List<Node> menu(HttpServletRequest request) {
        Integer userId = SecurityUtil.getCurrentUser(request).getId();
        List<Node> nodes;
        if (userId == 1) {
            //TODO 如果是系统管理员，那么不过滤权限，用角色ID来判断而不是用户ID
            nodes = nodeManager.findAllNode();
        } else {
            nodes = nodeManager.findAuthorisedMenu(userId);
        }
        return TreeUtil.buildMenuTree(nodes);
    }

    @RequestMapping(value = "/user/getCurrentUser")
    @ResponseBody
    public AsyncResponse getCurrentUser(HttpServletRequest request) {
        AsyncResponse asyncResponse = new AsyncResponse(false, "加载用户信息成功");
        asyncResponse.getData().add(SecurityUtil.getCurrentUser(request));
        return asyncResponse;
    }

    @RequestMapping(value = "/user/getAuthorisedButton")
    @ResponseBody
    public AsyncResponse getAuthorisedButton(HttpServletRequest request, String menuNo) {
        AsyncResponse asyncResponse = new AsyncResponse(false, "获取用户授权按钮成功");

        Integer userId = SecurityUtil.getCurrentUser(request).getId();
        List<Node> buttons;
        if (SecurityUtil.isSysAdmin(userId)) {
            buttons = nodeManager.findChildren(menuNo);
        } else {
            buttons = nodeManager.findAuthorisedButton(menuNo, userId);
        }

        asyncResponse.setData(buttons);
        return asyncResponse;
    }
}
