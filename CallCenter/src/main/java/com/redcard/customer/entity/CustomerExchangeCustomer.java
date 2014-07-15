package com.redcard.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.common.security.util.SecurityUtil;

/**
 * CustomerExchangeCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMEREXCHANGECUSTOMER")
public class CustomerExchangeCustomer implements java.io.Serializable {

	// Fields

	private Integer fldId;
	private String fldCustomerExchangeId;
	private String fldCustomerId;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public CustomerExchangeCustomer() {
	}

	/** minimal constructor */
	public CustomerExchangeCustomer(Integer fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public CustomerExchangeCustomer(Integer fldId,
			String fldCustomerExchangeId, String fldCustomerId,
			String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldCustomerExchangeId = fldCustomerExchangeId;
		this.fldCustomerId = fldCustomerId;
		this.fldOperateUserNo = fldOperateUserNo;
		this.fldOperateDate = fldOperateDate;
		this.fldCreateUserNo = fldCreateUserNo;
		this.fldCreateDate = fldCreateDate;
	}

	// Property accessors
	@Id
	@Column(name = "FLDID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getFldId() {
		return this.fldId;
	}

	public void setFldId(Integer fldId) {
		this.fldId = fldId;
	}

	@Column(name = "FLDCUSTOMEREXCHANGEID")
	public String getFldCustomerExchangeId() {
		return this.fldCustomerExchangeId;
	}

	public void setFldCustomerExchangeId(String fldCustomerExchangeId) {
		this.fldCustomerExchangeId = fldCustomerExchangeId;
	}

	@Column(name = "FLDCUSTOMERID")
	public String getFldCustomerId() {
		return this.fldCustomerId;
	}

	public void setFldCustomerId(String fldCustomerId) {
		this.fldCustomerId = fldCustomerId;
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