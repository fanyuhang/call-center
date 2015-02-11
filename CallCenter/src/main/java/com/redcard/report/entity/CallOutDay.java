package com.redcard.report.entity;

import com.common.core.util.JsonDateSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by thinkpad on 14-8-5.
 */
public class CallOutDay {

    private Date reportDate;
    private String deptName;
    private String userName;
    private Integer totalCallLong;
    private Integer totalCutomerNum;
    private Integer totalCallNum;
    private Integer totalValidCallLong;
    private Integer totalValidCallNum;

    private String reportDateStr;

    public String getReportDateStr() {
        if(reportDate==null){
            return null;
        }
        return DateFormatUtils.format(reportDate,"yyyy-MM-dd");
    }

    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTotalCallLong() {
        return totalCallLong;
    }

    public void setTotalCallLong(Integer totalCallLong) {
        this.totalCallLong = totalCallLong;
    }

    public Integer getTotalCutomerNum() {
        return totalCutomerNum;
    }

    public void setTotalCutomerNum(Integer totalCutomerNum) {
        this.totalCutomerNum = totalCutomerNum;
    }

    public Integer getTotalCallNum() {
        return totalCallNum;
    }

    public void setTotalCallNum(Integer totalCallNum) {
        this.totalCallNum = totalCallNum;
    }

    public Integer getTotalValidCallLong() {
        return totalValidCallLong;
    }

    public void setTotalValidCallLong(Integer totalValidCallLong) {
        this.totalValidCallLong = totalValidCallLong;
    }

    public Integer getTotalValidCallNum() {
        return totalValidCallNum;
    }

    public void setTotalValidCallNum(Integer totalValidCallNum) {
        this.totalValidCallNum = totalValidCallNum;
    }
}
