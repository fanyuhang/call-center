package com.redcard.system.entity;

import com.common.core.annotation.Comment;
import com.common.core.util.JsonTimestampSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 11/25/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tbllog")
@Comment(value = "系统日志")
public class SysLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Long id;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(name = "fldoperatedate")
    private Date operateDate;

    @Column(name = "fldloginname")
    private String loginName;

    @Column(name = "fldaction")
    private String action;

    @Column(name = "fldresource")
    private String resource;

    @Column(name = "fldcomment")
    private String comment;

    @Column(name = "fldbak")
    private String bak;

    public SysLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysLog sysLog = (SysLog) o;

        if (action != null ? !action.equals(sysLog.action) : sysLog.action != null) return false;
        if (id != null ? !id.equals(sysLog.id) : sysLog.id != null) return false;
        if (loginName != null ? !loginName.equals(sysLog.loginName) : sysLog.loginName != null) return false;
        if (operateDate != null ? !operateDate.equals(sysLog.operateDate) : sysLog.operateDate != null) return false;
        if (operateId != null ? !operateId.equals(sysLog.operateId) : sysLog.operateId != null) return false;
        if (resource != null ? !resource.equals(sysLog.resource) : sysLog.resource != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operateId != null ? operateId.hashCode() : 0);
        result = 31 * result + (operateDate != null ? operateDate.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", operateId=" + operateId +
                ", operateDate=" + operateDate +
                ", loginName='" + loginName + '\'' +
                ", action='" + action + '\'' +
                ", resource='" + resource + '\'' +
                ", comment='" + comment + '\'' +
                ", bak='" + bak + '\'' +
                '}';
    }
}
