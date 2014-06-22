package com.common.security.web.filter;

import com.common.security.context.RequestContext;
import com.common.security.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将未登录用户自动导向登录页面
 * User: Allen
 * Date: 9/20/12
 */
public class LoginFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private Pattern resourcePattern;  //定义不需要过滤的静态资源匹配符，如css，js

    private Pattern ignoredPattern;   //定义不需要过滤的请求，如登录，注销

    private String contextPath;

    private String loginUrl;         //登录页面地址

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String resources = filterConfig.getInitParameter("resources");
        if (StringUtils.isNotEmpty(resources)) {
            resourcePattern = Pattern.compile(resources);
        }
        String ignored = filterConfig.getInitParameter("ignored");
        if (StringUtils.isNotEmpty(ignored)) {
            ignoredPattern = Pattern.compile(ignored);
        }
        contextPath = filterConfig.getServletContext().getContextPath();
        if (StringUtils.isEmpty(contextPath) || "/".equals(contextPath)) {
            contextPath = "";
        }
        loginUrl = filterConfig.getInitParameter("login");
        if (StringUtils.isNotEmpty(loginUrl)) {
            loginUrl = contextPath + loginUrl;
        }
    }

    private boolean excludeResource(String servletPath) {
        if (resourcePattern == null) {
            return false;
        }
        Matcher matcher = resourcePattern.matcher(servletPath);
        return matcher.matches();
    }

    private boolean ignoredRequest(String servletPath) {
        if (ignoredPattern == null) {
            return false;
        }

        Matcher matcher = ignoredPattern.matcher(servletPath);
        return matcher.matches();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String servletPath = httpRequest.getServletPath();

        RequestContext requestContext = new RequestContext();
        requestContext.setUrl(servletPath);
        RequestContext.setCurrentRequestContext(requestContext);
        if (excludeResource(servletPath) || ignoredRequest(servletPath) || SecurityUtil.hasLogin(httpRequest)) {
            chain.doFilter(request, response);
        } else {
            if ("/".equals(servletPath)) {
                httpResponse.sendRedirect(loginUrl);
            } else {
                String fromUri = httpRequest.getRequestURI();
                String queryString = httpRequest.getQueryString();
                if (StringUtils.isNotEmpty(queryString)) {
                    fromUri = fromUri + "?" + queryString;
                }
                logger.debug("from uri:{}", fromUri);
                logger.info("login filter, redirect to login url:{}", loginUrl);
                httpResponse.sendRedirect(loginUrl + "?fromUrl=" + URLEncoder.encode(fromUri, "UTF-8"));
            }
        }
    }

    @Override
    public void destroy() {
    }
}
