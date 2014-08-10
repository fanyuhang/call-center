package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * TelephoneImport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEIMPORT")
public class TelephoneImport implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldName;
	private Integer fldDuplicateStatus;
	private String fldUploadFilePath;
	private Integer fldTotalNumber;
	private Integer fldDuplicateTotalNumber;
	private String fldDuplicateFilePath;
	private Integer fldImportTotalNumber;
	private String fldImportFilePath;
	private Integer fldAssignTotalNumber;
	private String fldComment;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;
	
	private Integer unAssignTotalNumber;

	// Constructors

	/** default constructor */
	public TelephoneImport() {
	}

	/** minimal constructor */
	public TelephoneImport(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneImport(String fldId, String fldName,
			Integer fldDuplicateStatus, String fldUploadFilePath,
			Integer fldTotalNumber, Integer fldDuplicateTotalNumber,
			String fldDuplicateFilePath, Integer fldImportTotalNumber,
			String fldImportFilePath, Integer fldAssignTotalNumber,
			String fldComment, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldName = fldName;
		this.fldDuplicateStatus = fldDuplicateStatus;
		this.fldUploadFilePath = fldUploadFilePath;
		this.fldTotalNumber = fldTotalNumber;
		this.fldDuplicateTotalNumber = fldDuplicateTotalNumber;
		this.fldDuplicateFilePath = fldDuplicateFilePath;
		this.fldImportTotalNumber = fldImportTotalNumber;
		this.fldImportFilePath = fldImportFilePath;
		this.fldAssignTotalNumber = fldAssignTotalNumber;
		this.fldComment = fldComment;
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

	@Column(name = "FLDNAME")
	public String getFldName() {
		return this.fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	@Column(name = "FLDDUPLICATESTATUS")
	public Integer getFldDuplicateStatus() {
		return this.fldDuplicateStatus;
	}

	public void setFldDuplicateStatus(Integer fldDuplicateStatus) {
		this.fldDuplicateStatus = fldDuplicateStatus;
	}

	@Column(name = "FLDUPLOADFILEPATH")
	public String getFldUploadFilePath() {
		return this.fldUploadFilePath;
	}

	public void setFldUploadFilePath(String fldUploadFilePath) {
		this.fldUploadFilePath = fldUploadFilePath;
	}

	@Column(name = "FLDTOTALNUMBER")
	public Integer getFldTotalNumber() {
		return this.fldTotalNumber;
	}

	public void setFldTotalNumber(Integer fldTotalNumber) {
		this.fldTotalNumber = fldTotalNumber;
	}

	@Column(name = "FLDDUPLICATETOTALNUMBER")
	public Integer getFldDuplicateTotalNumber() {
		return this.fldDuplicateTotalNumber;
	}

	public void setFldDuplicateTotalNumber(Integer fldDuplicateTotalNumber) {
		this.fldDuplicateTotalNumber = fldDuplicateTotalNumber;
	}

	@Column(name = "FLDDUPLICATEFILEPATH")
	public String getFldDuplicateFilePath() {
		return this.fldDuplicateFilePath;
	}

	public void setFldDuplicateFilePath(String fldDuplicateFilePath) {
		this.fldDuplicateFilePath = fldDuplicateFilePath;
	}

	@Column(name = "FLDIMPORTTOTALNUMBER")
	public Integer getFldImportTotalNumber() {
		return this.fldImportTotalNumber;
	}

	public void setFldImportTotalNumber(Integer fldImportTotalNumber) {
		this.fldImportTotalNumber = fldImportTotalNumber;
	}

	@Column(name = "FLDIMPORTFILEPATH")
	public String getFldImportFilePath() {
		return this.fldImportFilePath;
	}

	public void setFldImportFilePath(String fldImportFilePath) {
		this.fldImportFilePath = fldImportFilePath;
	}

	@Column(name = "FLDASSIGNTOTALNUMBER")
	public Integer getFldAssignTotalNumber() {
		return this.fldAssignTotalNumber;
	}

	public void setFldAssignTotalNumber(Integer fldAssignTotalNumber) {
		this.fldAssignTotalNumber = fldAssignTotalNumber;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
	}

	@Column(name = "FLDOPERATEUSERNO")
	public String getFldOperateUserNo() {
		return this.fldOperateUserNo;
	}

	public void setFldOperateUserNo(String fldOperateUserNo) {
		this.fldOperateUserNo = fldOperateUserNo;
	}

	@Column(name = "FLDOPERATEDATE")
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Date getFldCreateDate() {
		return this.fldCreateDate;
	}

	public void setFldCreateDate(Date fldCreateDate) {
		this.fldCreateDate = fldCreateDate;
	}

	protected User operateUser;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOPERATEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}
	
	@Transient
	public String getOperateUserName() {
		return operateUser!=null ? operateUser.getUserName() : "";
	}

    private User createUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCREATEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @Transient
    public String getCreateUserName() {
        return createUser!=null ? createUser.getUserName() : "";
    }

    @Transient
	public Integer getUnAssignTotalNumber() {
		return unAssignTotalNumber;
	}

	public void setUnAssignTotalNumber(Integer unAssignTotalNumber) {
		this.unAssignTotalNumber = unAssignTotalNumber;
	}
}