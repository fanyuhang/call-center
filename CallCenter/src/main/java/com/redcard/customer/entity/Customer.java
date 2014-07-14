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
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.common.core.util.JsonDateSerializer;
import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMER")
public class Customer implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldName;
	private Integer fldSource;
	private Integer fldGender;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fldBirthday;
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
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;
	private String newServiceUserNo;

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
			String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
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
	@Column(name = "FLDID", unique = true, nullable = false)
	public String getFldId() {
		return this.fldId;
	}

	public void setFldId(String fldId) {
		this.fldId = fldId;
	}

	@Column(name = "FLDNAME")
	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	@Column(name = "FLDSOURCE")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "FLDGENDER")
	public Integer getFldGender() {
		return this.fldGender;
	}

	public void setFldGender(Integer fldGender) {
		this.fldGender = fldGender;
	}

	@Column(name = "FLDBIRTHDAY")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getFldBirthday() {
		return fldBirthday;
	}

	public void setFldBirthday(Date fldBirthday) {
		this.fldBirthday = fldBirthday;
	}

	@Column(name = "FLDIDENTITYNO")
	public String getFldIdentityNo() {
		return this.fldIdentityNo;
	}

	public void setFldIdentityNo(String fldIdentityNo) {
		this.fldIdentityNo = fldIdentityNo;
	}

	@Column(name = "FLDPHONE")
	public String getFldPhone() {
		return this.fldPhone;
	}

	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}

	@Column(name = "FLDMOBILE")
	public String getFldMobile() {
		return this.fldMobile;
	}

	public void setFldMobile(String fldMobile) {
		this.fldMobile = fldMobile;
	}

	@Column(name = "FLDADDRESS")
	public String getFldAddress() {
		return this.fldAddress;
	}

	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}

	@Column(name = "FLDEMAIL")
	public String getFldEmail() {
		return this.fldEmail;
	}

	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
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

	@Column(name = "FLDCARDTOTALMONEY")
	public Double getFldCardTotalMoney() {
		return this.fldCardTotalMoney;
	}

	public void setFldCardTotalMoney(Double fldCardTotalMoney) {
		this.fldCardTotalMoney = fldCardTotalMoney;
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

	@Column(name = "FLDOPERATEUSERNO")
	public String getFldOperateUserNo() {
		return this.fldOperateUserNo;
	}

	public void setFldOperateUserNo(String fldOperateUserNo) {
		this.fldOperateUserNo = fldOperateUserNo;
	}

	@Column(name = "FLDOPERATEDATE")
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Date getFldCreateDate() {
		return this.fldCreateDate;
	}

	public void setFldCreateDate(Date fldCreateDate) {
		this.fldCreateDate = fldCreateDate;
	}
	
	
	protected User operateUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOPERATEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}
	
	@Transient
	public String getOperateUserName() {
		return operateUser!=null ? operateUser.getUserName() : "";
	}
	
	protected User financialUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDFINANCIALUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getFinancialUser() {
		return financialUser;
	}

	public void setFinancialUser(User financialUser) {
		this.financialUser = financialUser;
	}
	
	@Transient
	public String getFinancialUserName() {
		return financialUser!=null ? financialUser.getUserName() : "";
	}
	
	protected User customerUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCUSTOMERUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getCustomerUser() {
		return customerUser;
	}

	public void setCustomerUser(User customerUser) {
		this.customerUser = customerUser;
	}
	
	@Transient
	public String getCustomerUserName() {
		return customerUser!=null ? customerUser.getUserName() : "";
	}
	
	protected User serviceUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDSERVICEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(User serviceUser) {
		this.serviceUser = serviceUser;
	}
	
	@Transient
	public String getServiceUserName() {
		return serviceUser!=null ? serviceUser.getUserName() : "";
	}

	@Transient
	public String getNewServiceUserNo() {
		return newServiceUserNo;
	}

	public void setNewServiceUserNo(String newServiceUserNo) {
		this.newServiceUserNo = newServiceUserNo;
	}
}