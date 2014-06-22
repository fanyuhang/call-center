package com.common.core.listener;

import com.common.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * automatic reload cache
 * User: Allen
 * Date: 10/31/12
 */
@Configurable
public class AppCacheListener {

    private static final Logger logger = LoggerFactory.getLogger(AppCacheListener.class);

    @PostPersist
    public void postForCreate(Object target) {
        updateCache(target);
    }

    @PostUpdate
    public void postForUpdate(Object target) {
        updateCache(target);
    }

    @PostRemove
    public void postForRemove(Object target) {
        updateCache(target);
    }

    private void updateCache(Object target) {
        AppContext.getInstance().syncData(target.getClass());
    }
}
