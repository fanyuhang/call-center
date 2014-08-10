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
 * TelephoneAssign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLTELEPHONEASSIGN")
public class TelephoneAssign implements java.io.Serializable {

	// Fields

	private String fldId;
	private Integer fldSource;
	private Integer fldAssignNumber;
	private Integer fldCallUserNumber;
	private Integer fldAverageNumber;
	private Integer fldDayNumber;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fldBeginDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fldEndDate;
	private Integer fldContentType;
	private String fldComment;
	private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
	private Date fldOperateDate;
	private String fldCreateUserNo;
	private Date fldCreateDate;
	
	private String fldCallUserNo;
	private String importId;

	// Constructors

	/** default constructor */
	public TelephoneAssign() {
	}

	/** minimal constructor */
	public TelephoneAssign(String fldId) {
		this.fldId = fldId;
	}

	/** full constructor */
	public TelephoneAssign(String fldId, Integer fldSource,
			Integer fldAssignNumber, Integer fldCallUserNumber,
			Integer fldAverageNumber, Integer fldDayNumber,
			Date fldBeginDate, Date fldEndDate,
			Integer fldContentType, String fldComment, String fldOperateUserNo,
			Date fldOperateDate, String fldCreateUserNo,
			Date fldCreateDate) {
		this.fldId = fldId;
		this.fldSource = fldSource;
		this.fldAssignNumber = fldAssignNumber;
		this.fldCallUserNumber = fldCallUserNumber;
		this.fldAverageNumber = fldAverageNumber;
		this.fldDayNumber = fldDayNumber;
		this.fldBeginDate = fldBeginDate;
		this.fldEndDate = fldEndDate;
		this.fldContentType = fldContentType;
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

	@Column(name = "FLDSOURCE")
	public Integer getFldSource() {
		return this.fldSource;
	}

	public void setFldSource(Integer fldSource) {
		this.fldSource = fldSource;
	}

	@Column(name = "FLDASSIGNNUMBER")
	public Integer getFldAssignNumber() {
		return this.fldAssignNumber;
	}

	public void setFldAssignNumber(Integer fldAssignNumber) {
		this.fldAssignNumber = fldAssignNumber;
	}

	@Column(name = "FLDCALLUSERNUMBER")
	public Integer getFldCallUserNumber() {
		return this.fldCallUserNumber;
	}

	public void setFldCallUserNumber(Integer fldCallUserNumber) {
		this.fldCallUserNumber = fldCallUserNumber;
	}

	@Column(name = "FLDAVERAGENUMBER")
	public Integer getFldAverageNumber() {
		return this.fldAverageNumber;
	}

	public void setFldAverageNumber(Integer fldAverageNumber) {
		this.fldAverageNumber = fldAverageNumber;
	}

	@Column(name = "FLDDAYNUMBER")
	public Integer getFldDayNumber() {
		return this.fldDayNumber;
	}

	public void setFldDayNumber(Integer fldDayNumber) {
		this.fldDayNumber = fldDayNumber;
	}

	@Column(name = "FLDBEGINDATE")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getFldBeginDate() {
		return this.fldBeginDate;
	}

	public void setFldBeginDate(Date fldBeginDate) {
		this.fldBeginDate = fldBeginDate;
	}

	@Column(name = "FLDENDDATE")
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getFldEndDate() {
		return this.fldEndDate;
	}

	public void setFldEndDate(Date fldEndDate) {
		this.fldEndDate = fldEndDate;
	}

	@Column(name = "FLDCONTENTTYPE")
	public Integer getFldContentType() {
		return this.fldContentType;
	}

	public void setFldContentType(Integer fldContentType) {
		this.fldContentType = fldContentType;
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

	@Transient
	public String getFldCallUserNo() {
		return fldCallUserNo;
	}

	public void setFldCallUserNo(String fldCallUserNo) {
		this.fldCallUserNo = fldCallUserNo;
	}

	@Transient
	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
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

    private User createUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCREATEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @Transient
    public String getCreateUserName() {
        return createUser!=null ? createUser.getUserName() : "";
    }

}