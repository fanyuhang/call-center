package com.redcard.telephone.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by thinkpad on 15-2-26.
 */
@Entity
@Table(name = "TBLTELEPHONETRACELOG")
public class TelephoneTraceLog {
    private Long fldId;
    private Long fldTraceId;
    private String fldStatusDesc;
    private String fldComment;
    private String fldOperateUserNo;
    private Date fldOperateDate;
    private String fldCreateUserNo;
    private Date fldCreateDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLDID")
    public Long getFldId() {
        return fldId;
    }

    public void setFldId(Long fldId) {
        this.fldId = fldId;
    }

    @Basic
    @Column(name = "FLDTRACEID")
    public Long getFldTraceId() {
        return fldTraceId;
    }

    public void setFldTraceId(Long fldTraceId) {
        this.fldTraceId = fldTraceId;
    }

    @Basic
    @Column(name = "FLDSTATUSDESC")
    public String getFldStatusDesc() {
        return fldStatusDesc;
    }

    public void setFldStatusDesc(String fldStatusDesc) {
        this.fldStatusDesc = fldStatusDesc;
    }

    @Basic
    @Column(name = "FLDCOMMENT")
    public String getFldComment() {
        return fldComment;
    }

    public void setFldComment(String fldComment) {
        this.fldComment = fldComment;
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
    @Column(name = "FLDCREATEDATE")
    public Date getFldCreateDate() {
        return fldCreateDate;
    }

    public void setFldCreateDate(Date fldCreateDate) {
        this.fldCreateDate = fldCreateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelephoneTraceLog that = (TelephoneTraceLog) o;

        if (fldComment != null ? !fldComment.equals(that.fldComment) : that.fldComment != null) return false;
        if (fldCreateDate != null ? !fldCreateDate.equals(that.fldCreateDate) : that.fldCreateDate != null)
            return false;
        if (fldCreateUserNo != null ? !fldCreateUserNo.equals(that.fldCreateUserNo) : that.fldCreateUserNo != null)
            return false;
        if (fldId != null ? !fldId.equals(that.fldId) : that.fldId != null) return false;
        if (fldOperateDate != null ? !fldOperateDate.equals(that.fldOperateDate) : that.fldOperateDate != null)
            return false;
        if (fldOperateUserNo != null ? !fldOperateUserNo.equals(that.fldOperateUserNo) : that.fldOperateUserNo != null)
            return false;
        if (fldStatusDesc != null ? !fldStatusDesc.equals(that.fldStatusDesc) : that.fldStatusDesc != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fldId != null ? fldId.hashCode() : 0;
        result = 31 * result + (fldStatusDesc != null ? fldStatusDesc.hashCode() : 0);
        result = 31 * result + (fldComment != null ? fldComment.hashCode() : 0);
        result = 31 * result + (fldOperateUserNo != null ? fldOperateUserNo.hashCode() : 0);
        result = 31 * result + (fldOperateDate != null ? fldOperateDate.hashCode() : 0);
        result = 31 * result + (fldCreateUserNo != null ? fldCreateUserNo.hashCode() : 0);
        result = 31 * result + (fldCreateDate != null ? fldCreateDate.hashCode() : 0);
        return result;
    }
}
