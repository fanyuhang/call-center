package com.redcard.customer.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CustomerProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tblCustomerProduct")
public class CustomerProduct implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldFullName;
	private String fldShortName;
	private String fldDescription;
	private Timestamp fldEstablishDate;
	private Timestamp fldValueDate;
	private String fldComment;
	private Integer fldStatus;
	private String fldOperateUserNo;
	private Timestamp fldOperateDate;
	private String fldCreateUserNo;
	private Timestamp fldCreateDate;

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
			Timestamp fldEstablishDate, Timestamp fldValueDate,
			String fldComment, Integer fldStatus, String fldOperateUserNo,
			Timestamp fldOperateDate, String fldCreateUserNo,
			Timestamp fldCreateDate) {
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
	@Column(name = "fldId", unique = true, nullable = false)
	public String getFldId() {
		return this.fldId;
	}

	public void setFldId(String fldId) {
		this.fldId = fldId;
	}

	@Column(name = "fldFullName")
	public String getFldFullName() {
		return this.fldFullName;
	}

	public void setFldFullName(String fldFullName) {
		this.fldFullName = fldFullName;
	}

	@Column(name = "fldShortName")
	public String getFldShortName() {
		return this.fldShortName;
	}

	public void setFldShortName(String fldShortName) {
		this.fldShortName = fldShortName;
	}

	@Column(name = "fldDescription")
	public String getFldDescription() {
		return this.fldDescription;
	}

	public void setFldDescription(String fldDescription) {
		this.fldDescription = fldDescription;
	}

	@Column(name = "fldEstablishDate")
	public Timestamp getFldEstablishDate() {
		return this.fldEstablishDate;
	}

	public void setFldEstablishDate(Timestamp fldEstablishDate) {
		this.fldEstablishDate = fldEstablishDate;
	}

	@Column(name = "fldValueDate")
	public Timestamp getFldValueDate() {
		return this.fldValueDate;
	}

	public void setFldValueDate(Timestamp fldValueDate) {
		this.fldValueDate = fldValueDate;
	}

	@Column(name = "fldComment")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
	}

	@Column(name = "fldStatus")
	public Integer getFldStatus() {
		return this.fldStatus;
	}

	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}

	@Column(name = "fldOperateUserNo")
	public String getFldOperateUserNo() {
		return this.fldOperateUserNo;
	}

	public void setFldOperateUserNo(String fldOperateUserNo) {
		this.fldOperateUserNo = fldOperateUserNo;
	}

	@Column(name = "fldOperateDate")
	public Timestamp getFldOperateDate() {
		return this.fldOperateDate;
	}

	public void setFldOperateDate(Timestamp fldOperateDate) {
		this.fldOperateDate = fldOperateDate;
	}

	@Column(name = "fldCreateUserNo")
	public String getFldCreateUserNo() {
		return this.fldCreateUserNo;
	}

	public void setFldCreateUserNo(String fldCreateUserNo) {
		this.fldCreateUserNo = fldCreateUserNo;
	}

	@Column(name = "fldCreateDate")
	public Timestamp getFldCreateDate() {
		return this.fldCreateDate;
	}

	public void setFldCreateDate(Timestamp fldCreateDate) {
		this.fldCreateDate = fldCreateDate;
	}

}