package com.redcard.email.entity;

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
 * EmailOperate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLEMAILOPERATE")
public class EmailOperate implements java.io.Serializable {

	private static final long serialVersionUID = 5184800598309997560L;
	// Fields
	private String fldId;
	private String fldEmailTemplateId;
	private Date fldSendDate;
	private Integer fldEmailNum;
	private String fldSenderEmail;
	private String fldContent;
	private String fldAttachPath;
	private String fldTitle;
	private String fldReceiverEmails;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public EmailOperate() {
	}

	/** minimal constructor */
	public EmailOperate(String fldId) {
		this.fldId = fldId;
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

	@Column(name = "FLDEMAILTEMPLATEID")
	public String getFldEmailTemplateId() {
		return fldEmailTemplateId;
	}

	public void setFldEmailTemplateId(String fldEmailTemplateId) {
		this.fldEmailTemplateId = fldEmailTemplateId;
	}

	@Column(name = "FLDSENDDATE")
	public Date getFldSendDate() {
		return fldSendDate;
	}

	public void setFldSendDate(Date fldSendDate) {
		this.fldSendDate = fldSendDate;
	}

	@Column(name = "FLDEMAILNUM")
	public Integer getFldEmailNum() {
		return fldEmailNum;
	}

	public void setFldEmailNum(Integer fldEmailNum) {
		this.fldEmailNum = fldEmailNum;
	}

	@Column(name = "FLDSENDEREMAIL")
	public String getFldSenderEmail() {
		return fldSenderEmail;
	}

	public void setFldSenderEmail(String fldSenderEmail) {
		this.fldSenderEmail = fldSenderEmail;
	}

	@Column(name = "FLDCONTENT")
	public String getFldContent() {
		return fldContent;
	}

	public void setFldContent(String fldContent) {
		this.fldContent = fldContent;
	}

	@Column(name = "FLDATTACHPATH")
	public String getFldAttachPath() {
		return fldAttachPath;
	}

	public void setFldAttachPath(String fldAttachPath) {
		this.fldAttachPath = fldAttachPath;
	}

	@Column(name = "FLDTITLE")
	public String getFldTitle() {
		return fldTitle;
	}

	public void setFldTitle(String fldTitle) {
		this.fldTitle = fldTitle;
	}

	@Column(name = "FLDRECEIVEREMAILS")
	public String getFldReceiverEmails() {
		return fldReceiverEmails;
	}

	public void setFldReceiverEmails(String fldReceiverEmails) {
		this.fldReceiverEmails = fldReceiverEmails;
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

	protected EmailTemplate emailTemplate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLDEMAILTEMPLATEID", referencedColumnName = "FLDID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	@Transient
	private String emailTemplateName;

	@Transient
	public String getEmailTemplateName() {
		return emailTemplate != null ? emailTemplate.getFldName() : StringUtils.EMPTY;
	}

	@Transient
	private String emailTemplateContent;

	@Transient
	public String getEmailTemplateContent() {
		return emailTemplate != null ? emailTemplate.getFldContent() : StringUtils.EMPTY;
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