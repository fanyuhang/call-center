package com.redcard.system.entity;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseObject {

    BaseObject() {
        this.id = UUID.randomUUID().toString();
    }

    protected String id;

    @Column(name = "FLDID")
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Integer operateId = SecurityUtil.getCurrentUserId();

    @Column(name = "FLDOPERATEID")
    @Basic
    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    protected Date operateDate = new Date();

    @Column(name = "FLDOPERATEDATE")
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Basic
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    protected User operateUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOPERATEID", insertable = false, updatable = false)
    public User getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }

    @Transient
    public String getOperateName() {
        if (operateUser != null)
            return operateUser.getUserName() + "(" + operateUser.getLoginName() + ")";
        return null;
    }
}
