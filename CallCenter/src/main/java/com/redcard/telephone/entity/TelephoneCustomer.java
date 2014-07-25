package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.common.security.util.SecurityUtil;

/**
 * TelephoneCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONECUSTOMER")
public class TelephoneCustomer implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldCustomerName;
	private Integer fldGender;
	private String fldMobile;
	private String fldPhone;
	private String fldAddress;
	private Integer fldSource;
	private Date fldLatestCallDate;
	private Integer fldResultStatus;
	private Integer fldAssignStatus;
	private Date fldAssignDate;
	private String fldFinancialUserNo;
	private String fldCallUserNo;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public TelephoneCustomer() {
	}

	/** minimal constructor */
	public TelephoneCustomer(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneCustomer(String fldId, String fldCustomerName,
			Integer fldGender, String fldMobile, String fldPhone,
			String fldAddress, Integer fldSource, Date fldLatestCallDate,
			Integer fldResultStatus, Integer fldAssignStatus,
			Date fldAssignDate, String fldFinancialUserNo,
			String fldCallUserNo, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldCustomerName = fldCustomerName;
		this.fldGender = fldGender;
		this.fldMobile = fldMobile;
		this.fldPhone = fldPhone;
		this.fldAddress = fldAddress;
		this.fldSource = fldSource;
		this.fldLatestCallDate = fldLatestCallDate;
		this.fldResultStatus = fldResultStatus;
		this.fldAssignStatus = fldAssignStatus;
		this.fldAssignDate = fldAssignDate;
		this.fldFinancialUserNo = fldFinancialUserNo;
		this.fldCallUserNo = fldCallUserNo;
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

	@Column(name = "FLDCUSTOMERNAME")
	public String getFldCustomerName() {
		return this.fldCustomerName;
	}

	public void setFldCustomerName(String fldCustomerName) {
		this.fldCustomerName = fldCustomerName;
	}

	@Column(name = "FLDGENDER")
	public Integer getFldGender() {
		return this.fldGender;
	}

	public void setFldGender(Integer fldGender) {
		this.fldGender = fldGender;
	}

	@Column(name = "FLDMOBILE")
	public String getFldMobile() {
		return this.fldMobile;
	}

	public void setFldMobile(String fldMobile) {
		this.fldMobile = fldMobile;
	}

	@Column(name = "FLDPHONE")
	public String getFldPhone() {
		return this.fldPhone;
	}

	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}

	@Column(name = "FLDADDRESS")
	public String getFldAddress() {
		return this.fldAddress;
	}

	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}

	@Column(name = "FLDSOURCE")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "FLDLATESTCALLDATE")
	public Date getFldLatestCallDate() {
		return this.fldLatestCallDate;
	}

	public void setFldLatestCallDate(Date fldLatestCallDate) {
		this.fldLatestCallDate = fldLatestCallDate;
	}

	@Column(name = "FLDRESULTSTATUS")
	public Integer getFldResultStatus() {
		return this.fldResultStatus;
	}

	public void setFldResultStatus(Integer fldResultStatus) {
		this.fldResultStatus = fldResultStatus;
	}

	@Column(name = "FLDASSIGNSTATUS")
	public Integer getFldAssignStatus() {
		return this.fldAssignStatus;
	}

	public void setFldAssignStatus(Integer fldAssignStatus) {
		this.fldAssignStatus = fldAssignStatus;
	}

	@Column(name = "FLDASSIGNDATE")
	public Date getFldAssignDate() {
		return this.fldAssignDate;
	}

	public void setFldAssignDate(Date fldAssignDate) {
		this.fldAssignDate = fldAssignDate;
	}

	@Column(name = "FLDFINANCIALUSERNO")
	public String getFldFinancialUserNo() {
		return this.fldFinancialUserNo;
	}

	public void setFldFinancialUserNo(String fldFinancialUserNo) {
		this.fldFinancialUserNo = fldFinancialUserNo;
	}

	@Column(name = "FLDCALLUSERNO")
	public String getFldCallUserNo() {
		return this.fldCallUserNo;
	}

	public void setFldCallUserNo(String fldCallUserNo) {
		this.fldCallUserNo = fldCallUserNo;
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