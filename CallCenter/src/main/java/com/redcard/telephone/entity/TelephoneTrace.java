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
@javax.persistence.Table(name = "TBLTELEPHONETRACE")
public class TelephoneTrace {
    private Long fldId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "FLDID")
    public Long getFldId() {
        return fldId;
    }

    public void setFldId(Long fldId) {
        this.fldId = fldId;
    }

    private Long fldTaskId;

    @Basic
    @javax.persistence.Column(name = "FLDTASKID")
    public Long getFldTaskId() {
        return fldTaskId;
    }

    public void setFldTaskId(Long fldTaskId) {
        this.fldTaskId = fldTaskId;
    }

    private String fldCustomerId;

    @Basic
    @javax.persistence.Column(name = "FLDCUSTOMERID")
    public String getFldCustomerId() {
        return fldCustomerId;
    }

    public void setFldCustomerId(String fldCustomerId) {
        this.fldCustomerId = fldCustomerId;
    }

    private String fldImportId;

    @Basic
    @javax.persistence.Column(name = "FLDIMPORTID")
    public String getFldImportId() {
        return fldImportId;
    }

    public void setFldImportId(String fldImportId) {
        this.fldImportId = fldImportId;
    }

    private String fldCallUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDCALLUSERNO")
    public String getFldCallUserNo() {
        return fldCallUserNo;
    }

    public void setFldCallUserNo(String fldCallUserNo) {
        this.fldCallUserNo = fldCallUserNo;
    }

    private String fldCustomerName;

    @Basic
    @javax.persistence.Column(name = "FLDCUSTOMERNAME")
    public String getFldCustomerName() {
        return fldCustomerName;
    }

    public void setFldCustomerName(String fldCustomerName) {
        this.fldCustomerName = fldCustomerName;
    }

    private String fldMobile;

    @Basic
    @javax.persistence.Column(name = "FLDMOBILE")
    public String getFldMobile() {
        return fldMobile;
    }

    public void setFldMobile(String fldMobile) {
        this.fldMobile = fldMobile;
    }

    private String fldPhone;

    @Basic
    @javax.persistence.Column(name = "FLDPHONE")
    public String getFldPhone() {
        return fldPhone;
    }

    public void setFldPhone(String fldPhone) {
        this.fldPhone = fldPhone;
    }

    private String fldComment;

    @Basic
    @javax.persistence.Column(name = "FLDCOMMENT")
    public String getFldComment() {
        return fldComment;
    }

    public void setFldComment(String fldComment) {
        this.fldComment = fldComment;
    }

    private Integer fldStatus;

    @Basic
    @javax.persistence.Column(name = "FLDSTATUS")
    public Integer getFldStatus() {
        return fldStatus;
    }

    public void setFldStatus(Integer fldStatus) {
        this.fldStatus = fldStatus;
    }

    private Integer fldResultStatus;

    @Basic
    @javax.persistence.Column(name = "FLDRESULTSTATUS")
    public Integer getFldResultStatus() {
        return fldResultStatus;
    }

    public void setFldResultStatus(Integer fldResultStatus) {
        this.fldResultStatus = fldResultStatus;
    }

    private String fldFinancialUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDFINANCIALUSERNO")
    public String getFldFinancialUserNo() {
        return fldFinancialUserNo;
    }

    public void setFldFinancialUserNo(String fldFinancialUserNo) {
        this.fldFinancialUserNo = fldFinancialUserNo;
    }

    private String fldServiceUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDSERVICEUSERNO")
    public String getFldServiceUserNo() {
        return fldServiceUserNo;
    }

    public void setFldServiceUserNo(String fldServiceUserNo) {
        this.fldServiceUserNo = fldServiceUserNo;
    }

    private String fldAuditUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDAUDITUSERNO")
    public String getFldAuditUserNo() {
        return fldAuditUserNo;
    }

    public void setFldAuditUserNo(String fldAuditUserNo) {
        this.fldAuditUserNo = fldAuditUserNo;
    }

    private Date fldAuditDate;

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @javax.persistence.Column(name = "FLDAUDITDATE")
    public Date getFldAuditDate() {
        return fldAuditDate;
    }

    public void setFldAuditDate(Date fldAuditDate) {
        this.fldAuditDate = fldAuditDate;
    }

    private Date fldFinishDate;

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @javax.persistence.Column(name = "FLDFINISHDATE")
    public Date getFldFinishDate() {
        return fldFinishDate;
    }

    public void setFldFinishDate(Date fldFinishDate) {
        this.fldFinishDate = fldFinishDate;
    }

    private Integer fldAssignStatus;

    @Basic
    @javax.persistence.Column(name = "FLDASSIGNSTATUS")
    public Integer getFldAssignStatus() {
        return fldAssignStatus;
    }

    public void setFldAssignStatus(Integer fldAssignStatus) {
        this.fldAssignStatus = fldAssignStatus;
    }

    private String fldAssignUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDASSIGNUSERNO")
    public String getFldAssignUserNo() {
        return fldAssignUserNo;
    }

    public void setFldAssignUserNo(String fldAssignUserNo) {
        this.fldAssignUserNo = fldAssignUserNo;
    }

    private Date fldAssignDate;

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @javax.persistence.Column(name = "FLDASSIGNDATE")
    public Date getFldAssignDate() {
        return fldAssignDate;
    }

    public void setFldAssignDate(Date fldAssignDate) {
        this.fldAssignDate = fldAssignDate;
    }

    private Integer fldAuditStatus;

    @Basic
    @javax.persistence.Column(name = "FLDAUDITSTATUS")
    public Integer getFldAuditStatus() {
        return fldAuditStatus;
    }

    public void setFldAuditStatus(Integer fldAuditStatus) {
        this.fldAuditStatus = fldAuditStatus;
    }

    private String fldOperateUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDOPERATEUSERNO")
    public String getFldOperateUserNo() {
        return fldOperateUserNo;
    }

    public void setFldOperateUserNo(String fldOperateUserNo) {
        this.fldOperateUserNo = fldOperateUserNo;
    }

    private Date fldOperateDate;

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @javax.persistence.Column(name = "FLDOPERATEDATE")
    public Date getFldOperateDate() {
        return fldOperateDate;
    }

    public void setFldOperateDate(Date fldOperateDate) {
        this.fldOperateDate = fldOperateDate;
    }

    private String fldCreateUserNo;

    @Basic
    @javax.persistence.Column(name = "FLDCREATEUSERNO")
    public String getFldCreateUserNo() {
        return fldCreateUserNo;
    }

    public void setFldCreateUserNo(String fldCreateUserNo) {
        this.fldCreateUserNo = fldCreateUserNo;
    }

    private Date fldCreateDate;

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @javax.persistence.Column(name = "FLDCREATEDATE")
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

    private User createUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCREATEUSERNO", referencedColumnName = "FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @Transient
    public String getCreateName() {
        if (createUser != null) {
            return createUser.getUserName();
        }
        return "";
    }

    private User assignUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDASSIGNUSERNO", referencedColumnName = "FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(User assignUser) {
        this.assignUser = assignUser;
    }

    @Transient
    public String getAssignName() {
        if (assignUser != null) {
            return assignUser.getUserName();
        }
        return "";
    }

    private User auditUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDAUDITUSERNO", referencedColumnName = "FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    @Transient
    public String getAuditName() {
        if (auditUser != null) {
            return auditUser.getUserName();
        }
        return "";
    }

    private User financialUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDFINANCIALUSERNO", referencedColumnName = "FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getFinancialUser() {
        return financialUser;
    }

    public void setFinancialUser(User financialUser) {
        this.financialUser = financialUser;
    }

    @Transient
    public String getFinancialName() {
        if (financialUser != null) {
            return financialUser.getUserName();
        }
        return "";
    }

    private TelephoneImport telephoneImport;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDIMPORTID", nullable = true, insertable = false, updatable = false)
    public TelephoneImport getTelephoneImport() {
        return telephoneImport;
    }

    public void setTelephoneImport(TelephoneImport telephoneImport) {
        this.telephoneImport = telephoneImport;
    }

    @Transient
    public String getImportName() {
        return telephoneImport == null ? "" : telephoneImport.getFldName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelephoneTrace that = (TelephoneTrace) o;

        if (fldAssignDate != null ? !fldAssignDate.equals(that.fldAssignDate) : that.fldAssignDate != null)
            return false;
        if (fldAuditDate != null ? !fldAuditDate.equals(that.fldAuditDate) : that.fldAuditDate != null) return false;
        if (fldAuditUserNo != null ? !fldAuditUserNo.equals(that.fldAuditUserNo) : that.fldAuditUserNo != null)
            return false;
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
        if (fldFinancialUserNo != null ? !fldFinancialUserNo.equals(that.fldFinancialUserNo) : that.fldFinancialUserNo != null)
            return false;
        if (fldFinishDate != null ? !fldFinishDate.equals(that.fldFinishDate) : that.fldFinishDate != null)
            return false;
        if (fldId != null ? !fldId.equals(that.fldId) : that.fldId != null) return false;
        if (fldImportId != null ? !fldImportId.equals(that.fldImportId) : that.fldImportId != null) return false;
        if (fldMobile != null ? !fldMobile.equals(that.fldMobile) : that.fldMobile != null) return false;
        if (fldOperateDate != null ? !fldOperateDate.equals(that.fldOperateDate) : that.fldOperateDate != null)
            return false;
        if (fldOperateUserNo != null ? !fldOperateUserNo.equals(that.fldOperateUserNo) : that.fldOperateUserNo != null)
            return false;
        if (fldPhone != null ? !fldPhone.equals(that.fldPhone) : that.fldPhone != null) return false;
        if (fldResultStatus != null ? !fldResultStatus.equals(that.fldResultStatus) : that.fldResultStatus != null)
            return false;
        if (fldServiceUserNo != null ? !fldServiceUserNo.equals(that.fldServiceUserNo) : that.fldServiceUserNo != null)
            return false;
        if (fldStatus != null ? !fldStatus.equals(that.fldStatus) : that.fldStatus != null) return false;
        if (fldTaskId != null ? !fldTaskId.equals(that.fldTaskId) : that.fldTaskId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fldId != null ? fldId.hashCode() : 0;
        result = 31 * result + (fldTaskId != null ? fldTaskId.hashCode() : 0);
        result = 31 * result + (fldCustomerId != null ? fldCustomerId.hashCode() : 0);
        result = 31 * result + (fldImportId != null ? fldImportId.hashCode() : 0);
        result = 31 * result + (fldCallUserNo != null ? fldCallUserNo.hashCode() : 0);
        result = 31 * result + (fldCustomerName != null ? fldCustomerName.hashCode() : 0);
        result = 31 * result + (fldMobile != null ? fldMobile.hashCode() : 0);
        result = 31 * result + (fldPhone != null ? fldPhone.hashCode() : 0);
        result = 31 * result + (fldComment != null ? fldComment.hashCode() : 0);
        result = 31 * result + (fldStatus != null ? fldStatus.hashCode() : 0);
        result = 31 * result + (fldResultStatus != null ? fldResultStatus.hashCode() : 0);
        result = 31 * result + (fldFinancialUserNo != null ? fldFinancialUserNo.hashCode() : 0);
        result = 31 * result + (fldServiceUserNo != null ? fldServiceUserNo.hashCode() : 0);
        result = 31 * result + (fldAuditUserNo != null ? fldAuditUserNo.hashCode() : 0);
        result = 31 * result + (fldAuditDate != null ? fldAuditDate.hashCode() : 0);
        result = 31 * result + (fldFinishDate != null ? fldFinishDate.hashCode() : 0);
        result = 31 * result + (fldAssignDate != null ? fldAssignDate.hashCode() : 0);
        result = 31 * result + (fldOperateUserNo != null ? fldOperateUserNo.hashCode() : 0);
        result = 31 * result + (fldOperateDate != null ? fldOperateDate.hashCode() : 0);
        result = 31 * result + (fldCreateUserNo != null ? fldCreateUserNo.hashCode() : 0);
        result = 31 * result + (fldCreateDate != null ? fldCreateDate.hashCode() : 0);
        return result;
    }
}
