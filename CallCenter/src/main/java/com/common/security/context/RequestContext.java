package com.common.security.context;

import java.io.Serializable;

/**
 * save some attributes for the current request
 * User: Allen
 * Date: 12-9-29
 */
public class RequestContext implements Serializable {

    private static final ThreadLocal<RequestContext> requestContextHolder = new ThreadLocal<RequestContext>();

    private boolean logFlag = true;   //是否需要记录日志

    private boolean dataFilter = true;

    private String url;

    public RequestContext() {
    }

    public static RequestContext getCurrentRequestContext() {
        return requestContextHolder.get();
    }

    public static void setCurrentRequestContext(RequestContext requestContext) {
        requestContextHolder.set(requestContext);
    }

    /**
     * 打开记录日志功能
     */
    public static void openLog() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            requestContext.setLogFlag(true);
        }
    }

    /**
     * 关闭记录日志功能
     */
    public static void closeLog() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            requestContext.setLogFlag(false);
        }
    }

    /**
     * 获取日志标志位
     *
     * @return 是否需要记录日志
     */
    public static boolean getLogFlag() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            return requestContext.isLogFlag();
        }
        return false;
    }

    public boolean isLogFlag() {
        return logFlag;
    }

    public void setLogFlag(boolean logFlag) {
        this.logFlag = logFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDataFilter() {
        return dataFilter;
    }

    public void setDataFilter(boolean dataFilter) {
        this.dataFilter = dataFilter;
    }

    /**
     * 打开数据权限功能
     */
    public static void openDataFilter() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            requestContext.setDataFilter(true);
        }
    }

    /**
     * 关闭数据权限功能
     */
    public static void closeDataFilter() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            requestContext.setDataFilter(false);
        }
    }

    /**
     * 获取数据权限状态
     *
     * @return 是否已打开数据权限
     */
    public static boolean getDataFilter() {
        RequestContext requestContext = requestContextHolder.get();
        if (requestContext != null) {
            return requestContext.isDataFilter();
        }
        return false;
    }
}
