package com.redcard.telephone.entity;

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
 * TelephoneCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONECUSTOMER")
public class TelephoneCustomer implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldCustomerName;
	private Integer fldGender;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fldBirthday;
	private String fldIdentityNo;
	private String fldMobile;
	private String fldPhone;
	private String fldAddress;
	private String fldEmail;
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	
	@Column(name = "FLDEMAIL")
	public String getFldEmail() {
		return this.fldEmail;
	}

	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}
	
	private User financialUser;

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
    
    private User callUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCALLUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getCallUser() {
		return callUser;
	}

	public void setCallUser(User callUser) {
		this.callUser = callUser;
	}
	
	@Transient
	public String getCallUserName() {
		return callUser!=null ? callUser.getUserName() : "";
	}
}