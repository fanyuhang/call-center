package com.redcard.system.entity;

import com.common.Constant;
import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;
import com.common.core.util.JsonTimestampSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 12/5/12
 */
@Entity
@Table(name = "tblsysjob")
@Comment(value = "系统任务")
public class SysJob implements Serializable, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldjobname")
    private String jobName;

    @Column(name = "fldjobgroup")
    private String jobGroup;

    @Column(name = "fldtriggername")
    private String triggerName;

    @Column(name = "fldtriggergroup")
    private String triggerGroup;

    @Column(name = "fldcronexpression")
    private String cronExpression;  //定时任务运行表达式，仅用于cron trigger

    @Column(name = "fldintervalinseconds")
    private Integer intervalInSeconds;  //定时任务执行间隔时间，单位 秒，仅用于 simple trigger

    @Column(name = "fldrepeatcount")
    private Integer repeatCount;       //定时任务执行次数，0 表示 不限次数 ，仅用于 simple trigger

    @Column(name = "fldtriggertype")
    private Integer triggerType;       //定时任务类型，支持 cron trigger 和 simple trigger

    @Column(name = "fldjobclass")
    private String jobClass;       //任务执行类

    @Column(name = "fldstatus")
    private Integer status;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fldlastsuccessdate")
    private Date lastSuccessDate;

    @Column(name = "fldexecutecount")
    private Integer executeCount;

    @Column(name = "fldnextfiretime")
    private Date nextFireTime;

    @Column(name = "fldstarttime")
    private Date startTime;

    @Column(name = "fldendtime")
    private Date endTime;

    public SysJob() {
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(Integer intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTriggerType(Constant.TriggerType triggerType) {
        this.triggerType = triggerType.ordinal();
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getLastSuccessDate() {
        return lastSuccessDate;
    }

    public void setLastSuccessDate(Date lastSuccessDate) {
        this.lastSuccessDate = lastSuccessDate;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysJob sysJob = (SysJob) o;

        if (triggerType != sysJob.triggerType) return false;
        if (id != null ? !id.equals(sysJob.id) : sysJob.id != null) return false;
        if (jobGroup != null ? !jobGroup.equals(sysJob.jobGroup) : sysJob.jobGroup != null) return false;
        if (jobName != null ? !jobName.equals(sysJob.jobName) : sysJob.jobName != null) return false;
        if (triggerGroup != null ? !triggerGroup.equals(sysJob.triggerGroup) : sysJob.triggerGroup != null)
            return false;
        if (triggerName != null ? !triggerName.equals(sysJob.triggerName) : sysJob.triggerName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (jobName != null ? jobName.hashCode() : 0);
        result = 31 * result + (jobGroup != null ? jobGroup.hashCode() : 0);
        result = 31 * result + (triggerName != null ? triggerName.hashCode() : 0);
        result = 31 * result + (triggerGroup != null ? triggerGroup.hashCode() : 0);
        result = 31 * result + triggerType;
        return result;
    }
}
