package com.redcard.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CustomerProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMERPRODUCT")
public class CustomerProduct implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldFullName;
	private String fldShortName;
	private String fldDescription;
	private Date fldEstablishDate;
	private Date fldValueDate;
	private String fldComment;
	private Integer fldStatus;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public CustomerProduct() {
	}

	/** minimal constructor */
	public CustomerProduct(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public CustomerProduct(String fldId, String fldFullName,
			String fldShortName, String fldDescription,
			Date fldEstablishDate, Date fldValueDate,
			String fldComment, Integer fldStatus, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldFullName = fldFullName;
		this.fldShortName = fldShortName;
		this.fldDescription = fldDescription;
		this.fldEstablishDate = fldEstablishDate;
		this.fldValueDate = fldValueDate;
		this.fldComment = fldComment;
		this.fldStatus = fldStatus;
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

	@Column(name = "FLDFULLNAME")
	public String getFldFullName() {
		return this.fldFullName;
	}

	public void setFldFullName(String fldFullName) {
		this.fldFullName = fldFullName;
	}

	@Column(name = "FLDSHORTNAME")
	public String getFldShortName() {
		return this.fldShortName;
	}

	public void setFldShortName(String fldShortName) {
		this.fldShortName = fldShortName;
	}

	@Column(name = "FLDDESCRIPTION")
	public String getFldDescription() {
		return this.fldDescription;
	}

	public void setFldDescription(String fldDescription) {
		this.fldDescription = fldDescription;
	}

	@Column(name = "FLDESTABLISHDATE")
	public Date getFldEstablishDate() {
		return this.fldEstablishDate;
	}

	public void setFldEstablishDate(Date fldEstablishDate) {
		this.fldEstablishDate = fldEstablishDate;
	}

	@Column(name = "FLDVALUEDATE")
	public Date getFldValueDate() {
		return this.fldValueDate;
	}

	public void setFldValueDate(Date fldValueDate) {
		this.fldValueDate = fldValueDate;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
	}

	@Column(name = "FLDSTATUS")
	public Integer getFldStatus() {
		return this.fldStatus;
	}

	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
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