package com.phone.entity;

import java.util.Date;

/**
 * Created by thinkpad on 14-8-4.
 */
public class ExtMonitor {
    private Integer nPos;
    private Integer nStatus;
    private String szExtension;
    private String szRxDTMF;
    private String szPhoneNumber;
    private String szAgentID;
    private String szAgentName;
    private Long nStatusTime;
    private Date startTime;
    private Integer nCh;

    public Integer getnPos() {
        return nPos;
    }

    public void setnPos(Integer nPos) {
        this.nPos = nPos;
    }

    public Integer getnStatus() {
        return nStatus;
    }

    public void setnStatus(Integer nStatus) {
        this.nStatus = nStatus;
    }

    public String getSzExtension() {
        return szExtension;
    }

    public void setSzExtension(String szExtension) {
        this.szExtension = szExtension;
    }

    public String getSzRxDTMF() {
        return szRxDTMF;
    }

    public void setSzRxDTMF(String szRxDTMF) {
        this.szRxDTMF = szRxDTMF;
    }

    public String getSzPhoneNumber() {
        return szPhoneNumber;
    }

    public void setSzPhoneNumber(String szPhoneNumber) {
        this.szPhoneNumber = szPhoneNumber;
    }

    public String getSzAgentID() {
        return szAgentID;
    }

    public void setSzAgentID(String szAgentID) {
        this.szAgentID = szAgentID;
    }

    public String getSzAgentName() {
        return szAgentName;
    }

    public void setSzAgentName(String szAgentName) {
        this.szAgentName = szAgentName;
    }

    public Long getnStatusTime() {
        return nStatusTime;
    }

    public void setnStatusTime(Long nStatusTime) {
        this.nStatusTime = nStatusTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getnCh() {
        return nCh;
    }

    public void setnCh(Integer nCh) {
        this.nCh = nCh;
    }
}
