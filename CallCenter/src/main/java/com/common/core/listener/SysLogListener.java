package com.common.core.listener;

import com.common.Constant;
import com.common.core.entity.Auditable;
import com.common.core.util.SysLogUtil;
import com.redcard.system.entity.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;

/**
 * save log for user operation
 * User: Allen
 * Date: 11/25/12
 */
@Configurable
public class SysLogListener {

    private static final Logger logger = LoggerFactory.getLogger(SysLogListener.class);

    @PrePersist
    public void touchForCreate(Object target) {
        GenerationType idStrategy = SysLogUtil.getIdStrategy(target);
        if (idStrategy == null) {
            touch(target, Constant.LOG_ADD);
        }
    }

    @PostPersist
    public void touchForCreatePost(Object target) {
        GenerationType idStrategy = SysLogUtil.getIdStrategy(target);
        if (idStrategy != null) {
            touch(target, Constant.LOG_ADD);
        }
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        touch(target, Constant.LOG_UPDATE);
    }

    @PreRemove
    public void touchForDelete(Object target) {
        touch(target, Constant.LOG_DELETE);
    }

    private void touch(Object target, String action) {
        if(!(target instanceof Auditable)){
            return;
        }
        if (target instanceof SysLog) {
            return;
        }
        if (!SysLogUtil.getLogFlag()) {
            return;
        }
        //@TODO 暂时不通过该方法做日志拦截
//        SysLogUtil.saveSysLog(target, action);
    }
}
