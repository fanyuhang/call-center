package com.common.core.util;

import com.common.ContextHolder;
import com.common.security.context.RequestContext;
import com.redcard.system.entity.SysLog;
import com.redcard.system.service.SysLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.GenerationType;

/**
 * User: Allen
 * Date: 11/27/12
 */
public class SysLogUtil {

    private static Logger logger = LoggerFactory.getLogger(SysLogUtil.class);

    private static SysLogManager sysLogManager;

    public static SysLogManager getSysLogManager() {
        if (sysLogManager == null) {
            synchronized (SysLogUtil.class) {
                if (sysLogManager == null) {
                    sysLogManager = ContextHolder.getBean(SysLogManager.class);
                }
            }
        }
        return sysLogManager;
    }

    public static void saveSysLog(SysLog entity) {
        SysLogManager logManager = getSysLogManager();
        if (logManager != null) {
            logManager.saveSysLog(entity);
        }
    }

    public static void saveSysLog(Object target, String action) {
        SysLogManager logManager = getSysLogManager();
        if (logManager != null) {
            logManager.saveSysLog(target, action);
        }
    }

    public static void saveSysLog(String action, String comment) {
        SysLogManager logManager = getSysLogManager();
        if (logManager != null) {
            logManager.saveSysLog(action, comment);
        }
    }

    public static void saveSysLog(String action, String resource, String comment) {
        SysLogManager logManager = getSysLogManager();
        if (logManager != null) {
            logManager.saveSysLog(action, resource, comment);
        }
    }

    public static GenerationType getIdStrategy(Object entity) {
        return EntityUtil.getIdStrategy(entity, SysLogUtil.getSysLogManager().getEntityManager());
    }

    public static void openLog() {
        RequestContext.openLog();
    }

    /**
     * 关闭记录日志功能
     */
    public static void closeLog() {
        RequestContext.closeLog();
    }

    /**
     * 获取日志标志位
     *
     * @return 是否需要记录日志
     */
    public static boolean getLogFlag() {
        return RequestContext.getLogFlag();
    }
}
