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
 * TelephoneAssignDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEASSIGNDETAIL")
public class TelephoneAssignDetail implements java.io.Serializable {

    // Fields

    private String fldId;
    private String fldAssignId;
    private String fldCallUserNo;
    private String fldAssignUserNo;
    private Date fldAssignDate;
    private Date fldTaskDate;
    private Integer fldTaskNumber;
    private Integer fldFinishNumber;
    private Integer fldFollowNumber;
    private Integer fldExchangeNumber;
    private Integer fldFinishStatus;
    private Integer fldContentType;
    private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
    private Date fldOperateDate;
    private String fldCreateUserNo;
    private Date fldCreateDate;

    private Integer unFinishNumber;
    private Integer fldTaskType;
    private String fldImportId;

    // Constructors

    /**
     * default constructor
     */
    public TelephoneAssignDetail() {
    }

    /**
     * minimal constructor
     */
    public TelephoneAssignDetail(String fldId) {
        this.fldId = fldId;
    }

    /**
     * full constructor
     */
    public TelephoneAssignDetail(String fldId, String fldAssignId,
                                 String fldCallUserNo, String fldAssignUserNo,
                                 Date fldAssignDate, Date fldTaskDate,
                                 Integer fldTaskNumber, Integer fldFinishNumber,
                                 Integer fldFollowNumber, Integer fldExchangeNumber,
                                 Integer fldFinishStatus, Integer fldContentType,
                                 String fldOperateUserNo, Date fldOperateDate,
                                 String fldCreateUserNo, Date fldCreateDate) {
        this.fldId = fldId;
        this.fldAssignId = fldAssignId;
        this.fldCallUserNo = fldCallUserNo;
        this.fldAssignUserNo = fldAssignUserNo;
        this.fldAssignDate = fldAssignDate;
        this.fldTaskDate = fldTaskDate;
        this.fldTaskNumber = fldTaskNumber;
        this.fldFinishNumber = fldFinishNumber;
        this.fldFollowNumber = fldFollowNumber;
        this.fldExchangeNumber = fldExchangeNumber;
        this.fldFinishStatus = fldFinishStatus;
        this.fldContentType = fldContentType;
        this.fldOperateUserNo = fldOperateUserNo;
        this.fldOperateDate = fldOperateDate;
        this.fldCreateUserNo = fldCreateUserNo;
        this.fldCreateDate = fldCreateDate;
    }

    // Property accessors
    @Id
    @Column(name = "FLDID", unique = true, nullable = false)
    public String getFldId() {
        return this.fldId;
    }

    public void setFldId(String fldId) {
        this.fldId = fldId;
    }

    @Column(name = "FLDASSIGNID")
    public String getFldAssignId() {
        return this.fldAssignId;
    }

    public void setFldAssignId(String fldAssignId) {
        this.fldAssignId = fldAssignId;
    }

    @Column(name = "FLDCALLUSERNO")
    public String getFldCallUserNo() {
        return this.fldCallUserNo;
    }

    public void setFldCallUserNo(String fldCallUserNo) {
        this.fldCallUserNo = fldCallUserNo;
    }

    @Column(name = "FLDASSIGNUSERNO")
    public String getFldAssignUserNo() {
        return this.fldAssignUserNo;
    }

    public void setFldAssignUserNo(String fldAssignUserNo) {
        this.fldAssignUserNo = fldAssignUserNo;
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

    @Column(name = "FLDTASKNUMBER")
    public Integer getFldTaskNumber() {
        return this.fldTaskNumber;
    }

    public void setFldTaskNumber(Integer fldTaskNumber) {
        this.fldTaskNumber = fldTaskNumber;
    }

    @Column(name = "FLDFINISHNUMBER")
    public Integer getFldFinishNumber() {
        return this.fldFinishNumber;
    }

    public void setFldFinishNumber(Integer fldFinishNumber) {
        this.fldFinishNumber = fldFinishNumber;
    }

    @Column(name = "FLDFOLLOWNUMBER")
    public Integer getFldFollowNumber() {
        return this.fldFollowNumber;
    }

    public void setFldFollowNumber(Integer fldFollowNumber) {
        this.fldFollowNumber = fldFollowNumber;
    }

    @Column(name = "FLDEXCHANGENUMBER")
    public Integer getFldExchangeNumber() {
        return this.fldExchangeNumber;
    }

    public void setFldExchangeNumber(Integer fldExchangeNumber) {
        this.fldExchangeNumber = fldExchangeNumber;
    }

    @Column(name = "FLDFINISHSTATUS")
    public Integer getFldFinishStatus() {
        return this.fldFinishStatus;
    }

    public void setFldFinishStatus(Integer fldFinishStatus) {
        this.fldFinishStatus = fldFinishStatus;
    }

    @Column(name = "FLDCONTENTTYPE")
    public Integer getFldContentType() {
        return this.fldContentType;
    }

    public void setFldContentType(Integer fldContentType) {
        this.fldContentType = fldContentType;
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

    @Transient
    public String getCallUserName() {
        return null != callUser ? callUser.getUserName() : "";
    }

    protected User assignUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDASSIGNUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
    public User getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(User assignUser) {
        this.assignUser = assignUser;
    }

    @Transient
    public String getAssignUserName() {
        return null != assignUser ? assignUser.getUserName() : "";
    }

    @Transient
    public Integer getUnFinishNumber() {
        return unFinishNumber;
    }

    public void setUnFinishNumber(Integer unFinishNumber) {
        this.unFinishNumber = unFinishNumber;
    }

    @Column(name = "FLDTASKTYPE")
    public Integer getFldTaskType() {
        return fldTaskType;
    }

    public void setFldTaskType(Integer fldTaskType) {
        this.fldTaskType = fldTaskType;
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

    private String callUserName;

    private String assignUserName;

    private String taskTypeLabel;

    @Transient
    public String getTaskTypeLabel() {
        return taskTypeLabel;
    }

    public void setTaskTypeLabel(String taskTypeLabel) {
        this.taskTypeLabel = taskTypeLabel;
    }

    private String resultStatusLabel;

    @Transient
    public String getResultStatusLabel() {
        return resultStatusLabel;
    }

    public void setResultStatusLabel(String resultStatusLabel) {
        this.resultStatusLabel = resultStatusLabel;
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