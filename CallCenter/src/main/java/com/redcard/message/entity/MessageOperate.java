package com.redcard.message.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * MessageOperate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLMESSAGEOPERATE")
public class MessageOperate implements java.io.Serializable {

	private static final long serialVersionUID = -9218079086538702798L;

	// Fields
	private String fldId;
	private String fldMessageTemplateId;
	private String fldContent;
	private Integer fldMessageNum;
	private String fldMobiles;
	private String fldComment;
	private Integer fldSendStatus;
	private String fldSendResult;
	private String fldTaskId;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public MessageOperate() {
	}

	/** minimal constructor */
	public MessageOperate(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public MessageOperate(String fldId, String fldMessageTemplateId, String fldContent, Integer fldMessageNum,
			String fldMobiles, String fldComment, Integer fldSendStatus, String fldSendResult, String fldTaskId,
			String fldOperateUserNo, Date fldOperateDate, String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldMessageTemplateId = fldMessageTemplateId;
		this.fldContent = fldContent;
		this.fldMessageNum = fldMessageNum;
		this.fldMobiles = fldMobiles;
		this.fldComment = fldComment;
		this.fldSendStatus = fldSendStatus;
		this.fldSendResult = fldSendResult;
		this.fldTaskId = fldTaskId;
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

	@Column(name = "FLDMESSAGETEMPLATEID")
	public String getFldMessageTemplateId() {
		return fldMessageTemplateId;
	}

	public void setFldMessageTemplateId(String fldMessageTemplateId) {
		this.fldMessageTemplateId = fldMessageTemplateId;
	}

	@Column(name = "FLDCONTENT")
	public String getFldContent() {
		return fldContent;
	}

	public void setFldContent(String fldContent) {
		this.fldContent = fldContent;
	}

	@Column(name = "FLDMESSAGENUM")
	public Integer getFldMessageNum() {
		return fldMessageNum;
	}

	public void setFldMessageNum(Integer fldMessageNum) {
		this.fldMessageNum = fldMessageNum;
	}

	@Column(name = "FLDMOBILES")
	public String getFldMobiles() {
		return fldMobiles;
	}

	public void setFldMobiles(String fldMobiles) {
		this.fldMobiles = fldMobiles;
	}

	@Column(name = "FLDCOMMENT")
	public String getFldComment() {
		return this.fldComment;
	}

	public void setFldComment(String fldComment) {
		this.fldComment = fldComment;
	}

	@Column(name = "FLDSENDSTATUS")
	public Integer getFldSendStatus() {
		return fldSendStatus;
	}

	public void setFldSendStatus(Integer fldSendStatus) {
		this.fldSendStatus = fldSendStatus;
	}

	@Column(name = "FLDSENDRESULT")
	public String getFldSendResult() {
		return fldSendResult;
	}

	public void setFldSendResult(String fldSendResult) {
		this.fldSendResult = fldSendResult;
	}

	@Column(name = "FLDTASKID")
	public String getFldTaskId() {
		return fldTaskId;
	}

	public void setFldTaskId(String fldTaskId) {
		this.fldTaskId = fldTaskId;
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

	protected MessageTemplate messageTemplate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLDMESSAGETEMPLATEID", referencedColumnName = "FLDID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public MessageTemplate getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(MessageTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	@Transient
	private String messageTemplateName;

	@Transient
	public String getMessageTemplateName() {
		return messageTemplate != null ? messageTemplate.getFldName() : StringUtils.EMPTY;
	}

	@Transient
	private String messageTemplateContent;

	@Transient
	public String getMessageTemplateContent() {
		return messageTemplate != null ? messageTemplate.getFldContent() : StringUtils.EMPTY;
	}

	protected User operateUser;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLDOPERATEUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
	public User getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}

	@Transient
	private String operateUserName;

	@Transient
	public String getOperateUserName() {
		return operateUser != null ? operateUser.getUserName() : "";
	}

	protected User createUser;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLDCREATEUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	@Transient
	private String createUserName;

	@Transient
	public String getCreateUserName() {
		return createUser != null ? createUser.getUserName() : "";
	}
}