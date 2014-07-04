package com.redcard.customer.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CustomerContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tblCustomerContract")
public class CustomerContract implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldCustomerId;
	private String fldProductId;
	private String fldProductDetailId;
	private Timestamp fldSignDate;
	private Timestamp fldMoneyDate;
	private Integer fldCollectDays;
	private Double fldDepositRate;
	private Double fldCollectMoney;
	private Double fldAnnualizedRate;
	private Double fldAnnualizedMoney;
	private Double fldPurchaseMoney;
	private Double fldCommissionRadio;
	private Double fldCommissionMoney;
	private Double fldPerformanceRadio;
	private Double fldPerformanceMoney;
	private String fldBankNo;
	private String fldBankName;
	private String fldFinancialUserNo;
	private String fldCustomerUserNo;
	private String fldServiceUserNo;
	private Integer fldSource;
	private Double fldCardMoney;
	private String fldCardNo;
	private Integer fldCardLevel;
	private Integer fldCardStatus;
	private Integer fldStatus;
	private Integer fldFinishStatus;
	private String fldOperateUserNo;
	private Timestamp fldOperateDate;
	private String fldCreateUserNo;
	private Timestamp fldCreateDate;

	// Constructors

	/** default constructor */
	public CustomerContract() {
	}

	/** minimal constructor */
	public CustomerContract(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public CustomerContract(String fldId, String fldCustomerId,
			String fldProductId, String fldProductDetailId,
			Timestamp fldSignDate, Timestamp fldMoneyDate,
			Integer fldCollectDays, Double fldDepositRate,
			Double fldCollectMoney, Double fldAnnualizedRate,
			Double fldAnnualizedMoney, Double fldPurchaseMoney,
			Double fldCommissionRadio, Double fldCommissionMoney,
			Double fldPerformanceRadio, Double fldPerformanceMoney,
			String fldBankNo, String fldBankName, String fldFinancialUserNo,
			String fldCustomerUserNo, String fldServiceUserNo,
			Integer fldSource, Double fldCardMoney, String fldCardNo,
			Integer fldCardLevel, Integer fldCardStatus, Integer fldStatus,
			Integer fldFinishStatus, String fldOperateUserNo,
			Timestamp fldOperateDate, String fldCreateUserNo,
			Timestamp fldCreateDate) {
		this.fldId = fldId;
		this.fldCustomerId = fldCustomerId;
		this.fldProductId = fldProductId;
		this.fldProductDetailId = fldProductDetailId;
		this.fldSignDate = fldSignDate;
		this.fldMoneyDate = fldMoneyDate;
		this.fldCollectDays = fldCollectDays;
		this.fldDepositRate = fldDepositRate;
		this.fldCollectMoney = fldCollectMoney;
		this.fldAnnualizedRate = fldAnnualizedRate;
		this.fldAnnualizedMoney = fldAnnualizedMoney;
		this.fldPurchaseMoney = fldPurchaseMoney;
		this.fldCommissionRadio = fldCommissionRadio;
		this.fldCommissionMoney = fldCommissionMoney;
		this.fldPerformanceRadio = fldPerformanceRadio;
		this.fldPerformanceMoney = fldPerformanceMoney;
		this.fldBankNo = fldBankNo;
		this.fldBankName = fldBankName;
		this.fldFinancialUserNo = fldFinancialUserNo;
		this.fldCustomerUserNo = fldCustomerUserNo;
		this.fldServiceUserNo = fldServiceUserNo;
		this.fldSource = fldSource;
		this.fldCardMoney = fldCardMoney;
		this.fldCardNo = fldCardNo;
		this.fldCardLevel = fldCardLevel;
		this.fldCardStatus = fldCardStatus;
		this.fldStatus = fldStatus;
		this.fldFinishStatus = fldFinishStatus;
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

	@Column(name = "fldCustomerId")
	public String getFldCustomerId() {
		return this.fldCustomerId;
	}

	public void setFldCustomerId(String fldCustomerId) {
		this.fldCustomerId = fldCustomerId;
	}

	@Column(name = "fldProductId")
	public String getFldProductId() {
		return this.fldProductId;
	}

	public void setFldProductId(String fldProductId) {
		this.fldProductId = fldProductId;
	}

	@Column(name = "fldProductDetailId")
	public String getFldProductDetailId() {
		return this.fldProductDetailId;
	}

	public void setFldProductDetailId(String fldProductDetailId) {
		this.fldProductDetailId = fldProductDetailId;
	}

	@Column(name = "fldSignDate")
	public Timestamp getFldSignDate() {
		return this.fldSignDate;
	}

	public void setFldSignDate(Timestamp fldSignDate) {
		this.fldSignDate = fldSignDate;
	}

	@Column(name = "fldMoneyDate")
	public Timestamp getFldMoneyDate() {
		return this.fldMoneyDate;
	}

	public void setFldMoneyDate(Timestamp fldMoneyDate) {
		this.fldMoneyDate = fldMoneyDate;
	}

	@Column(name = "fldCollectDays")
	public Integer getFldCollectDays() {
		return this.fldCollectDays;
	}

	public void setFldCollectDays(Integer fldCollectDays) {
		this.fldCollectDays = fldCollectDays;
	}

	@Column(name = "fldDepositRate")
	public Double getFldDepositRate() {
		return this.fldDepositRate;
	}

	public void setFldDepositRate(Double fldDepositRate) {
		this.fldDepositRate = fldDepositRate;
	}

	@Column(name = "fldCollectMoney")
	public Double getFldCollectMoney() {
		return this.fldCollectMoney;
	}

	public void setFldCollectMoney(Double fldCollectMoney) {
		this.fldCollectMoney = fldCollectMoney;
	}

	@Column(name = "fldAnnualizedRate")
	public Double getFldAnnualizedRate() {
		return this.fldAnnualizedRate;
	}

	public void setFldAnnualizedRate(Double fldAnnualizedRate) {
		this.fldAnnualizedRate = fldAnnualizedRate;
	}

	@Column(name = "fldAnnualizedMoney")
	public Double getFldAnnualizedMoney() {
		return this.fldAnnualizedMoney;
	}

	public void setFldAnnualizedMoney(Double fldAnnualizedMoney) {
		this.fldAnnualizedMoney = fldAnnualizedMoney;
	}

	@Column(name = "fldPurchaseMoney")
	public Double getFldPurchaseMoney() {
		return this.fldPurchaseMoney;
	}

	public void setFldPurchaseMoney(Double fldPurchaseMoney) {
		this.fldPurchaseMoney = fldPurchaseMoney;
	}

	@Column(name = "fldCommissionRadio", scale = 4)
	public Double getFldCommissionRadio() {
		return this.fldCommissionRadio;
	}

	public void setFldCommissionRadio(Double fldCommissionRadio) {
		this.fldCommissionRadio = fldCommissionRadio;
	}

	@Column(name = "fldCommissionMoney")
	public Double getFldCommissionMoney() {
		return this.fldCommissionMoney;
	}

	public void setFldCommissionMoney(Double fldCommissionMoney) {
		this.fldCommissionMoney = fldCommissionMoney;
	}

	@Column(name = "fldPerformanceRadio")
	public Double getFldPerformanceRadio() {
		return this.fldPerformanceRadio;
	}

	public void setFldPerformanceRadio(Double fldPerformanceRadio) {
		this.fldPerformanceRadio = fldPerformanceRadio;
	}

	@Column(name = "fldPerformanceMoney")
	public Double getFldPerformanceMoney() {
		return this.fldPerformanceMoney;
	}

	public void setFldPerformanceMoney(Double fldPerformanceMoney) {
		this.fldPerformanceMoney = fldPerformanceMoney;
	}

	@Column(name = "fldBankNo")
	public String getFldBankNo() {
		return this.fldBankNo;
	}

	public void setFldBankNo(String fldBankNo) {
		this.fldBankNo = fldBankNo;
	}

	@Column(name = "fldBankName")
	public String getFldBankName() {
		return this.fldBankName;
	}

	public void setFldBankName(String fldBankName) {
		this.fldBankName = fldBankName;
	}

	@Column(name = "fldFinancialUserNo")
	public String getFldFinancialUserNo() {
		return this.fldFinancialUserNo;
	}

	public void setFldFinancialUserNo(String fldFinancialUserNo) {
		this.fldFinancialUserNo = fldFinancialUserNo;
	}

	@Column(name = "fldCustomerUserNo")
	public String getFldCustomerUserNo() {
		return this.fldCustomerUserNo;
	}

	public void setFldCustomerUserNo(String fldCustomerUserNo) {
		this.fldCustomerUserNo = fldCustomerUserNo;
	}

	@Column(name = "fldServiceUserNo")
	public String getFldServiceUserNo() {
		return this.fldServiceUserNo;
	}

	public void setFldServiceUserNo(String fldServiceUserNo) {
		this.fldServiceUserNo = fldServiceUserNo;
	}

	@Column(name = "fldSource")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "fldCardMoney")
	public Double getFldCardMoney() {
		return this.fldCardMoney;
	}

	public void setFldCardMoney(Double fldCardMoney) {
		this.fldCardMoney = fldCardMoney;
	}

	@Column(name = "fldCardNo")
	public String getFldCardNo() {
		return this.fldCardNo;
	}

	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}

	@Column(name = "fldCardLevel")
	public Integer getFldCardLevel() {
		return this.fldCardLevel;
	}

	public void setFldCardLevel(Integer fldCardLevel) {
		this.fldCardLevel = fldCardLevel;
	}

	@Column(name = "fldCardStatus")
	public Integer getFldCardStatus() {
		return this.fldCardStatus;
	}

	public void setFldCardStatus(Integer fldCardStatus) {
		this.fldCardStatus = fldCardStatus;
	}

	@Column(name = "fldStatus")
	public Integer getFldStatus() {
		return this.fldStatus;
	}

	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}

	@Column(name = "fldFinishStatus")
	public Integer getFldFinishStatus() {
		return this.fldFinishStatus;
	}

	public void setFldFinishStatus(Integer fldFinishStatus) {
		this.fldFinishStatus = fldFinishStatus;
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