package com.phone.entity;

import com.common.core.util.JsonTimestampSerializer;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by thinkpad on 15-1-18.
 */
@Entity
@Table(name = "STATRECORD")
public class Statrecord implements Serializable {

    private static final long serialVersionUID = 8940016927409806920L;
    private Long id;
    private Integer channel;
    private Integer status;
    private Date starttime;
    private Date endtime;
    private String extno;
    private String uid;
    private Integer callid;
    private String timeLong;
    private String deptName;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CHANNEL")
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(name = "STARTTIME")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @Basic
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(name = "ENDTIME")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "EXTNO")
    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }

    @Basic
    @Column(name = "UID")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "CALLID")
    public Integer getCallid() {
        return callid;
    }

    public void setCallid(Integer callid) {
        this.callid = callid;
    }

    private StatRecordValue statRecordValue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS", referencedColumnName = "STATUSVALUE", insertable = false, updatable = false)
    public StatRecordValue getStatRecordValue() {
        return statRecordValue;
    }

    public void setStatRecordValue(StatRecordValue statRecordValue) {
        this.statRecordValue = statRecordValue;
    }

    private String statusName;

    @Transient
    public String getStatusName() {
        if (StringUtils.isNotBlank(statusName)) {
            return statusName;
        }
        return statRecordValue == null ? null : statRecordValue.getStatusName();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    protected Operator operateUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID", referencedColumnName = "UID", insertable = false, updatable = false)
    public Operator getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Operator operateUser) {
        this.operateUser = operateUser;
    }

    private String operateUserName;

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    @Transient
    public String getOperateUserName() {
        if (StringUtils.isNotBlank(operateUserName)) {
            return operateUserName;
        }
        return operateUser != null ? operateUser.getName() : "";
    }

    @Transient
    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    @Transient
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statrecord that = (Statrecord) o;

        if (callid != null ? !callid.equals(that.callid) : that.callid != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (extno != null ? !extno.equals(that.extno) : that.extno != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (extno != null ? extno.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (callid != null ? callid.hashCode() : 0);
        return result;
    }
}
