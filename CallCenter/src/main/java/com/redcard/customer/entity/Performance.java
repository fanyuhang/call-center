package com.redcard.customer.entity;

import java.io.Serializable;
import java.util.Date;

public class Performance implements Serializable {
	private String fldId;
	private String fldFinancialUserNo;
	private String fldSignDate;
	private String fldFullName;
	private Integer fldClearDays;
	private Double fldPurchaseMoney;
	private Double fldPerformanceRadio;
	private Double fldPerformanceMoney;
    private String fldAnnualizedRate;
	
	public String getFldId() {
		return fldId;
	}
	public void setFldId(String fldId) {
		this.fldId = fldId;
	}
	public String getFldFinancialUserNo() {
		return fldFinancialUserNo;
	}
	public void setFldFinancialUserNo(String fldFinancialUserNo) {
		this.fldFinancialUserNo = fldFinancialUserNo;
	}
	public String getFldSignDate() {
		return fldSignDate;
	}
	public void setFldSignDate(String fldSignDate) {
		this.fldSignDate = fldSignDate;
	}
	public String getFldFullName() {
		return fldFullName;
	}
	public void setFldFullName(String fldFullName) {
		this.fldFullName = fldFullName;
	}
	public Integer getFldClearDays() {
		return fldClearDays;
	}
	public void setFldClearDays(Integer fldClearDays) {
		this.fldClearDays = fldClearDays;
	}
	public Double getFldPurchaseMoney() {
		return fldPurchaseMoney;
	}
	public void setFldPurchaseMoney(Double fldPurchaseMoney) {
		this.fldPurchaseMoney = fldPurchaseMoney;
	}
	public Double getFldPerformanceRadio() {
		return fldPerformanceRadio;
	}
	public void setFldPerformanceRadio(Double fldPerformanceRadio) {
		this.fldPerformanceRadio = fldPerformanceRadio;
	}
	public Double getFldPerformanceMoney() {
		return fldPerformanceMoney;
	}
	public void setFldPerformanceMoney(Double fldPerformanceMoney) {
		this.fldPerformanceMoney = fldPerformanceMoney;
	}

    public String getFldAnnualizedRate() {
        return fldAnnualizedRate;
    }

    public void setFldAnnualizedRate(String fldAnnualizedRate) {
        this.fldAnnualizedRate = fldAnnualizedRate;
    }
}
