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
 * CustomerExchangeContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMEREXCHANGECONTRACT")
public class CustomerExchangeContract implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fldCustomerExchangeId;
	private String fldContractId;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public CustomerExchangeContract() {
	}

	/** minimal constructor */
	public CustomerExchangeContract(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public CustomerExchangeContract(Integer id, String fldCustomerExchangeId,
			String fldContractId, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.id = id;
		this.fldCustomerExchangeId = fldCustomerExchangeId;
		this.fldContractId = fldContractId;
		this.fldOperateUserNo = fldOperateUserNo;
		this.fldOperateDate = fldOperateDate;
		this.fldCreateUserNo = fldCreateUserNo;
		this.fldCreateDate = fldCreateDate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "FLDCUSTOMEREXCHANGEID")
	public String getFldCustomerExchangeId() {
		return this.fldCustomerExchangeId;
	}

	public void setFldCustomerExchangeId(String fldCustomerExchangeId) {
		this.fldCustomerExchangeId = fldCustomerExchangeId;
	}

	@Column(name = "FLDCONTRACTID")
	public String getFldContractId() {
		return this.fldContractId;
	}

	public void setFldContractId(String fldContractId) {
		this.fldContractId = fldContractId;
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