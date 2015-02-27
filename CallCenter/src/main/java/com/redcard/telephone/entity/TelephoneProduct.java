package com.redcard.telephone.entity;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by thinkpad on 15-2-26.
 */
@Entity
@Table(name = "TBLTELEPHONEPRODUCT")
public class TelephoneProduct {
    private Integer fldId;
    private String fldTitle;
    private String fldContent;
    private Integer fldLevel;
    private Integer fldStatus;
    private String fldOperateUserNo;
    private Date fldOperateDate;
    private String fldCreateUserNo;
    private Date fldCreateDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLDID")
    public Integer getFldId() {
        return fldId;
    }

    public void setFldId(Integer fldId) {
        this.fldId = fldId;
    }

    @Basic
    @Column(name = "FLDTITLE")
    public String getFldTitle() {
        return fldTitle;
    }

    public void setFldTitle(String fldTitle) {
        this.fldTitle = fldTitle;
    }

    @Basic
    @Column(name = "FLDCONTENT")
    public String getFldContent() {
        return fldContent;
    }

    public void setFldContent(String fldContent) {
        this.fldContent = fldContent;
    }

    @Basic
    @Column(name = "FLDLEVEL")
    public Integer getFldLevel() {
        return fldLevel;
    }

    public void setFldLevel(Integer fldLevel) {
        this.fldLevel = fldLevel;
    }

    @Basic
    @Column(name = "FLDSTATUS")
    public Integer getFldStatus() {
        return fldStatus;
    }

    public void setFldStatus(Integer fldStatus) {
        this.fldStatus = fldStatus;
    }

    @Basic
    @Column(name = "FLDOPERATEUSERNO")
    public String getFldOperateUserNo() {
        return fldOperateUserNo;
    }

    public void setFldOperateUserNo(String fldOperateUserNo) {
        this.fldOperateUserNo = fldOperateUserNo;
    }

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(name = "FLDOPERATEDATE")
    public Date getFldOperateDate() {
        return fldOperateDate;
    }

    public void setFldOperateDate(Date fldOperateDate) {
        this.fldOperateDate = fldOperateDate;
    }

    @Basic
    @Column(name = "FLDCREATEUSERNO")
    public String getFldCreateUserNo() {
        return fldCreateUserNo;
    }

    public void setFldCreateUserNo(String fldCreateUserNo) {
        this.fldCreateUserNo = fldCreateUserNo;
    }

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(name = "FLDCREATEDATE")
    public Date getFldCreateDate() {
        return fldCreateDate;
    }

    public void setFldCreateDate(Date fldCreateDate) {
        this.fldCreateDate = fldCreateDate;
    }

    private User operateUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOPERATEUSERNO", referencedColumnName = "FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }

    @Transient
    public String getOperateName() {
        if (operateUser != null) {
            return operateUser.getUserName();
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelephoneProduct that = (TelephoneProduct) o;

        if (fldContent != null ? !fldContent.equals(that.fldContent) : that.fldContent != null) return false;
        if (fldCreateDate != null ? !fldCreateDate.equals(that.fldCreateDate) : that.fldCreateDate != null)
            return false;
        if (fldCreateUserNo != null ? !fldCreateUserNo.equals(that.fldCreateUserNo) : that.fldCreateUserNo != null)
            return false;
        if (fldId != null ? !fldId.equals(that.fldId) : that.fldId != null) return false;
        if (fldLevel != null ? !fldLevel.equals(that.fldLevel) : that.fldLevel != null) return false;
        if (fldOperateDate != null ? !fldOperateDate.equals(that.fldOperateDate) : that.fldOperateDate != null)
            return false;
        if (fldOperateUserNo != null ? !fldOperateUserNo.equals(that.fldOperateUserNo) : that.fldOperateUserNo != null)
            return false;
        if (fldStatus != null ? !fldStatus.equals(that.fldStatus) : that.fldStatus != null) return false;
        if (fldTitle != null ? !fldTitle.equals(that.fldTitle) : that.fldTitle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fldId != null ? fldId.hashCode() : 0;
        result = 31 * result + (fldTitle != null ? fldTitle.hashCode() : 0);
        result = 31 * result + (fldContent != null ? fldContent.hashCode() : 0);
        result = 31 * result + (fldLevel != null ? fldLevel.hashCode() : 0);
        result = 31 * result + (fldStatus != null ? fldStatus.hashCode() : 0);
        result = 31 * result + (fldOperateUserNo != null ? fldOperateUserNo.hashCode() : 0);
        result = 31 * result + (fldOperateDate != null ? fldOperateDate.hashCode() : 0);
        result = 31 * result + (fldCreateUserNo != null ? fldCreateUserNo.hashCode() : 0);
        result = 31 * result + (fldCreateDate != null ? fldCreateDate.hashCode() : 0);
        return result;
    }
}
