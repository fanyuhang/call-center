package com.common.security.util;

import com.common.AppContext;
import com.common.Constant;
import com.common.security.context.RequestContext;
import com.common.security.context.UserContext;
import com.common.security.entity.CertActive;
import com.common.security.entity.Dept;
import com.common.security.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 该类用于操作用户登录之后在session中的数据
 * User: Allen
 * Date: 9/20/12
 */
public class SecurityUtil {

    /**
     * 从spring 环境中获得当前http request
     *
     * @return httpRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 从session中获取当前登录用户对应的UserContext
     *
     * @param request httpRequest
     * @return UserContext
     */
    public static UserContext getCurrentUserContext(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (UserContext) request.getSession().getAttribute(Constant.CURRENT_USER);
    }

    /**
     * 从session中获取当前登录用户对应的UserContext
     *
     * @param session httpSession
     * @return UserContext
     */
    public static UserContext getCurrentUserContext(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (UserContext) session.getAttribute(Constant.CURRENT_USER);
    }

    /**
     * 从spring环境中获取当前登录用户对应的UserContext
     *
     * @return UserContext
     */
    public static UserContext getCurrentUserContext() {
        return getCurrentUserContext(getHttpServletRequest());
    }

    /**
     * 从session中获取当前登录用户
     *
     * @param request httpRequest
     * @return User
     */
    public static User getCurrentUser(HttpServletRequest request) {
        User currentUser = null;
        UserContext userContext = getCurrentUserContext(request);
        if (userContext != null) {
            currentUser = userContext.getUser();
        }
        return currentUser;
    }

    /**
     * 从session中获取当前登录用户
     *
     * @param sessions httpSession
     * @return User
     */
    public static User getCurrentUser(HttpSession sessions) {
        User currentUser = null;
        UserContext userContext = getCurrentUserContext(sessions);
        if (userContext != null) {
            currentUser = userContext.getUser();
        }
        return currentUser;
    }

    /**
     * 从spring环境中获取当前登录用户
     *
     * @return User
     */
    public static User getCurrentUser() {
        return getCurrentUser(getHttpServletRequest());
    }

    /**
     * 从session中获取当前登录用户ID
     *
     * @param request httpRequest
     * @return Integer   userId
     */
    public static Integer getCurrentUserId(HttpServletRequest request) {
        Integer userId = null;
        User user = getCurrentUser(request);
        if (user != null) {
            userId = user.getId();
        }
        return userId;
    }

    /**
     * 从spring环境中获取当前登录用户账户
     *
     * @return String   loginName
     */
    public static String getCurrentUserLoginName(){
        return getCurrentUserLoginName(getHttpServletRequest());
    }

    /**
     * 从session中获取当前登录用户的登录账户
     * */
    public static String getCurrentUserLoginName(HttpServletRequest request){
        String loginName = null;
        User user = getCurrentUser(request);
        if(user!=null){
            loginName = user.getLoginName();
        }
        return loginName;
    }

    /**
     * 从session中获取当前登录用户ID
     *
     * @param session httpSession
     * @return Integer   userId
     */
    public static Integer getCurrentUserId(HttpSession session) {
        Integer userId = null;
        User user = getCurrentUser(session);
        if (user != null) {
            userId = user.getId();
        }
        return userId;
    }

    /**
     * 从spring环境中获取当前登录用户ID
     *
     * @return Integer   userId
     */
    public static Integer getCurrentUserId() {
        return getCurrentUserId(getHttpServletRequest());
    }

    /**
     * 从session中获取当前登录用户对应的部门
     *
     * @param request httpRequest
     * @return Dept
     */
    public static Dept getCurrentDept(HttpServletRequest request) {
        Dept dept = null;
        UserContext userContext = getCurrentUserContext(request);
        if (userContext != null) {
            dept = userContext.getDept();
        }
        return dept;
    }

    /**
     * 从spring环境中获取当前登录用户对应的部门
     *
     * @return Dept
     */
    public static Dept getCurrentDept() {
        return getCurrentDept(getHttpServletRequest());
    }

