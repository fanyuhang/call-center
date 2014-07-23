package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TelephoneExchangeDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEEXCHANGEDETAIL")
public class TelephoneExchangeDetail implements java.io.Serializable {

	// Fields

	private Long fldId;
	private String fldExchangeId;
	private Long fldTaskId;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public TelephoneExchangeDetail() {
	}

	/** minimal constructor */
	public TelephoneExchangeDetail(Long fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneExchangeDetail(Long fldId, String fldExchangeId,
			Long fldTaskId, String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldExchangeId = fldExchangeId;
		this.fldTaskId = fldTaskId;
		this.fldOperateUserNo = fldOperateUserNo;
		this.fldOperateDate = fldOperateDate;
		this.fldCreateUserNo = fldCreateUserNo;
		this.fldCreateDate = fldCreateDate;
	}

	// Property accessors
	@Id
	@Column(name = "FLDID", unique = true, nullable = false)
	public Long getFldId() {
		return this.fldId;
	}

	public void setFldId(Long fldId) {
		this.fldId = fldId;
	}

	@Column(name = "FLDEXCHANGEID")
	public String getFldExchangeId() {
		return this.fldExchangeId;
	}

	public void setFldExchangeId(String fldExchangeId) {
		this.fldExchangeId = fldExchangeId;
	}

	@Column(name = "FLDTASKID")
	public Long getFldTaskId() {
		return this.fldTaskId;
	}

	public void setFldTaskId(Long fldTaskId) {
		this.fldTaskId = fldTaskId;
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