package com.phone.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by thinkpad on 14-8-3.
 */
@Entity
public class Talklog {
    private long id;
    private Timestamp datetime;
    private String uid;
    private String usercode;
    private String filename;
    private Integer timelong;
    private String callerid;
    private String ip;
    private Integer ch;
    private Boolean istalk;
    private Integer ringtime;
    private Boolean deleted;
    private Long callid;
    private String memo;
    private Integer grpno;
    private String compname;
    private String iispath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "usercode")
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "timelong")
    public Integer getTimelong() {
        return timelong;
    }

    public void setTimelong(Integer timelong) {
        this.timelong = timelong;
    }

    @Basic
    @Column(name = "callerid")
    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "ch")
    public Integer getCh() {
        return ch;
    }

    public void setCh(Integer ch) {
        this.ch = ch;
    }

    @Basic
    @Column(name = "istalk")
    public Boolean getIstalk() {
        return istalk;
    }

    public void setIstalk(Boolean istalk) {
        this.istalk = istalk;
    }

    @Basic
    @Column(name = "ringtime")
    public Integer getRingtime() {
        return ringtime;
    }

    public void setRingtime(Integer ringtime) {
        this.ringtime = ringtime;
    }

    @Basic
    @Column(name = "deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "callid")
    public Long getCallid() {
        return callid;
    }

    public void setCallid(Long callid) {
        this.callid = callid;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "grpno")
    public Integer getGrpno() {
        return grpno;
    }

    public void setGrpno(Integer grpno) {
        this.grpno = grpno;
    }

    @Basic
    @Column(name = "compname")
    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    @Basic
    @Column(name = "iispath")
    public String getIispath() {
        return iispath;
    }

    public void setIispath(String iispath) {
        this.iispath = iispath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talklog talklog = (Talklog) o;

        if (id != talklog.id) return false;
        if (callerid != null ? !callerid.equals(talklog.callerid) : talklog.callerid != null) return false;
        if (callid != null ? !callid.equals(talklog.callid) : talklog.callid != null) return false;
        if (ch != null ? !ch.equals(talklog.ch) : talklog.ch != null) return false;
        if (compname != null ? !compname.equals(talklog.compname) : talklog.compname != null) return false;
        if (datetime != null ? !datetime.equals(talklog.datetime) : talklog.datetime != null) return false;
        if (deleted != null ? !deleted.equals(talklog.deleted) : talklog.deleted != null) return false;
        if (filename != null ? !filename.equals(talklog.filename) : talklog.filename != null) return false;
        if (grpno != null ? !grpno.equals(talklog.grpno) : talklog.grpno != null) return false;
        if (iispath != null ? !iispath.equals(talklog.iispath) : talklog.iispath != null) return false;
        if (ip != null ? !ip.equals(talklog.ip) : talklog.ip != null) return false;
        if (istalk != null ? !istalk.equals(talklog.istalk) : talklog.istalk != null) return false;
        if (memo != null ? !memo.equals(talklog.memo) : talklog.memo != null) return false;
        if (ringtime != null ? !ringtime.equals(talklog.ringtime) : talklog.ringtime != null) return false;
        if (timelong != null ? !timelong.equals(talklog.timelong) : talklog.timelong != null) return false;
        if (uid != null ? !uid.equals(talklog.uid) : talklog.uid != null) return false;
        if (usercode != null ? !usercode.equals(talklog.usercode) : talklog.usercode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (usercode != null ? usercode.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (timelong != null ? timelong.hashCode() : 0);
        result = 31 * result + (callerid != null ? callerid.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (ch != null ? ch.hashCode() : 0);
        result = 31 * result + (istalk != null ? istalk.hashCode() : 0);
        result = 31 * result + (ringtime != null ? ringtime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (callid != null ? callid.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (grpno != null ? grpno.hashCode() : 0);
        result = 31 * result + (compname != null ? compname.hashCode() : 0);
        result = 31 * result + (iispath != null ? iispath.hashCode() : 0);
        return result;
    }
}
