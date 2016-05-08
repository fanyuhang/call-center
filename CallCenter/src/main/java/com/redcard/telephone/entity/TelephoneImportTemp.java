package com.redcard.telephone.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by thinkpad on 16-4-18.
 */
@Entity
@javax.persistence.Table(name = "TBLTELEPHONEIMPORTTEMP")
public class TelephoneImportTemp implements java.io.Serializable {

    private static final long serialVersionUID = -575153679592006678L;
    private Long fldId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "FLDID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getFldId() {
        return fldId;
    }

    public void setFldId(Long fldId) {
        this.fldId = fldId;
    }

    private String fldBatchNo;

    @Basic
    @javax.persistence.Column(name = "FLDBATCHNO", nullable = true, insertable = true, updatable = true, length = 40, precision = 0)
    public String getFldBatchNo() {
        return fldBatchNo;
    }

    public void setFldBatchNo(String fldBatchNo) {
        this.fldBatchNo = fldBatchNo;
    }

    private String fldCustomerId;

    @Basic
    @javax.persistence.Column(name = "FLDCUSTOMERID", nullable = true, insertable = true, updatable = true, length = 40, precision = 0)
    public String getFldCustomerId() {
        return fldCustomerId;
    }

    public void setFldCustomerId(String fldCustomerId) {
        this.fldCustomerId = fldCustomerId;
    }

    private String fldCustomerName;

    @Basic
    @javax.persistence.Column(name = "FLDCUSTOMERNAME", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    public String getFldCustomerName() {
        return fldCustomerName;
    }

    public void setFldCustomerName(String fldCustomerName) {
        this.fldCustomerName = fldCustomerName;
    }

    private Integer fldGender;

    @Basic
    @javax.persistence.Column(name = "FLDGENDER", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFldGender() {
        return fldGender;
    }

    public void setFldGender(Integer fldGender) {
        this.fldGender = fldGender;
    }

    private String fldMobile;

    @Basic
    @javax.persistence.Column(name = "FLDMOBILE", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    public String getFldMobile() {
        return fldMobile;
    }

    public void setFldMobile(String fldMobile) {
        this.fldMobile = fldMobile;
    }

    private String fldPhone;

    @Basic
    @javax.persistence.Column(name = "FLDPHONE", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    public String getFldPhone() {
        return fldPhone;
    }

    public void setFldPhone(String fldPhone) {
        this.fldPhone = fldPhone;
    }

    private String fldAddress;

    @Basic
    @javax.persistence.Column(name = "FLDADDRESS", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getFldAddress() {
        return fldAddress;
    }

    public void setFldAddress(String fldAddress) {
        this.fldAddress = fldAddress;
    }

    private String fldComment;

    @Basic
    @javax.persistence.Column(name = "FLDCOMMENT", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getFldComment() {
        return fldComment;
    }

    public void setFldComment(String fldComment) {
        this.fldComment = fldComment;
    }

    private Integer fldDuplicateStatus;

    @Basic
    @javax.persistence.Column(name = "FLDDUPLICATESTATUS", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFldDuplicateStatus() {
        return fldDuplicateStatus;
    }

    public void setFldDuplicateStatus(Integer fldDuplicateStatus) {
        this.fldDuplicateStatus = fldDuplicateStatus;
    }

    private String fldFinancialUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDFINANCIALUSERNO", nullable = true, insertable = true, updatable = true, length = 32, precision = 0)
    public String getFldFinancialUserNo() {
        return fldFinancialUserNo;
    }

    public void setFldFinancialUserNo(String fldFinancialUserNo) {
        this.fldFinancialUserNo = fldFinancialUserNo;
    }

    private String fldCallUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDCALLUSERNO", nullable = true, insertable = true, updatable = true, length = 40, precision = 0)
    public String getFldCallUserNo() {
        return fldCallUserNo;
    }

    public void setFldCallUserNo(String fldCallUserNo) {
        this.fldCallUserNo = fldCallUserNo;
    }

    private String fldOperateUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDOPERATEUSERNO", nullable = true, insertable = true, updatable = true, length = 32, precision = 0)
    public String getFldOperateUserNo() {
        return fldOperateUserNo;
    }

    public void setFldOperateUserNo(String fldOperateUserNo) {
        this.fldOperateUserNo = fldOperateUserNo;
    }

    private Date fldOperateDate;

    @Basic
    @javax.persistence.Column(name = "FLDOPERATEDATE", nullable = true, insertable = true, updatable = true, length = 23, precision = 3)
    public Date getFldOperateDate() {
        return fldOperateDate;
    }

    public void setFldOperateDate(Date fldOperateDate) {
        this.fldOperateDate = fldOperateDate;
    }

    private String fldCreateUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDCREATEUSERNO", nullable = true, insertable = true, updatable = true, length = 32, precision = 0)
    public String getFldCreateUserNo() {
        return fldCreateUserNo;
    }

    public void setFldCreateUserNo(String fldCreateUserNo) {
        this.fldCreateUserNo = fldCreateUserNo;
    }

    private Date fldCreateDate;

    @Basic
    @javax.persistence.Column(name = "FLDCREATEDATE", nullable = true, insertable = true, updatable = true, length = 23, precision = 3)
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

        TelephoneImportTemp that = (TelephoneImportTemp) o;

        if (fldAddress != null ? !fldAddress.equals(that.fldAddress) : that.fldAddress != null) return false;
        if (fldBatchNo != null ? !fldBatchNo.equals(that.fldBatchNo) : that.fldBatchNo != null) return false;
        if (fldCallUserNo != null ? !fldCallUserNo.equals(that.fldCallUserNo) : that.fldCallUserNo != null)
            return false;
        if (fldComment != null ? !fldComment.equals(that.fldComment) : that.fldComment != null) return false;
        if (fldCreateDate != null ? !fldCreateDate.equals(that.fldCreateDate) : that.fldCreateDate != null)
            return false;
        if (fldCreateUserNo != null ? !fldCreateUserNo.equals(that.fldCreateUserNo) : that.fldCreateUserNo != null)
            return false;
        if (fldCustomerId != null ? !fldCustomerId.equals(that.fldCustomerId) : that.fldCustomerId != null)
            return false;
        if (fldCustomerName != null ? !fldCustomerName.equals(that.fldCustomerName) : that.fldCustomerName != null)
            return false;
        if (fldDuplicateStatus != null ? !fldDuplicateStatus.equals(that.fldDuplicateStatus) : that.fldDuplicateStatus != null)
            return false;
        if (fldFinancialUserNo != null ? !fldFinancialUserNo.equals(that.fldFinancialUserNo) : that.fldFinancialUserNo != null)
            return false;
        if (fldGender != null ? !fldGender.equals(that.fldGender) : that.fldGender != null) return false;
        if (fldId != null ? !fldId.equals(that.fldId) : that.fldId != null) return false;
        if (fldMobile != null ? !fldMobile.equals(that.fldMobile) : that.fldMobile != null) return false;
        if (fldOperateDate != null ? !fldOperateDate.equals(that.fldOperateDate) : that.fldOperateDate != null)
            return false;
        if (fldOperateUserNo != null ? !fldOperateUserNo.equals(that.fldOperateUserNo) : that.fldOperateUserNo != null)
            return false;
        if (fldPhone != null ? !fldPhone.equals(that.fldPhone) : that.fldPhone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fldId != null ? fldId.hashCode() : 0;
        result = 31 * result + (fldBatchNo != null ? fldBatchNo.hashCode() : 0);
        result = 31 * result + (fldCustomerId != null ? fldCustomerId.hashCode() : 0);
        result = 31 * result + (fldCustomerName != null ? fldCustomerName.hashCode() : 0);
        result = 31 * result + (fldGender != null ? fldGender.hashCode() : 0);
        result = 31 * result + (fldMobile != null ? fldMobile.hashCode() : 0);
        result = 31 * result + (fldPhone != null ? fldPhone.hashCode() : 0);
        result = 31 * result + (fldAddress != null ? fldAddress.hashCode() : 0);
        result = 31 * result + (fldComment != null ? fldComment.hashCode() : 0);
        result = 31 * result + (fldDuplicateStatus != null ? fldDuplicateStatus.hashCode() : 0);
        result = 31 * result + (fldFinancialUserNo != null ? fldFinancialUserNo.hashCode() : 0);
        result = 31 * result + (fldCallUserNo != null ? fldCallUserNo.hashCode() : 0);
        result = 31 * result + (fldOperateUserNo != null ? fldOperateUserNo.hashCode() : 0);
        result = 31 * result + (fldOperateDate != null ? fldOperateDate.hashCode() : 0);
        result = 31 * result + (fldCreateUserNo != null ? fldCreateUserNo.hashCode() : 0);
        result = 31 * result + (fldCreateDate != null ? fldCreateDate.hashCode() : 0);
        return result;
    }
}
