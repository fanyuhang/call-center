package com.redcard.customer.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CustomerProductDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tblCustomerProductDetail")
public class CustomerProductDetail implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldProductId;
	private Integer fldClearDays;
	private Timestamp fldDueDate;
	private Double fldMinPurchaseMoney;
	private Double fldMaxPurchaseMoney;
	private Double fldAnnualizedRate;
	private Double fldDepositRate;
	private Double fldPerformanceRadio;
	private Double fldCommissionRadio;
	private String fldOperateUserNo;
	private Timestamp fldOperateDate;
	private String fldCreateUserNo;
	private Timestamp fldCreateDate;

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
			Integer fldClearDays, Timestamp fldDueDate,
			Double fldMinPurchaseMoney, Double fldMaxPurchaseMoney,
			Double fldAnnualizedRate, Double fldDepositRate,
			Double fldPerformanceRadio, Double fldCommissionRadio,
			String fldOperateUserNo, Timestamp fldOperateDate,
			String fldCreateUserNo, Timestamp fldCreateDate) {
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
	@Column(name = "fldId", unique = true, nullable = false)
	public String getFldId() {
		return this.fldId;
	}

	public void setFldId(String fldId) {
		this.fldId = fldId;
	}

	@Column(name = "fldProductId")
	public String getFldProductId() {
		return this.fldProductId;
	}

	public void setFldProductId(String fldProductId) {
		this.fldProductId = fldProductId;
	}

	@Column(name = "fldClearDays")
	public Integer getFldClearDays() {
		return this.fldClearDays;
	}

	public void setFldClearDays(Integer fldClearDays) {
		this.fldClearDays = fldClearDays;
	}

	@Column(name = "fldDueDate")
	public Timestamp getFldDueDate() {
		return this.fldDueDate;
	}

	public void setFldDueDate(Timestamp fldDueDate) {
		this.fldDueDate = fldDueDate;
	}

	@Column(name = "fldMinPurchaseMoney")
	public Double getFldMinPurchaseMoney() {
		return this.fldMinPurchaseMoney;
	}

	public void setFldMinPurchaseMoney(Double fldMinPurchaseMoney) {
		this.fldMinPurchaseMoney = fldMinPurchaseMoney;
	}

	@Column(name = "fldMaxPurchaseMoney")
	public Double getFldMaxPurchaseMoney() {
		return this.fldMaxPurchaseMoney;
	}

	public void setFldMaxPurchaseMoney(Double fldMaxPurchaseMoney) {
		this.fldMaxPurchaseMoney = fldMaxPurchaseMoney;
	}

	@Column(name = "fldAnnualizedRate")
	public Double getFldAnnualizedRate() {
		return this.fldAnnualizedRate;
	}

	public void setFldAnnualizedRate(Double fldAnnualizedRate) {
		this.fldAnnualizedRate = fldAnnualizedRate;
	}

	@Column(name = "fldDepositRate")
	public Double getFldDepositRate() {
		return this.fldDepositRate;
	}

	public void setFldDepositRate(Double fldDepositRate) {
		this.fldDepositRate = fldDepositRate;
	}

	@Column(name = "fldPerformanceRadio")
	public Double getFldPerformanceRadio() {
		return this.fldPerformanceRadio;
	}

	public void setFldPerformanceRadio(Double fldPerformanceRadio) {
		this.fldPerformanceRadio = fldPerformanceRadio;
	}

	@Column(name = "fldCommissionRadio")
	public Double getFldCommissionRadio() {
		return this.fldCommissionRadio;
	}

	public void setFldCommissionRadio(Double fldCommissionRadio) {
		this.fldCommissionRadio = fldCommissionRadio;
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