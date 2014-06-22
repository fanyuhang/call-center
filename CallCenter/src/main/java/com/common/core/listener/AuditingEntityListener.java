package com.common.core.listener;

import com.common.core.entity.Auditable;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * set information for auditable entity
 * User: Allen
 * Date: 12-9-29
 */
@Configurable
public class AuditingEntityListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditingEntityListener.class);

    @PrePersist
    public void touchForCreate(Object target) {
        touch(target, true);
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        touch(target, false);
    }

    private void touch(Object target, boolean isNew) {
        if (!(target instanceof Auditable)) {
            return;
        }
        Auditable auditable = (Auditable) target;
        Date now = new Date();
        User auditor = SecurityUtil.getCurrentUser();

        if (auditor != null) {
            auditable.setOperateId(auditor.getId());
        }
        auditable.setOperateDate(now);

        Object defaultedAuditor = auditor == null ? "unknown" : auditor.getId();

        logger.debug("Touched {} - Last modification at {} by {}", new Object[]{auditable, now, defaultedAuditor});
    }
}

