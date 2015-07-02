package com.redcard.report.entity;

import com.common.core.util.JsonDateSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by thinkpad on 14-8-5.
 */
public class CallOutDayImport {

    private Date reportDate;
    private String deptName;
    private String userName;
    private String importName;
    private Integer totalCallLong;
    private Integer totalCutomerNum;
    private Integer totalCallNum;
    private Integer totalValidCallLong;
    private Integer totalValidCallNum;
    private Integer item0;
    private Integer item3;
    private Integer item5;
    private Integer item9;
    private Integer item10;
    private Integer count0;
    private Integer count3;
    private Integer count5;
    private Integer count9;
    private Integer count10;

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

    public Integer getItem0() {
        return item0;
    }

    public void setItem0(Integer item0) {
        this.item0 = item0;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Integer getItem5() {
        return item5;
    }

    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public Integer getItem9() {
        return item9;
    }

    public void setItem9(Integer item9) {
        this.item9 = item9;
    }

    public Integer getItem10() {
        return item10;
    }

    public void setItem10(Integer item10) {
        this.item10 = item10;
    }

    public Integer getCount0() {
        return count0;
    }

    public void setCount0(Integer count0) {
        this.count0 = count0;
    }

    public Integer getCount3() {
        return count3;
    }

    public void setCount3(Integer count3) {
        this.count3 = count3;
    }

    public Integer getCount5() {
        return count5;
    }

    public void setCount5(Integer count5) {
        this.count5 = count5;
    }

    public Integer getCount9() {
        return count9;
    }

    public void setCount9(Integer count9) {
        this.count9 = count9;
    }

    public Integer getCount10() {
        return count10;
    }

    public void setCount10(Integer count10) {
        this.count10 = count10;
    }

    public String getImportName() {
        return importName;
    }

    public void setImportName(String importName) {
        this.importName = importName;
    }
}
