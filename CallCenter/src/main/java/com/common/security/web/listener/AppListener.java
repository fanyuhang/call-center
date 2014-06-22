package com.common.security.web.listener;

import com.common.Constant;
import com.common.security.service.UserManager;
import com.common.security.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Http listener
 * User: Allen
 * Date: 11/10/12
 */
public class AppListener implements HttpSessionListener, ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AppListener.class);

    public <T> T getBean(ServletContext context, Class<T> clazz) {
        return WebApplicationContextUtils.getWebApplicationContext(context).getBean(clazz);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("context initialized");
        UserManager userManager = getBean(sce.getServletContext(), UserManager.class);
        //update login status to logout for all users
        userManager.logoutAllUser();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        UserManager userManager = getBean(sce.getServletContext(), UserManager.class);
        //update login status to logout for all users
        userManager.logoutAllUser();
        logger.debug("context destroyed");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.debug("session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        UserManager userManager = getBean(se.getSession().getServletContext(), UserManager.class);
        //update login status to logout for current user
        Integer userId = SecurityUtil.getCurrentUserId(se.getSession());
        userManager.updateLoginStatus(Constant.LOGIN_STATUS_LOGOUT, userId);
        logger.debug("session destroyed");
    }
}
