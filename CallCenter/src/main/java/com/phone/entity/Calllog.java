package com.phone.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by thinkpad on 14-8-3.
 */
@Entity
public class Calllog {
    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String callerid;

    @Basic
    @javax.persistence.Column(name = "callerid")
    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    private String calleeid;

    @Basic
    @javax.persistence.Column(name = "calleeid")
    public String getCalleeid() {
        return calleeid;
    }

    public void setCalleeid(String calleeid) {
        this.calleeid = calleeid;
    }

    private int channel;

    @Basic
    @javax.persistence.Column(name = "channel")
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    private int io;

    @Basic
    @javax.persistence.Column(name = "IO")
    public int getIo() {
        return io;
    }

    public void setIo(int io) {
        this.io = io;
    }

    private Timestamp inboundCallTime;

    @Basic
    @javax.persistence.Column(name = "InboundCallTime")
    public Timestamp getInboundCallTime() {
        return inboundCallTime;
    }

    public void setInboundCallTime(Timestamp inboundCallTime) {
        this.inboundCallTime = inboundCallTime;
    }

    private Timestamp inQueueTime;

    @Basic
    @javax.persistence.Column(name = "InQueueTime")
    public Timestamp getInQueueTime() {
        return inQueueTime;
    }

    public void setInQueueTime(Timestamp inQueueTime) {
        this.inQueueTime = inQueueTime;
    }

    private Timestamp outQueueTime;

    @Basic
    @javax.persistence.Column(name = "OutQueueTime")
    public Timestamp getOutQueueTime() {
        return outQueueTime;
    }

    public void setOutQueueTime(Timestamp outQueueTime) {
        this.outQueueTime = outQueueTime;
    }

    private Integer queueDuration;

    @Basic
    @javax.persistence.Column(name = "QueueDuration")
    public Integer getQueueDuration() {
        return queueDuration;
    }

    public void setQueueDuration(Integer queueDuration) {
        this.queueDuration = queueDuration;
    }

    private Timestamp startRingTime;

    @Basic
    @javax.persistence.Column(name = "StartRingTime")
    public Timestamp getStartRingTime() {
        return startRingTime;
    }

    public void setStartRingTime(Timestamp startRingTime) {
        this.startRingTime = startRingTime;
    }

    private Timestamp answeredTime;

    @Basic
    @javax.persistence.Column(name = "AnsweredTime")
    public Timestamp getAnsweredTime() {
        return answeredTime;
    }

    public void setAnsweredTime(Timestamp answeredTime) {
        this.answeredTime = answeredTime;
    }

    private Timestamp hangUpTime;

    @Basic
    @javax.persistence.Column(name = "HangUpTime")
    public Timestamp getHangUpTime() {
        return hangUpTime;
    }

    public void setHangUpTime(Timestamp hangUpTime) {
        this.hangUpTime = hangUpTime;
    }

    private Integer talkDuration;

    @Basic
    @javax.persistence.Column(name = "TalkDuration")
    public Integer getTalkDuration() {
        return talkDuration;
    }

    public void setTalkDuration(Integer talkDuration) {
        this.talkDuration = talkDuration;
    }

    private Integer waitTime;

    @Basic
    @javax.persistence.Column(name = "WaitTime")
    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    private int answerFlag;

    @Basic
    @javax.persistence.Column(name = "AnswerFlag")
    public int getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(int answerFlag) {
        this.answerFlag = answerFlag;
    }

    private int isFlashed;

    @Basic
    @javax.persistence.Column(name = "IsFlashed")
    public int getIsFlashed() {
        return isFlashed;
    }

    public void setIsFlashed(int isFlashed) {
        this.isFlashed = isFlashed;
    }

    private String extno;

