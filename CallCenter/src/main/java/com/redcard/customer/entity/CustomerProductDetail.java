package com.redcard.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.common.security.util.SecurityUtil;

/**
 * CustomerProductDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMERPRODUCTDETAIL")
public class CustomerProductDetail implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldProductId;
	private Integer fldClearDays;
	private Date fldDueDate;
	private Double fldMinPurchaseMoney;
	private Double fldMaxPurchaseMoney;
	private Double fldAnnualizedRate;
	private Double fldDepositRate;
	private Double fldPerformanceRadio;
	private Double fldCommissionRadio;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public CustomerProductDetail() {
	}

	/** minimal constructor */
	public CustomerProductDetail(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public CustomerProductDetail(String fldId, String fldProductId,
			Integer fldClearDays, Date fldDueDate,
			Double fldMinPurchaseMoney, Double fldMaxPurchaseMoney,
			Double fldAnnualizedRate, Double fldDepositRate,
			Double fldPerformanceRadio, Double fldCommissionRadio,
			String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldProductId = fldProductId;
		this.fldClearDays = fldClearDays;
		this.fldDueDate = fldDueDate;
		this.fldMinPurchaseMoney = fldMinPurchaseMoney;
		this.fldMaxPurchaseMoney = fldMaxPurchaseMoney;
		this.fldAnnualizedRate = fldAnnualizedRate;
		this.fldDepositRate = fldDepositRate;
		this.fldPerformanceRadio = fldPerformanceRadio;
		this.fldCommissionRadio = fldCommissionRadio;
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

	@Column(name = "FLDPRODUCTID")
	public String getFldProductId() {
		return this.fldProductId;
	}

	public void setFldProductId(String fldProductId) {
		this.fldProductId = fldProductId;
	}

	@Column(name = "FLDCLEARDAYS")
	public Integer getFldClearDays() {
		return this.fldClearDays;
	}

	public void setFldClearDays(Integer fldClearDays) {
		this.fldClearDays = fldClearDays;
	}

	@Column(name = "FLDDUEDATE")
	public Date getFldDueDate() {
		return this.fldDueDate;
	}

	public void setFldDueDate(Date fldDueDate) {
		this.fldDueDate = fldDueDate;
	}

	@Column(name = "FLDMINPURCHASEMONEY")
	public Double getFldMinPurchaseMoney() {
		return this.fldMinPurchaseMoney;
	}

	public void setFldMinPurchaseMoney(Double fldMinPurchaseMoney) {
		this.fldMinPurchaseMoney = fldMinPurchaseMoney;
	}

	@Column(name = "FLDMAXPURCHASEMONEY")
	public Double getFldMaxPurchaseMoney() {
		return this.fldMaxPurchaseMoney;
	}

	public void setFldMaxPurchaseMoney(Double fldMaxPurchaseMoney) {
		this.fldMaxPurchaseMoney = fldMaxPurchaseMoney;
	}

	@Column(name = "FLDANNUALIZEDRATE")
	public Double getFldAnnualizedRate() {
		return this.fldAnnualizedRate;
	}

	public void setFldAnnualizedRate(Double fldAnnualizedRate) {
		this.fldAnnualizedRate = fldAnnualizedRate;
	}

	@Column(name = "FLDDEPOSITRATE")
	public Double getFldDepositRate() {
		return this.fldDepositRate;
	}

	public void setFldDepositRate(Double fldDepositRate) {
		this.fldDepositRate = fldDepositRate;
	}

	@Column(name = "FLDPERFORMANCERADIO")
	public Double getFldPerformanceRadio() {
		return this.fldPerformanceRadio;
	}

	public void setFldPerformanceRadio(Double fldPerformanceRadio) {
		this.fldPerformanceRadio = fldPerformanceRadio;
	}

	@Column(name = "FLDCOMMISSIONRADIO")
	public Double getFldCommissionRadio() {
		return this.fldCommissionRadio;
	}

	public void setFldCommissionRadio(Double fldCommissionRadio) {
		this.fldCommissionRadio = fldCommissionRadio;
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