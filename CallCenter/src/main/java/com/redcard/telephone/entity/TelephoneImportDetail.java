package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.common.security.util.SecurityUtil;

/**
 * TelephoneImportDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEIMPORTDETAIL")
public class TelephoneImportDetail implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldTelephoneId;
	private String fldImportId;
	private String fldCustomerName;
	private Integer fldGender;
	private String fldMobile;
	private String fldPhone;
	private String fldAddress;
	private Integer fldDuplicateStatus;
	private Integer fldAssignStatus;
	private String fldFinancialUserNo;
	private String fldCallUserNo;
	private Integer fldAssignNumber;
	private String fldComment;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public TelephoneImportDetail() {
	}

	/** minimal constructor */
	public TelephoneImportDetail(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneImportDetail(String fldId, String fldTelephoneId,
			String fldImportId, String fldCustomerName, Integer fldGender,
			String fldMobile, String fldPhone, String fldAddress,
			Integer fldDuplicateStatus, Integer fldAssignStatus,
			String fldFinancialUserNo, String fldCallUserNo,
			Integer fldAssignNumber, String fldComment,
			String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldTelephoneId = fldTelephoneId;
		this.fldImportId = fldImportId;
		this.fldCustomerName = fldCustomerName;
		this.fldGender = fldGender;
		this.fldMobile = fldMobile;
		this.fldPhone = fldPhone;
		this.fldAddress = fldAddress;
		this.fldDuplicateStatus = fldDuplicateStatus;
		this.fldAssignStatus = fldAssignStatus;
		this.fldFinancialUserNo = fldFinancialUserNo;
		this.fldCallUserNo = fldCallUserNo;
		this.fldAssignNumber = fldAssignNumber;
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

	@Column(name = "FLDTELEPHONEID")
	public String getFldTelephoneId() {
		return this.fldTelephoneId;
	}

	public void setFldTelephoneId(String fldTelephoneId) {
		this.fldTelephoneId = fldTelephoneId;
	}

	@Column(name = "FLDIMPORTID")
	public String getFldImportId() {
		return this.fldImportId;
	}

	public void setFldImportId(String fldImportId) {
		this.fldImportId = fldImportId;
	}

	@Column(name = "FLDCUSTOMERNAME")
	public String getFldCustomerName() {
		return this.fldCustomerName;
	}

	public void setFldCustomerName(String fldCustomerName) {
		this.fldCustomerName = fldCustomerName;
	}

	@Column(name = "FLDGENDER")
	public Integer getFldGender() {
		return this.fldGender;
	}

	public void setFldGender(Integer fldGender) {
		this.fldGender = fldGender;
	}

	@Column(name = "FLDMOBILE")
	public String getFldMobile() {
		return this.fldMobile;
	}

	public void setFldMobile(String fldMobile) {
		this.fldMobile = fldMobile;
	}

	@Column(name = "FLDPHONE")
	public String getFldPhone() {
		return this.fldPhone;
	}

	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}

	@Column(name = "FLDADDRESS")
	public String getFldAddress() {
		return this.fldAddress;
	}

	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}

	@Column(name = "FLDDUPLICATESTATUS")
	public Integer getFldDuplicateStatus() {
		return this.fldDuplicateStatus;
	}

	public void setFldDuplicateStatus(Integer fldDuplicateStatus) {
		this.fldDuplicateStatus = fldDuplicateStatus;
	}

	@Column(name = "FLDASSIGNSTATUS")
	public Integer getFldAssignStatus() {
		return this.fldAssignStatus;
	}

	public void setFldAssignStatus(Integer fldAssignStatus) {
		this.fldAssignStatus = fldAssignStatus;
	}

	@Column(name = "FLDFINANCIALUSERNO")
	public String getFldFinancialUserNo() {
		return this.fldFinancialUserNo;
	}

	public void setFldFinancialUserNo(String fldFinancialUserNo) {
		this.fldFinancialUserNo = fldFinancialUserNo;
	}

	@Column(name = "FLDCALLUSERNO")
	public String getFldCallUserNo() {
		return this.fldCallUserNo;
	}

	public void setFldCallUserNo(String fldCallUserNo) {
		this.fldCallUserNo = fldCallUserNo;
	}

	@Column(name = "FLDASSIGNNUMBER")
	public Integer getFldAssignNumber() {
		return this.fldAssignNumber;
	}

	public void setFldAssignNumber(Integer fldAssignNumber) {
		this.fldAssignNumber = fldAssignNumber;
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

}