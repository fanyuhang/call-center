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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * MessageTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLMESSAGETEMPLATE")
public class MessageTemplate implements java.io.Serializable {

	private static final long serialVersionUID = 9083002786919450651L;

	// Fields
	private String fldId;
	private String fldName;
	private String fldContent;
	private Integer fldStatus;
	private String fldComment;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;

	// Constructors

	/** default constructor */
	public MessageTemplate() {
	}

	/** minimal constructor */
	public MessageTemplate(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public MessageTemplate(String fldId, String fldName, String fldContent, Integer fldStatus, String fldComment,
			String fldOperateUserNo, Date fldOperateDate, String fldCreateUserNo, Date fldCreateDate) {
		this.fldId = fldId;
		this.fldName = fldName;
		this.fldContent = fldContent;
		this.fldStatus = fldStatus;
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

	@Column(name = "FLDNAME")
	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	@Column(name = "FLDCONTENT")
	public String getFldContent() {
		return fldContent;
	}

	public void setFldContent(String fldContent) {
		this.fldContent = fldContent;
	}

	@Column(name = "FLDSTATUS")
	public Integer getFldStatus() {
		return this.fldStatus;
	}

	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
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