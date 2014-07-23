package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TelephoneAssign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEASSIGN")
public class TelephoneAssign implements java.io.Serializable {

	// Fields

	private String fldId;
	private Integer fldSource;
	private Integer fldAssignNumber;
	private Integer fldCallUserNumber;
	private Integer fldAverageNumber;
	private Integer fldDayNumber;
	private Date fldBeginDate;
	private Date fldEndDate;
	private Integer fldContentType;
	private String fldComment;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public TelephoneAssign() {
	}

	/** minimal constructor */
	public TelephoneAssign(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneAssign(String fldId, Integer fldSource,
			Integer fldAssignNumber, Integer fldCallUserNumber,
			Integer fldAverageNumber, Integer fldDayNumber,
			Date fldBeginDate, Date fldEndDate,
			Integer fldContentType, String fldComment, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldSource = fldSource;
		this.fldAssignNumber = fldAssignNumber;
		this.fldCallUserNumber = fldCallUserNumber;
		this.fldAverageNumber = fldAverageNumber;
		this.fldDayNumber = fldDayNumber;
		this.fldBeginDate = fldBeginDate;
		this.fldEndDate = fldEndDate;
		this.fldContentType = fldContentType;
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

	@Column(name = "FLDSOURCE")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "FLDASSIGNNUMBER")
	public Integer getFldAssignNumber() {
		return this.fldAssignNumber;
	}

	public void setFldAssignNumber(Integer fldAssignNumber) {
		this.fldAssignNumber = fldAssignNumber;
	}

	@Column(name = "FLDCALLUSERNUMBER")
	public Integer getFldCallUserNumber() {
		return this.fldCallUserNumber;
	}

	public void setFldCallUserNumber(Integer fldCallUserNumber) {
		this.fldCallUserNumber = fldCallUserNumber;
	}

	@Column(name = "FLDAVERAGENUMBER")
	public Integer getFldAverageNumber() {
		return this.fldAverageNumber;
	}

	public void setFldAverageNumber(Integer fldAverageNumber) {
		this.fldAverageNumber = fldAverageNumber;
	}

	@Column(name = "FLDDAYNUMBER")
	public Integer getFldDayNumber() {
		return this.fldDayNumber;
	}

	public void setFldDayNumber(Integer fldDayNumber) {
		this.fldDayNumber = fldDayNumber;
	}

	@Column(name = "FLDBEGINDATE")
	public Date getFldBeginDate() {
		return this.fldBeginDate;
	}

	public void setFldBeginDate(Date fldBeginDate) {
		this.fldBeginDate = fldBeginDate;
	}

	@Column(name = "FLDENDDATE")
	public Date getFldEndDate() {
		return this.fldEndDate;
	}

	public void setFldEndDate(Date fldEndDate) {
		this.fldEndDate = fldEndDate;
	}

	@Column(name = "FLDCONTENTTYPE")
	public Integer getFldContentType() {
		return this.fldContentType;
	}

	public void setFldContentType(Integer fldContentType) {
		this.fldContentType = fldContentType;
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