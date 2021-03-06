package com.redcard.telephone.entity;

import com.common.core.util.JsonDateSerializer;
import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * TelephoneTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONETASK")
public class TelephoneTask implements java.io.Serializable {

    // Fields

    private Long fldId;
    private String fldCustomerId;
    private String fldAssignDetailId;
    private String fldCallUserNo;
    private String fldCustomerName;
    private Date fldAssignDate;
    private Date fldTaskDate;
    private Date fldCallDate;
    private Integer fldTaskType;
    private Integer fldCallStatus;
    private Integer fldTaskStatus;
    private Integer fldContentType;
    private Integer fldResultType;
    private String fldComment;
    private Integer fldAuditStatus;
    private String fldAuditComment;
    private Integer fldAuditFraction;
    private String fldAuditUserNo;
    private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
    private Date fldOperateDate;
    private String fldCreateUserNo;
    private Date fldCreateDate;
    private String fldImportId;

    // Constructors

    /**
     * default constructor
     */
    public TelephoneTask() {
    }

    /**
     * minimal constructor
     */
    public TelephoneTask(Long fldId) {
        this.fldId = fldId;
    }

    /**
     * full constructor
     */
    public TelephoneTask(Long fldId, String fldCustomerId,
                         String fldAssignDetailId, String fldCallUserNo,
                         String fldCustomerName, Date fldAssignDate,
                         Date fldTaskDate, Date fldCallDate, Integer fldTaskType,
                         Integer fldCallStatus, Integer fldTaskStatus,
                         Integer fldContentType, Integer fldResultType, String fldComment,
                         Integer fldAuditStatus, String fldAuditComment,
                         Integer fldAuditFraction, String fldAuditUserNo,
                         String fldOperateUserNo, Date fldOperateDate,
                         String fldCreateUserNo, Date fldCreateDate) {
        this.fldId = fldId;
        this.fldCustomerId = fldCustomerId;
        this.fldAssignDetailId = fldAssignDetailId;
        this.fldCallUserNo = fldCallUserNo;
        this.fldCustomerName = fldCustomerName;
        this.fldAssignDate = fldAssignDate;
        this.fldTaskDate = fldTaskDate;
        this.fldCallDate = fldCallDate;
        this.fldTaskType = fldTaskType;
        this.fldCallStatus = fldCallStatus;
        this.fldTaskStatus = fldTaskStatus;
        this.fldContentType = fldContentType;
        this.fldResultType = fldResultType;
        this.fldComment = fldComment;
        this.fldAuditStatus = fldAuditStatus;
        this.fldAuditComment = fldAuditComment;
        this.fldAuditFraction = fldAuditFraction;
        this.fldAuditUserNo = fldAuditUserNo;
        this.fldOperateUserNo = fldOperateUserNo;
        this.fldOperateDate = fldOperateDate;
        this.fldCreateUserNo = fldCreateUserNo;
        this.fldCreateDate = fldCreateDate;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLDID", unique = true, nullable = false)
    public Long getFldId() {
        return this.fldId;
    }

    public void setFldId(Long fldId) {
        this.fldId = fldId;
    }

    @Column(name = "FLDCUSTOMERID")
    public String getFldCustomerId() {
        return this.fldCustomerId;
    }

    public void setFldCustomerId(String fldCustomerId) {
        this.fldCustomerId = fldCustomerId;
    }

    @Column(name = "FLDASSIGNDETAILID")
    public String getFldAssignDetailId() {
        return this.fldAssignDetailId;
    }

    public void setFldAssignDetailId(String fldAssignDetailId) {
        this.fldAssignDetailId = fldAssignDetailId;
    }

    @Column(name = "FLDCALLUSERNO")
    public String getFldCallUserNo() {
        return this.fldCallUserNo;
    }

    public void setFldCallUserNo(String fldCallUserNo) {
        this.fldCallUserNo = fldCallUserNo;
    }

    @Column(name = "FLDCUSTOMERNAME")
    public String getFldCustomerName() {
        return this.fldCustomerName;
    }

    public void setFldCustomerName(String fldCustomerName) {
        this.fldCustomerName = fldCustomerName;
    }

    @Column(name = "FLDASSIGNDATE")
    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getFldAssignDate() {
        return this.fldAssignDate;
    }

    public void setFldAssignDate(Date fldAssignDate) {
        this.fldAssignDate = fldAssignDate;
    }

    @Column(name = "FLDTASKDATE")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFldTaskDate() {
        return this.fldTaskDate;
    }

    public void setFldTaskDate(Date fldTaskDate) {
        this.fldTaskDate = fldTaskDate;
    }

    @Column(name = "FLDCALLDATE")
    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getFldCallDate() {
        return this.fldCallDate;
    }

    public void setFldCallDate(Date fldCallDate) {
        this.fldCallDate = fldCallDate;
    }

    @Column(name = "FLDTASKTYPE")
    public Integer getFldTaskType() {
        return this.fldTaskType;
    }

    public void setFldTaskType(Integer fldTaskType) {
        this.fldTaskType = fldTaskType;
    }

    @Column(name = "FLDCALLSTATUS")
    public Integer getFldCallStatus() {
        return this.fldCallStatus;
    }

    public void setFldCallStatus(Integer fldCallStatus) {
        this.fldCallStatus = fldCallStatus;
    }

    @Column(name = "FLDTASKSTATUS")
    public Integer getFldTaskStatus() {
        return this.fldTaskStatus;
    }

    public void setFldTaskStatus(Integer fldTaskStatus) {
        this.fldTaskStatus = fldTaskStatus;
    }

    @Column(name = "FLDCONTENTTYPE")
    public Integer getFldContentType() {
        return this.fldContentType;
    }

    public void setFldContentType(Integer fldContentType) {
        this.fldContentType = fldContentType;
    }

    @Column(name = "FLDRESULTTYPE")
    public Integer getFldResultType() {
        return this.fldResultType;
    }

    public void setFldResultType(Integer fldResultType) {
        this.fldResultType = fldResultType;
    }

    @Column(name = "FLDCOMMENT")
    public String getFldComment() {
        return this.fldComment;
    }

    public void setFldComment(String fldComment) {
        this.fldComment = fldComment;
    }

    @Column(name = "FLDAUDITSTATUS")
    public Integer getFldAuditStatus() {
        return this.fldAuditStatus;
    }

    public void setFldAuditStatus(Integer fldAuditStatus) {
        this.fldAuditStatus = fldAuditStatus;
    }

    @Column(name = "FLDAUDITCOMMENT")
    public String getFldAuditComment() {
        return this.fldAuditComment;
    }

    public void setFldAuditComment(String fldAuditComment) {
        this.fldAuditComment = fldAuditComment;
    }

    @Column(name = "FLDAUDITFRACTION")
    public Integer getFldAuditFraction() {
        return this.fldAuditFraction;
    }

    public void setFldAuditFraction(Integer fldAuditFraction) {
        this.fldAuditFraction = fldAuditFraction;
    }

    @Column(name = "FLDAUDITUSERNO")
    public String getFldAuditUserNo() {
        return this.fldAuditUserNo;
    }

    public void setFldAuditUserNo(String fldAuditUserNo) {
        this.fldAuditUserNo = fldAuditUserNo;
    }

    @Column(name = "FLDOPERATEUSERNO")
    public String getFldOperateUserNo() {
        return this.fldOperateUserNo;
    }

    public void setFldOperateUserNo(String fldOperateUserNo) {
        this.fldOperateUserNo = fldOperateUserNo;
    }

    @Column(name = "FLDOPERATEDATE")
    public Date getFldOperateDate() {
        return this.fldOperateDate;
    }

    public void setFldOperateDate(Date fldOperateDate) {
        this.fldOperateDate = fldOperateDate;
    }

    @Column(name = "FLDCREATEUSERNO")
    public String getFldCreateUserNo() {
        return this.fldCreateUserNo;
    }

    public void setFldCreateUserNo(String fldCreateUserNo) {
        this.fldCreateUserNo = fldCreateUserNo;
    }

    @Column(name = "FLDCREATEDATE")
    public Date getFldCreateDate() {
        return this.fldCreateDate;
    }

    public void setFldCreateDate(Date fldCreateDate) {
        this.fldCreateDate = fldCreateDate;
    }

    protected User callUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCALLUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
    public User getCallUser() {
        return callUser;
    }

    public void setCallUser(User callUser) {
        this.callUser = callUser;
    }

    private String callUserName;

    @Transient
    public String getCallUserName() {
        return null != callUser ? callUser.getUserName() : "";
    }

    protected TelephoneImportDetail telephoneImportDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FLDCUSTOMERID", referencedColumnName = "FLDID", insertable = false, updatable = false)
    public TelephoneImportDetail getTelephoneImportDetail() {
        return telephoneImportDetail;
    }

    public void setTelephoneImportDetail(TelephoneImportDetail telephoneImportDetail) {
        this.telephoneImportDetail = telephoneImportDetail;
    }

    private String fldGender;

    @Transient
    public Integer getFldGender() {
        return null != telephoneImportDetail.getFldGender() ? telephoneImportDetail.getFldGender() : null;
    }

    private String fldMobile;

    @Transient
    public String getFldMobile() {
        return null != telephoneImportDetail.getFldMobile() ? telephoneImportDetail.getFldMobile() : "";
    }

    private String fldPhone;

    @Transient
    public String getFldPhone() {
        return null != telephoneImportDetail.getFldPhone() ? telephoneImportDetail.getFldPhone() : "";
    }

    @Column(name = "FLDIMPORTID")
    public String getFldImportId() {
        return fldImportId;
    }

    public void setFldImportId(String fldImportId) {
        this.fldImportId = fldImportId;
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

    private String importName;

    @Transient
    public String getImportName() {
        return telephoneImport == null ? "" : telephoneImport.getFldName();
    }

    private String fldTaskTypeLabel;

    @Transient
    public String getFldTaskTypeLabel() {
        return fldTaskTypeLabel;
    }

    public void setFldTaskTypeLabel(String fldTaskTypeLabel) {
        this.fldTaskTypeLabel = fldTaskTypeLabel;
    }

    private String fldTaskStatusLabel;

    @Transient
    public String getFldTaskStatusLabel() {
        return fldTaskStatusLabel;
    }

    public void setFldTaskStatusLabel(String fldTaskStatusLabel) {
        this.fldTaskStatusLabel = fldTaskStatusLabel;
    }

    private String fldCallStatusLabel;

    @Transient
    public String getFldCallStatusLabel() {
        return fldCallStatusLabel;
    }

    public void setFldCallStatusLabel(String fldCallStatusLabel) {
        this.fldCallStatusLabel = fldCallStatusLabel;
    }

    private String fldResultTypeLabel;

    @Transient
    public String getFldResultTypeLabel() {
        return fldResultTypeLabel;
    }

    public void setFldResultTypeLabel(String fldResultTypeLabel) {
        this.fldResultTypeLabel = fldResultTypeLabel;
    }

    private Integer fldRecoverStatus;
    private Date fldRecoverDate;
    private String fldRecoverUserNo;

    @Column(name = "FLDRECOVERSTATUS")
    public Integer getFldRecoverStatus() {
        return fldRecoverStatus;
    }

    public void setFldRecoverStatus(Integer fldRecoverStatus) {
        this.fldRecoverStatus = fldRecoverStatus;
    }

    @Column(name = "FLDRECOVERDATE")
    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getFldRecoverDate() {
        return fldRecoverDate;
    }

    public void setFldRecoverDate(Date fldRecoverDate) {
        this.fldRecoverDate = fldRecoverDate;
    }

    @Column(name = "FLDRECOVERUSERNO")
    public String getFldRecoverUserNo() {
        return fldRecoverUserNo;
    }

    public void setFldRecoverUserNo(String fldRecoverUserNo) {
        this.fldRecoverUserNo = fldRecoverUserNo;
    }

    private User recoverUserNo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDRECOVERUSERNO", referencedColumnName="FLDLOGINNAME", nullable = true, insertable = false, updatable = false)
    public User getRecoverUserNo() {
        return recoverUserNo;
    }

    public void setRecoverUserNo(User recoverUserNo) {
        this.recoverUserNo = recoverUserNo;
    }

    @Transient
    public String getRecoverUserName() {
        return recoverUserNo!=null ? recoverUserNo.getUserName() : "";
    }
}