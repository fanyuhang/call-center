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

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * CustomerExchange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMEREXCHANGE")
public class CustomerExchange implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldOldUserNo;
	private String fldNewUserNo;
	private Integer fldOldCustomerNum;
	private Integer fldOldContractNum;
	private Integer fldNewCustomerNum;
	private Integer fldNewContractNum;
	private Integer fldCustomerNum;
	private Integer fldContractNum;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;
	
	private Integer customerNum;
	private Integer contractNum;

	// Constructors

	/** default constructor */
	public CustomerExchange() {
	}

	/** minimal constructor */
	public CustomerExchange(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public CustomerExchange(String fldId, String fldOldUserNo,
			String fldNewUserNo, Integer fldOldCustomerNum,
			Integer fldOldContractNum, Integer fldNewCustomerNum,
			Integer fldNewContractNum, Integer fldCustomerNum,
			Integer fldContractNum, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldOldUserNo = fldOldUserNo;
		this.fldNewUserNo = fldNewUserNo;
		this.fldOldCustomerNum = fldOldCustomerNum;
		this.fldOldContractNum = fldOldContractNum;
		this.fldNewCustomerNum = fldNewCustomerNum;
		this.fldNewContractNum = fldNewContractNum;
		this.fldCustomerNum = fldCustomerNum;
		this.fldContractNum = fldContractNum;
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

	@Column(name = "FLDOLDUSERNO")
	public String getFldOldUserNo() {
		return this.fldOldUserNo;
	}

	public void setFldOldUserNo(String fldOldUserNo) {
		this.fldOldUserNo = fldOldUserNo;
	}

	@Column(name = "FLDNEWUSERNO")
	public String getFldNewUserNo() {
		return this.fldNewUserNo;
	}

	public void setFldNewUserNo(String fldNewUserNo) {
		this.fldNewUserNo = fldNewUserNo;
	}

	@Column(name = "FLDOLDCUSTOMERNUM")
	public Integer getFldOldCustomerNum() {
		return this.fldOldCustomerNum;
	}

	public void setFldOldCustomerNum(Integer fldOldCustomerNum) {
		this.fldOldCustomerNum = fldOldCustomerNum;
	}

	@Column(name = "FLDOLDCONTRACTNUM")
	public Integer getFldOldContractNum() {
		return this.fldOldContractNum;
	}

	public void setFldOldContractNum(Integer fldOldContractNum) {
		this.fldOldContractNum = fldOldContractNum;
	}

	@Column(name = "FLDNEWCUSTOMERNUM")
	public Integer getFldNewCustomerNum() {
		return this.fldNewCustomerNum;
	}

	public void setFldNewCustomerNum(Integer fldNewCustomerNum) {
		this.fldNewCustomerNum = fldNewCustomerNum;
	}

	@Column(name = "FLDNEWCONTRACTNUM")
	public Integer getFldNewContractNum() {
		return this.fldNewContractNum;
	}

	public void setFldNewContractNum(Integer fldNewContractNum) {
		this.fldNewContractNum = fldNewContractNum;
	}

	@Column(name = "FLDCUSTOMERNUM")
	public Integer getFldCustomerNum() {
		return this.fldCustomerNum;
	}

	public void setFldCustomerNum(Integer fldCustomerNum) {
		this.fldCustomerNum = fldCustomerNum;
	}

	@Column(name = "FLDCONTRACTNUM")
	public Integer getFldContractNum() {
		return this.fldContractNum;
	}

	public void setFldContractNum(Integer fldContractNum) {
		this.fldContractNum = fldContractNum;
	}

	@Column(name = "FLDOPERATEUSERNO")
	public String getFldOperateUserNo() {
		return this.fldOperateUserNo;
	}

	public void setFldOperateUserNo(String fldOperateUserNo) {
		this.fldOperateUserNo = fldOperateUserNo;
	}

	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	@Transient
	public Integer getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(Integer customerNum) {
		this.customerNum = customerNum;
	}

	@Transient
	public Integer getContractNum() {
		return contractNum;
	}

	public void setContractNum(Integer contractNum) {
		this.contractNum = contractNum;
	}

	protected User oldUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOLDUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getOldUser() {
		return oldUser;
	}

	public void setOldUser(User oldUser) {
		this.oldUser = oldUser;
	}
	
	@Transient
	public String getOldUserName() {
		return null != oldUser ? oldUser.getUserName() : "";
	}
	
	protected User newUser;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDNEWUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
	@Transient
	public String getNewUserName() {
		return null != newUser ? newUser.getUserName() : "";
	}
}