package com.redcard.telephone.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * TelephoneRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONERECORD")
public class TelephoneRecord implements java.io.Serializable {

	// Fields

	private Long fldId;
	private Long fldTaskId;
	private String fldCustomerName;
	private String fldPhone;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fldCallDate;
	private Integer fldCallType;
	private Integer fldResultType;
	private String fldComment;
	private Integer fldCallLong;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fldCallBeginTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fldCallEndTime;
	private String fldRecordFilePath;
	private String fldCallButtons;
	private String fldChannelNo;
	private Integer fldAuditStatus;
	private String fldAuditComment;
	private Integer fldAuditFraction;
	private String fldAuditUserNo;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;
	
	private String callId;

    private Integer fldTotalDuration;
    private Integer fldWaitTime;
    private Integer fldAnswerFlag;

	// Constructors

	/** default constructor */
	public TelephoneRecord() {
	}

	/** minimal constructor */
	public TelephoneRecord(Long fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneRecord(Long fldId, Long fldTaskId, String fldCustomerName,
			String fldPhone, Date fldCallDate, Integer fldCallType,
			Integer fldResultType, String fldComment, Integer fldCallLong,
			Date fldCallBeginTime, Date fldCallEndTime,
			String fldRecordFilePath, String fldCallButtons,
			String fldChannelNo, Integer fldAuditStatus,
			String fldAuditComment, Integer fldAuditFraction,
			String fldAuditUserNo, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldTaskId = fldTaskId;
		this.fldCustomerName = fldCustomerName;
		this.fldPhone = fldPhone;
		this.fldCallDate = fldCallDate;
		this.fldCallType = fldCallType;
		this.fldResultType = fldResultType;
		this.fldComment = fldComment;
		this.fldCallLong = fldCallLong;
		this.fldCallBeginTime = fldCallBeginTime;
		this.fldCallEndTime = fldCallEndTime;
		this.fldRecordFilePath = fldRecordFilePath;
		this.fldCallButtons = fldCallButtons;
		this.fldChannelNo = fldChannelNo;
		this.fldAuditStatus = fldAuditStatus;
		this.fldAuditComment = fldAuditComment;
		this.fldAuditFraction = fldAuditFraction;
		this.fldAuditUserNo = fldAuditUserNo;
		this.fldOperateUserNo = fldOperateUserNo;
		this.fldOperateDate = fldOperateDate;
		this.fldCreateUserNo = fldCreateUserNo;
		this.fldCreateDate = fldCreateDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FLDID", unique = true, nullable = false)
	public Long getFldId() {
		return this.fldId;
	}

	public void setFldId(Long fldId) {
		this.fldId = fldId;
	}

	@Column(name = "FLDTASKID")
	public Long getFldTaskId() {
		return this.fldTaskId;
	}

	public void setFldTaskId(Long fldTaskId) {
		this.fldTaskId = fldTaskId;
	}

	@Column(name = "FLDCUSTOMERNAME")
	public String getFldCustomerName() {
		return this.fldCustomerName;
	}

	public void setFldCustomerName(String fldCustomerName) {
		this.fldCustomerName = fldCustomerName;
	}

	@Column(name = "FLDPHONE")
	public String getFldPhone() {
		return this.fldPhone;
	}

	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}

	@Column(name = "FLDCALLDATE")
	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Date getFldCallDate() {
		return this.fldCallDate;
	}

	public void setFldCallDate(Date fldCallDate) {
		this.fldCallDate = fldCallDate;
	}

	@Column(name = "FLDCALLTYPE")
	public Integer getFldCallType() {
		return this.fldCallType;
	}

	public void setFldCallType(Integer fldCallType) {
		this.fldCallType = fldCallType;
	}

	@Column(name = "FLDRESULTTYPE")
	public Integer getFldResultType() {
		return this.fldResultType;
	}

	public void setFldResultType(Integer fldResultType) {
		this.fldResultType = fldResultType;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
	}

	@Column(name = "FLDCALLLONG")
	public Integer getFldCallLong() {
		return this.fldCallLong;
	}

	public void setFldCallLong(Integer fldCallLong) {
		this.fldCallLong = fldCallLong;
	}

	@Column(name = "FLDCALLBEGINTIME")
	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Date getFldCallBeginTime() {
		return this.fldCallBeginTime;
	}

	public void setFldCallBeginTime(Date fldCallBeginTime) {
		this.fldCallBeginTime = fldCallBeginTime;
	}

	@Column(name = "FLDCALLENDTIME")
	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Date getFldCallEndTime() {
		return this.fldCallEndTime;
	}

	public void setFldCallEndTime(Date fldCallEndTime) {
		this.fldCallEndTime = fldCallEndTime;
	}

	@Column(name = "FLDRECORDFILEPATH")
	public String getFldRecordFilePath() {
		return this.fldRecordFilePath;
	}

	public void setFldRecordFilePath(String fldRecordFilePath) {
		this.fldRecordFilePath = fldRecordFilePath;
	}

	@Column(name = "FLDCALLBUTTONS")
	public String getFldCallButtons() {
		return this.fldCallButtons;
	}

	public void setFldCallButtons(String fldCallButtons) {
		this.fldCallButtons = fldCallButtons;
	}

	@Column(name = "FLDCHANNELNO")
	public String getFldChannelNo() {
		return this.fldChannelNo;
	}

	public void setFldChannelNo(String fldChannelNo) {
		this.fldChannelNo = fldChannelNo;
	}

	@Column(name = "FLDAUDITSTATUS")
	public Integer getFldAuditStatus() {
		return this.fldAuditStatus;
	}

	public void setFldAuditStatus(Integer fldAuditStatus) {
		this.fldAuditStatus = fldAuditStatus;
	}

	@Column(name = "FLDAUDITCOMMENT")
	public String getFldAuditComment() {
		return this.fldAuditComment;
	}

	public void setFldAuditComment(String fldAuditComment) {
		this.fldAuditComment = fldAuditComment;
	}

	@Column(name = "FLDAUDITFRACTION")
	public Integer getFldAuditFraction() {
		return this.fldAuditFraction;
	}

	public void setFldAuditFraction(Integer fldAuditFraction) {
		this.fldAuditFraction = fldAuditFraction;
	}

	@Column(name = "FLDAUDITUSERNO")
	public String getFldAuditUserNo() {
		return this.fldAuditUserNo;
	}

	public void setFldAuditUserNo(String fldAuditUserNo) {
		this.fldAuditUserNo = fldAuditUserNo;
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
	public Date getFldCreateDate() {
		return this.fldCreateDate;
	}

	public void setFldCreateDate(Date fldCreateDate) {
		this.fldCreateDate = fldCreateDate;
	}
	
	protected User callUser;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCREATEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
	public User getCallUser() {
		return callUser;
	}

	public void setCallUser(User callUser) {
		this.callUser = callUser;
	}
	
	@Transient
	private String callUserName;
	
	@Transient
	public String getCallUserName() {
		return null != callUser ? callUser.getUserName() : "";
	}

	@Transient
	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	@Transient
	private Integer fldTaskStatus;

	@Transient
	public Integer getFldTaskStatus() {
		return fldTaskStatus;
	}

	public void setFldTaskStatus(Integer fldTaskStatus) {
		this.fldTaskStatus = fldTaskStatus;
	}

    @Column(name = "FLDTOTALDURATION")
    public Integer getFldTotalDuration() {
        return fldTotalDuration;
    }

    public void setFldTotalDuration(Integer fldTotalDuration) {
        this.fldTotalDuration = fldTotalDuration;
    }

    @Column(name = "FLDWAITTIME")
    public Integer getFldWaitTime() {
        return fldWaitTime;
    }

    public void setFldWaitTime(Integer fldWaitTime) {
        this.fldWaitTime = fldWaitTime;
    }

    @Column(name = "FLDANSWERFLAG")
    public Integer getFldAnswerFlag() {
        return fldAnswerFlag;
    }

    public void setFldAnswerFlag(Integer fldAnswerFlag) {
        this.fldAnswerFlag = fldAnswerFlag;
    }
}