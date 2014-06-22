package com.common.core.entity;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BaseObject {

    protected Integer id;

    @javax.persistence.Column(name = "FLDID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected Integer operateId;

    @javax.persistence.Column(name = "FLDOPERATEID")
    @Basic
    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    protected Date operateDate;

    @javax.persistence.Column(name = "FLDOPERATEDATE")
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
