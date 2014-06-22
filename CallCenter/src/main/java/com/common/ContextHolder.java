package com.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: Allen
 * Date: 11/25/12
 */
@Component
public class ContextHolder implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(AppContext.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextHolder.context = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return ContextHolder.context.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ContextHolder.context.getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return ContextHolder.context.getBean(beanName);
    }
}
