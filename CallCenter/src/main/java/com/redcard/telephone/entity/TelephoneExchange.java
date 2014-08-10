package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TelephoneExchange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEEXCHANGE")
public class TelephoneExchange implements java.io.Serializable {

	// Fields

	private String fldId;
	private String fldOldAssignDetailId;
	private String fldNewAssignDetailId;
	private Date fldExchangeDate;
	private Date fldOldTaskDate;
	private Date fldNewTaskDate;
	private Integer fldExchangeNumber;
	private String fldOldCallUserNo;
	private String fldNewCallUserNo;
	private Integer fldOldBeforeNumber;
	private Integer fldOldAfterNumber;
	private Integer fldNewBeforeNumber;
	private Integer fldNewAfterNumber;
	private String fldComment;
	private String fldOperateUserNo;
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public TelephoneExchange() {
	}

	/** minimal constructor */
	public TelephoneExchange(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneExchange(String fldId, String fldOldAssignDetailId,
			String fldNewAssignDetailId, Date fldExchangeDate,
			Date fldOldTaskDate, Date fldNewTaskDate,
			Integer fldExchangeNumber, String fldOldCallUserNo,
			String fldNewCallUserNo, Integer fldOldBeforeNumber,
			Integer fldOldAfterNumber, Integer fldNewBeforeNumber,
			Integer fldNewAfterNumber, String fldComment,
			String fldOperateUserNo, Date fldOperateDate,
			String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldOldAssignDetailId = fldOldAssignDetailId;
		this.fldNewAssignDetailId = fldNewAssignDetailId;
		this.fldExchangeDate = fldExchangeDate;
		this.fldOldTaskDate = fldOldTaskDate;
		this.fldNewTaskDate = fldNewTaskDate;
		this.fldExchangeNumber = fldExchangeNumber;
		this.fldOldCallUserNo = fldOldCallUserNo;
		this.fldNewCallUserNo = fldNewCallUserNo;
		this.fldOldBeforeNumber = fldOldBeforeNumber;
		this.fldOldAfterNumber = fldOldAfterNumber;
		this.fldNewBeforeNumber = fldNewBeforeNumber;
		this.fldNewAfterNumber = fldNewAfterNumber;
		this.fldComment = fldComment;
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

	@Column(name = "FLDOLDASSIGNDETAILID")
	public String getFldOldAssignDetailId() {
		return this.fldOldAssignDetailId;
	}

	public void setFldOldAssignDetailId(String fldOldAssignDetailId) {
		this.fldOldAssignDetailId = fldOldAssignDetailId;
	}

	@Column(name = "FLDNEWASSIGNDETAILID")
	public String getFldNewAssignDetailId() {
		return this.fldNewAssignDetailId;
	}

	public void setFldNewAssignDetailId(String fldNewAssignDetailId) {
		this.fldNewAssignDetailId = fldNewAssignDetailId;
	}

	@Column(name = "FLDEXCHANGEDATE")
	public Date getFldExchangeDate() {
		return this.fldExchangeDate;
	}

	public void setFldExchangeDate(Date fldExchangeDate) {
		this.fldExchangeDate = fldExchangeDate;
	}

	@Column(name = "FLDOLDTASKDATE")
	public Date getFldOldTaskDate() {
		return this.fldOldTaskDate;
	}

	public void setFldOldTaskDate(Date fldOldTaskDate) {
		this.fldOldTaskDate = fldOldTaskDate;
	}

	@Column(name = "FLDNEWTASKDATE")
	public Date getFldNewTaskDate() {
		return this.fldNewTaskDate;
	}

	public void setFldNewTaskDate(Date fldNewTaskDate) {
		this.fldNewTaskDate = fldNewTaskDate;
	}

	@Column(name = "FLDEXCHANGENUMBER")
	public Integer getFldExchangeNumber() {
		return this.fldExchangeNumber;
	}

	public void setFldExchangeNumber(Integer fldExchangeNumber) {
		this.fldExchangeNumber = fldExchangeNumber;
	}

	@Column(name = "FLDOLDCALLUSERNO")
	public String getFldOldCallUserNo() {
		return this.fldOldCallUserNo;
	}

	public void setFldOldCallUserNo(String fldOldCallUserNo) {
		this.fldOldCallUserNo = fldOldCallUserNo;
	}

	@Column(name = "FLDNEWCALLUSERNO")
	public String getFldNewCallUserNo() {
		return this.fldNewCallUserNo;
	}

	public void setFldNewCallUserNo(String fldNewCallUserNo) {
		this.fldNewCallUserNo = fldNewCallUserNo;
	}

	@Column(name = "FLDOLDBEFORENUMBER")
	public Integer getFldOldBeforeNumber() {
		return this.fldOldBeforeNumber;
	}

	public void setFldOldBeforeNumber(Integer fldOldBeforeNumber) {
		this.fldOldBeforeNumber = fldOldBeforeNumber;
	}

	@Column(name = "FLDOLDAFTERNUMBER")
	public Integer getFldOldAfterNumber() {
		return this.fldOldAfterNumber;
	}

	public void setFldOldAfterNumber(Integer fldOldAfterNumber) {
		this.fldOldAfterNumber = fldOldAfterNumber;
	}

	@Column(name = "FLDNEWBEFORENUMBER")
	public Integer getFldNewBeforeNumber() {
		return this.fldNewBeforeNumber;
	}

	public void setFldNewBeforeNumber(Integer fldNewBeforeNumber) {
		this.fldNewBeforeNumber = fldNewBeforeNumber;
	}

	@Column(name = "FLDNEWAFTERNUMBER")
	public Integer getFldNewAfterNumber() {
		return this.fldNewAfterNumber;
	}

	public void setFldNewAfterNumber(Integer fldNewAfterNumber) {
		this.fldNewAfterNumber = fldNewAfterNumber;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
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