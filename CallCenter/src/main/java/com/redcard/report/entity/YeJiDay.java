package com.redcard.report.entity;

import com.common.core.util.JsonDateSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by thinkpad on 14-8-5.
 */
public class YeJiDay {

    private Date reportDate;
    private String deptName;
    private String userName;
    private BigDecimal totalMoney;

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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