    /**
     * 用户登录之后，初始化用户在session中的数据
     *
     * @param request     httpRequest
     * @param userContext userContext
     */
    public static void initUserContext(HttpServletRequest request, UserContext userContext) {
        request.getSession().setAttribute(Constant.CURRENT_USER, userContext);
    }

    /**
     * 清除当前登录用户在session中对应的UserContext
     *
     * @param request httpRequest
     */
    public static void cleanUserContext(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.CURRENT_USER);
    }

    /**
     * 判断用户是否已登录
     *
     * @param request httpRequest
     * @return true 表示已登录
     */
    public static boolean hasLogin(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return user != null;
    }

    public static X509Certificate getUniqueCertificate(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute(Constant.CERTIFICATE_KEY);
        if (certs != null && certs.length > 0) {
            return certs[0];
        }
        return null;
    }

    /**
     * 获取当前使用证书
     *
     * @param request httpRequest
     * @return 证书
     */
    public static CertActive getRequestCert(HttpServletRequest request) {
        CertActive certActive = new CertActive();
        X509Certificate cert = getUniqueCertificate(request);
        if (cert != null) {
            String subject = cert.getSubjectDN().getName();
            String cn = "";
            if (StringUtils.isNotBlank(subject)) {
                String[] array = subject.split("=");
                if (array != null && array.length >= 2) {
                    cn = array[1];
                } else {
                    cn = subject;
                }
            }
            certActive.setPublicKey(Hex.toHexString(cert.getPublicKey().getEncoded()));
            certActive.setCertCN(cn);
        }
        return certActive;
    }

    /**
     * 获得当前使用证书序列号
     *
     * @param request httpRequest
     * @return 证书序列号
     */
    public static String getRequestCertCN(HttpServletRequest request) {
        String cn = "";
        X509Certificate cert = getUniqueCertificate(request);
        if (cert != null) {
            String subject = cert.getSubjectDN().getName();
            if (StringUtils.isNotBlank(subject)) {
                String[] array = subject.split("=");
                if (array != null && array.length >= 2) {
                    cn = array[1];
                } else {
                    cn = subject;
                }
            }
        }
        return cn;
    }

    /**
     * 获得当前请求上下文环境
     *
     * @return requestContext
     */
    public static RequestContext getRequestContext() {
        return RequestContext.getCurrentRequestContext();
    }

    /**
     * 获得当前请求资源路径
     *
     * @return full path
     */
    public static String getRequestResource() {
        RequestContext requestContext = getRequestContext();
        if (requestContext == null) {
            return "";
        }

        String url = requestContext.getUrl();
        return url;
    }

    /**
     * 打开数据权限功能
     */
    public static void openDataFilter() {
        RequestContext.openDataFilter();
    }

    /**
     * 关闭数据权限功能
     */
    public static void closeDataFilter() {
        RequestContext.closeDataFilter();
    }

    /**
     * 获取数据权限状态
     *
     * @return 是否已打开数据权限
     */
    public static boolean getDataFilter() {
        return RequestContext.getDataFilter();
    }

    /**
     * 判断用户是否是默认管理员
     *
     * @param userId 用户ID
     * @return true 是默认管理员
     */
    public static boolean isSysAdmin(Integer userId) {
        int adminId = AppContext.getInstance().getIntParameterValue(Constant.PARAM_SYS_ADMIN, 1);
        return userId != null && userId == adminId;
    }

    /**
     * 判断用户是否是默认管理员
     *
     * @return true 是默认管理员
     */
    public static boolean isSysAdmin() {
        Integer userId = getCurrentUserId();
        int adminId = AppContext.getInstance().getIntParameterValue(Constant.PARAM_SYS_ADMIN, 1);
        return userId != null && userId == adminId;
    }

    public static String getContext(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if ("/".equals(contextPath)) {
            contextPath = "";
        }
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        return contextPath;
    }
}
