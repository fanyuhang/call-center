/**
 *
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */

package com.redcard.system.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author fanyuhang
 * @version $$Id: ${FILE_NAME}, v 0.1 13-12-8 下午6:27 fanyuhang Exp $$
 */
@javax.persistence.Table(name = "TBLOPERATELOG")
@Entity
public class OperateLog extends BaseObject {
    private String systemName;
    private String firstLevel;
    private String secondLevel;
    private String lastLevel;
    private String action;
    private String comment;
    private String loginName;

    @javax.persistence.Column(name = "FLDSYSTEMNAME")
    @Basic
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @javax.persistence.Column(name = "FLDFIRSTLEVEL")
    @Basic
    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    @javax.persistence.Column(name = "FLDSECONDLEVEL")
    @Basic
    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    @javax.persistence.Column(name = "FLDLASTLEVEL")
    @Basic
    public String getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(String lastLevel) {
        this.lastLevel = lastLevel;
    }

    @javax.persistence.Column(name = "FLDACTION")
    @Basic
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @javax.persistence.Column(name = "FLDCOMMENT")
    @Basic
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @javax.persistence.Column(name = "FLDLOGINNAME")
    @Basic
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperateLog that = (OperateLog) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (firstLevel != null ? !firstLevel.equals(that.firstLevel) : that.firstLevel != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastLevel != null ? !lastLevel.equals(that.lastLevel) : that.lastLevel != null)
            return false;
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null)
            return false;
        if (operateDate != null ? !operateDate.equals(that.operateDate) : that.operateDate != null)
            return false;
        if (operateId != null ? !operateId.equals(that.operateId) : that.operateId != null)
            return false;
        if (secondLevel != null ? !secondLevel.equals(that.secondLevel) : that.secondLevel != null)
            return false;
        if (systemName != null ? !systemName.equals(that.systemName) : that.systemName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (systemName != null ? systemName.hashCode() : 0);
        result = 31 * result + (firstLevel != null ? firstLevel.hashCode() : 0);
        result = 31 * result + (secondLevel != null ? secondLevel.hashCode() : 0);
        result = 31 * result + (lastLevel != null ? lastLevel.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (operateId != null ? operateId.hashCode() : 0);
        result = 31 * result + (operateDate != null ? operateDate.hashCode() : 0);
        return result;
    }
}