    @Basic
    @javax.persistence.Column(name = "Extno")
    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }

    private String agentId;

    @Basic
    @javax.persistence.Column(name = "AgentId")
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    private Integer agentGrp;

    @Basic
    @javax.persistence.Column(name = "AgentGrp")
    public Integer getAgentGrp() {
        return agentGrp;
    }

    public void setAgentGrp(Integer agentGrp) {
        this.agentGrp = agentGrp;
    }

    private Integer totalDuration;

    @Basic
    @javax.persistence.Column(name = "TotalDuration")
    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    private String orgCalleeid;

    @Basic
    @javax.persistence.Column(name = "OrgCalleeid")
    public String getOrgCalleeid() {
        return orgCalleeid;
    }

    public void setOrgCalleeid(String orgCalleeid) {
        this.orgCalleeid = orgCalleeid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Calllog calllog = (Calllog) o;

        if (answerFlag != calllog.answerFlag) return false;
        if (channel != calllog.channel) return false;
        if (id != calllog.id) return false;
        if (io != calllog.io) return false;
        if (isFlashed != calllog.isFlashed) return false;
        if (agentGrp != null ? !agentGrp.equals(calllog.agentGrp) : calllog.agentGrp != null) return false;
        if (agentId != null ? !agentId.equals(calllog.agentId) : calllog.agentId != null) return false;
        if (answeredTime != null ? !answeredTime.equals(calllog.answeredTime) : calllog.answeredTime != null)
            return false;
        if (calleeid != null ? !calleeid.equals(calllog.calleeid) : calllog.calleeid != null) return false;
        if (callerid != null ? !callerid.equals(calllog.callerid) : calllog.callerid != null) return false;
        if (extno != null ? !extno.equals(calllog.extno) : calllog.extno != null) return false;
        if (hangUpTime != null ? !hangUpTime.equals(calllog.hangUpTime) : calllog.hangUpTime != null) return false;
        if (inQueueTime != null ? !inQueueTime.equals(calllog.inQueueTime) : calllog.inQueueTime != null) return false;
        if (inboundCallTime != null ? !inboundCallTime.equals(calllog.inboundCallTime) : calllog.inboundCallTime != null)
            return false;
        if (orgCalleeid != null ? !orgCalleeid.equals(calllog.orgCalleeid) : calllog.orgCalleeid != null) return false;
        if (outQueueTime != null ? !outQueueTime.equals(calllog.outQueueTime) : calllog.outQueueTime != null)
            return false;
        if (queueDuration != null ? !queueDuration.equals(calllog.queueDuration) : calllog.queueDuration != null)
            return false;
        if (startRingTime != null ? !startRingTime.equals(calllog.startRingTime) : calllog.startRingTime != null)
            return false;
        if (talkDuration != null ? !talkDuration.equals(calllog.talkDuration) : calllog.talkDuration != null)
            return false;
        if (totalDuration != null ? !totalDuration.equals(calllog.totalDuration) : calllog.totalDuration != null)
            return false;
        if (waitTime != null ? !waitTime.equals(calllog.waitTime) : calllog.waitTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (callerid != null ? callerid.hashCode() : 0);
        result = 31 * result + (calleeid != null ? calleeid.hashCode() : 0);
        result = 31 * result + channel;
        result = 31 * result + io;
        result = 31 * result + (inboundCallTime != null ? inboundCallTime.hashCode() : 0);
        result = 31 * result + (inQueueTime != null ? inQueueTime.hashCode() : 0);
        result = 31 * result + (outQueueTime != null ? outQueueTime.hashCode() : 0);
        result = 31 * result + (queueDuration != null ? queueDuration.hashCode() : 0);
        result = 31 * result + (startRingTime != null ? startRingTime.hashCode() : 0);
        result = 31 * result + (answeredTime != null ? answeredTime.hashCode() : 0);
        result = 31 * result + (hangUpTime != null ? hangUpTime.hashCode() : 0);
        result = 31 * result + (talkDuration != null ? talkDuration.hashCode() : 0);
        result = 31 * result + (waitTime != null ? waitTime.hashCode() : 0);
        result = 31 * result + answerFlag;
        result = 31 * result + isFlashed;
        result = 31 * result + (extno != null ? extno.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        result = 31 * result + (agentGrp != null ? agentGrp.hashCode() : 0);
        result = 31 * result + (totalDuration != null ? totalDuration.hashCode() : 0);
        result = 31 * result + (orgCalleeid != null ? orgCalleeid.hashCode() : 0);
        return result;
    }
}
