package com.common.core.entity;

import java.util.Date;

/**
 * 该接口用于处理操作人，操作时间
 * User: Allen
 * Date: 9/16/12
 */
public interface Auditable {

    public Integer getOperateId();

    public void setOperateId(Integer operateId);

    public Date getOperateDate();

    public void setOperateDate(Date operateDate);
}
