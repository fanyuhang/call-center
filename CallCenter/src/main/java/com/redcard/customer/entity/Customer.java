package com.redcard.customer.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tblCustomer")
public class Customer implements java.io.Serializable {

	// Fields

	private String fldId;
	private Integer fldSource;
	private Integer fldGender;
	private String fldIdentityNo;
	private String fldPhone;
	private String fldMobile;
	private String fldAddress;
	private String fldEmail;
	private String fldComment;
	private String fldFinancialUserNo;
	private String fldCustomerUserNo;
	private String fldServiceUserNo;
	private String fldCardNo;
	private Integer fldCardLevel;
	private Double fldCardTotalMoney;
	private Integer fldCardStatus;
	private Integer fldStatus;
	private String fldOperateUserNo;
	private Timestamp fldOperateDate;
	private String fldCreateUserNo;
	private Timestamp fldCreateDate;

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public Customer(String fldId, Integer fldSource, Integer fldGender,
			String fldIdentityNo, String fldPhone, String fldMobile,
			String fldAddress, String fldEmail, String fldComment,
			String fldFinancialUserNo, String fldCustomerUserNo,
			String fldServiceUserNo, String fldCardNo, Integer fldCardLevel,
			Double fldCardTotalMoney, Integer fldCardStatus, Integer fldStatus,
			String fldOperateUserNo, Timestamp fldOperateDate,
			String fldCreateUserNo, Timestamp fldCreateDate) {
		this.fldId = fldId;
		this.fldSource = fldSource;
		this.fldGender = fldGender;
		this.fldIdentityNo = fldIdentityNo;
		this.fldPhone = fldPhone;
		this.fldMobile = fldMobile;
		this.fldAddress = fldAddress;
		this.fldEmail = fldEmail;
		this.fldComment = fldComment;
		this.fldFinancialUserNo = fldFinancialUserNo;
		this.fldCustomerUserNo = fldCustomerUserNo;
		this.fldServiceUserNo = fldServiceUserNo;
		this.fldCardNo = fldCardNo;
		this.fldCardLevel = fldCardLevel;
		this.fldCardTotalMoney = fldCardTotalMoney;
		this.fldCardStatus = fldCardStatus;
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

	@Column(name = "fldSource")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "fldGender")
	public Integer getFldGender() {
		return this.fldGender;
	}

	public void setFldGender(Integer fldGender) {
		this.fldGender = fldGender;
	}

	@Column(name = "fldIdentityNo")
	public String getFldIdentityNo() {
		return this.fldIdentityNo;
	}

	public void setFldIdentityNo(String fldIdentityNo) {
		this.fldIdentityNo = fldIdentityNo;
	}

	@Column(name = "fldPhone")
	public String getFldPhone() {
		return this.fldPhone;
	}

	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}

	@Column(name = "fldMobile")
	public String getFldMobile() {
		return this.fldMobile;
	}

	public void setFldMobile(String fldMobile) {
		this.fldMobile = fldMobile;
	}

	@Column(name = "fldAddress")
	public String getFldAddress() {
		return this.fldAddress;
	}

	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}

	@Column(name = "fldEmail")
	public String getFldEmail() {
		return this.fldEmail;
	}

	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	@Column(name = "fldComment")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
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

	@Column(name = "fldCardTotalMoney")
	public Double getFldCardTotalMoney() {
		return this.fldCardTotalMoney;
	}

	public void setFldCardTotalMoney(Double fldCardTotalMoney) {
		this.fldCardTotalMoney = fldCardTotalMoney;
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