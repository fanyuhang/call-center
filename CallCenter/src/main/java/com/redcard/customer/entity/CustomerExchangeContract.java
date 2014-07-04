package com.redcard.customer.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CustomerExchangeContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tblCustomerExchangeContract")
public class CustomerExchangeContract implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fldCustomerExchangeId;
	private String fldContractId;
	private String fldOperateUserNo;
	private Timestamp fldOperateDate;
	private String fldCreateUserNo;
	private Timestamp fldCreateDate;

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
			Timestamp fldOperateDate, String fldCreateUserNo,
			Timestamp fldCreateDate) {
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
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "fldCustomerExchangeId")
	public String getFldCustomerExchangeId() {
		return this.fldCustomerExchangeId;
	}

	public void setFldCustomerExchangeId(String fldCustomerExchangeId) {
		this.fldCustomerExchangeId = fldCustomerExchangeId;
	}

	@Column(name = "fldContractId")
	public String getFldContractId() {
		return this.fldContractId;
	}

	public void setFldContractId(String fldContractId) {
		this.fldContractId = fldContractId;
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