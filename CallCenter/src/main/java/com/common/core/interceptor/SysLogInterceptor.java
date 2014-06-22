/**
 *
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */

package com.common.core.interceptor;

import com.common.core.util.SysLogUtil;
import com.common.security.util.SecurityUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author fanyuhang
 * @version $$Id: ${FILE_NAME}, v 0.1 13-12-14 下午2:51 fanyuhang Exp $$
 */
public class SysLogInterceptor implements HandlerInterceptor {

    private void saveOperateLog(HttpServletRequest request) {
        Map map = request.getParameterMap();

        if(SecurityUtil.getRequestResource().indexOf("user/getAuthorisedButton")!=-1
                ||SecurityUtil.getRequestResource().indexOf("security/node/tree")!=-1){
            return;
        }

        try {

            StringBuilder builder = new StringBuilder();

            Set<Map.Entry> entrySet = map.entrySet();

            for (Map.Entry entry : entrySet) {
                if (entry.getKey() instanceof String) {

                    if (entry.getValue() instanceof Collection) {
                        continue;
                    }

                    if (entry.getValue() instanceof Map) {
                        continue;
                    }

                    if (entry.getValue() instanceof String) {
                        builder.append(entry.getKey()).append("={").append(entry.getValue()).append("}");
                    } else if (entry.getValue() instanceof String[]) {
                        builder.append(entry.getKey()).append("={").append(((String[]) entry.getValue())[0]).append("}");
                    } else {
                        builder.append(entry.getKey()).append("=").append(BeanUtils.describe(entry.getValue()).toString());
                    }
                } else {
                    continue;
                }
            }

            if (builder.length() > 1000) {
                SysLogUtil.saveSysLog("", builder.substring(0, 1000));
            } else {
                SysLogUtil.saveSysLog("", builder.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if ("/logout".equalsIgnoreCase(SecurityUtil.getRequestResource())) {
            saveOperateLog(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if (!"/logout".equalsIgnoreCase(SecurityUtil.getRequestResource())) {
            saveOperateLog(request);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }
}
