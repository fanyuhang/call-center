package com.redcard.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * CustomerContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMERCONTRACT")
public class CustomerContract implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldCustomerId;
	private String fldProductId;
	private String fldProductDetailId;
	private Date fldSignDate;
	private String fldSignDateStr;
	private Date fldMoneyDate;
	private String fldMoneyDateStr;
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
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

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
			Date fldSignDate, Date fldMoneyDate,
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
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
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
	@Column(name = "FLDID", unique = true, nullable = false)
	public String getFldId() {
		return this.fldId;
	}

	public void setFldId(String fldId) {
		this.fldId = fldId;
	}

	@Column(name = "FLDCUSTOMERID")
	public String getFldCustomerId() {
		return this.fldCustomerId;
	}

	public void setFldCustomerId(String fldCustomerId) {
		this.fldCustomerId = fldCustomerId;
	}

	@Column(name = "FLDPRODUCTID")
	public String getFldProductId() {
		return this.fldProductId;
	}

	public void setFldProductId(String fldProductId) {
		this.fldProductId = fldProductId;
	}

	@Column(name = "FLDPRODUCTDETAILID")
	public String getFldProductDetailId() {
		return this.fldProductDetailId;
	}

	public void setFldProductDetailId(String fldProductDetailId) {
		this.fldProductDetailId = fldProductDetailId;
	}

	@Column(name = "FLDSIGNDATE")
	public Date getFldSignDate() {
		return this.fldSignDate;
	}

	public void setFldSignDate(Date fldSignDate) {
		this.fldSignDate = fldSignDate;
	}

	@Transient
	public String getFldSignDateStr() {
		return fldSignDateStr;
	}

	public void setFldSignDateStr(String fldSignDateStr) {
		this.fldSignDateStr = fldSignDateStr;
	}

	@Column(name = "FLDMONEYDATE")
	public Date getFldMoneyDate() {
		return this.fldMoneyDate;
	}

	public void setFldMoneyDate(Date fldMoneyDate) {
		this.fldMoneyDate = fldMoneyDate;
	}

	@Transient
	public String getFldMoneyDateStr() {
		return fldMoneyDateStr;
	}

	public void setFldMoneyDateStr(String fldMoneyDateStr) {
		this.fldMoneyDateStr = fldMoneyDateStr;
	}

	@Column(name = "FLDCOLLECTDAYS")
	public Integer getFldCollectDays() {
		return this.fldCollectDays;
	}

	public void setFldCollectDays(Integer fldCollectDays) {
		this.fldCollectDays = fldCollectDays;
	}

	@Column(name = "FLDDEPOSITRATE")
	public Double getFldDepositRate() {
		return this.fldDepositRate;
	}

	public void setFldDepositRate(Double fldDepositRate) {
		this.fldDepositRate = fldDepositRate;
	}

	@Column(name = "FLDCOLLECTMONEY")
	public Double getFldCollectMoney() {
		return this.fldCollectMoney;
	}

	public void setFldCollectMoney(Double fldCollectMoney) {
		this.fldCollectMoney = fldCollectMoney;
	}

	@Column(name = "FLDANNUALIZEDRATE")
	public Double getFldAnnualizedRate() {
		return this.fldAnnualizedRate;
	}

	public void setFldAnnualizedRate(Double fldAnnualizedRate) {
		this.fldAnnualizedRate = fldAnnualizedRate;
	}

	@Column(name = "FLDANNUALIZEDMONEY")
	public Double getFldAnnualizedMoney() {
		return this.fldAnnualizedMoney;
	}

	public void setFldAnnualizedMoney(Double fldAnnualizedMoney) {
		this.fldAnnualizedMoney = fldAnnualizedMoney;
	}

	@Column(name = "FLDPURCHASEMONEY")
	public Double getFldPurchaseMoney() {
		return this.fldPurchaseMoney;
	}

	public void setFldPurchaseMoney(Double fldPurchaseMoney) {
		this.fldPurchaseMoney = fldPurchaseMoney;
	}

	@Column(name = "FLDCOMMISSIONRADIO")
	public Double getFldCommissionRadio() {
		return this.fldCommissionRadio;
	}

	public void setFldCommissionRadio(Double fldCommissionRadio) {
		this.fldCommissionRadio = fldCommissionRadio;
	}

	@Column(name = "FLDCOMMISSIONMONEY")
	public Double getFldCommissionMoney() {
		return this.fldCommissionMoney;
	}

	public void setFldCommissionMoney(Double fldCommissionMoney) {
		this.fldCommissionMoney = fldCommissionMoney;
	}

	@Column(name = "FLDPERFORMANCERADIO")
	public Double getFldPerformanceRadio() {
		return this.fldPerformanceRadio;
	}

	public void setFldPerformanceRadio(Double fldPerformanceRadio) {
		this.fldPerformanceRadio = fldPerformanceRadio;
	}

	@Column(name = "FLDPERFORMANCEMONEY")
	public Double getFldPerformanceMoney() {
		return this.fldPerformanceMoney;
	}

	public void setFldPerformanceMoney(Double fldPerformanceMoney) {
		this.fldPerformanceMoney = fldPerformanceMoney;
	}

	@Column(name = "FLDBANKNO")
	public String getFldBankNo() {
		return this.fldBankNo;
	}

	public void setFldBankNo(String fldBankNo) {
		this.fldBankNo = fldBankNo;
	}

	@Column(name = "FLDBANKNAME")
	public String getFldBankName() {
		return this.fldBankName;
	}

	public void setFldBankName(String fldBankName) {
		this.fldBankName = fldBankName;
	}

	@Column(name = "FLDFINANCIALUSERNO")
	public String getFldFinancialUserNo() {
		return this.fldFinancialUserNo;
	}

	public void setFldFinancialUserNo(String fldFinancialUserNo) {
		this.fldFinancialUserNo = fldFinancialUserNo;
	}

	@Column(name = "FLDCUSTOMERUSERNO")
	public String getFldCustomerUserNo() {
		return this.fldCustomerUserNo;
	}

	public void setFldCustomerUserNo(String fldCustomerUserNo) {
		this.fldCustomerUserNo = fldCustomerUserNo;
	}

	@Column(name = "FLDSERVICEUSERNO")
	public String getFldServiceUserNo() {
		return this.fldServiceUserNo;
	}

	public void setFldServiceUserNo(String fldServiceUserNo) {
		this.fldServiceUserNo = fldServiceUserNo;
	}

	@Column(name = "FLDSOURCE")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "FLDCARDMONEY")
	public Double getFldCardMoney() {
		return this.fldCardMoney;
	}

	public void setFldCardMoney(Double fldCardMoney) {
		this.fldCardMoney = fldCardMoney;
	}

	@Column(name = "FLDCARDNO")
	public String getFldCardNo() {
		return this.fldCardNo;
	}

	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}

	@Column(name = "FLDCARDLEVEL")
	public Integer getFldCardLevel() {
		return this.fldCardLevel;
	}

	public void setFldCardLevel(Integer fldCardLevel) {
		this.fldCardLevel = fldCardLevel;
	}

	@Column(name = "FLDCARDSTATUS")
	public Integer getFldCardStatus() {
		return this.fldCardStatus;
	}

	public void setFldCardStatus(Integer fldCardStatus) {
		this.fldCardStatus = fldCardStatus;
	}

	@Column(name = "FLDSTATUS")
	public Integer getFldStatus() {
		return this.fldStatus;
	}

	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}

	@Column(name = "FLDFINISHSTATUS")
	public Integer getFldFinishStatus() {
		return this.fldFinishStatus;
	}

	public void setFldFinishStatus(Integer fldFinishStatus) {
		this.fldFinishStatus = fldFinishStatus;
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
	
	private Customer customer;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCUSTOMERID", insertable = false, updatable = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}