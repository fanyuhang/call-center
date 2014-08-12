package com.redcard.ws.entity;

import java.util.Date;

/**
 * Created by thinkpad on 14-7-20.
 */
public class CustomerContract {
    private String customerName;
    private String idCard;
    private String mobile;
    private String phone;
    private String productName;
    private String productType;
    private Double annualizedRate;
    private Integer productDeadLine;
    private String deadLineUnit;
    private Double purchaseMoney;
    private String dueDate;
    private String signDate;
    private String moneyDate;
    private Double annualizedMoney;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getAnnualizedRate() {
        return annualizedRate;
    }

    public void setAnnualizedRate(Double annualizedRate) {
        this.annualizedRate = annualizedRate;
    }

    public Integer getProductDeadLine() {
        return productDeadLine;
    }

    public void setProductDeadLine(Integer productDeadLine) {
        this.productDeadLine = productDeadLine;
    }

    public String getDeadLineUnit() {
        return deadLineUnit;
    }

    public void setDeadLineUnit(String deadLineUnit) {
        this.deadLineUnit = deadLineUnit;
    }

    public Double getPurchaseMoney() {
        return purchaseMoney;
    }

    public void setPurchaseMoney(Double purchaseMoney) {
        this.purchaseMoney = purchaseMoney;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(String moneyDate) {
        this.moneyDate = moneyDate;
    }

    public Double getAnnualizedMoney() {
        return annualizedMoney;
    }

    public void setAnnualizedMoney(Double annualizedMoney) {
        this.annualizedMoney = annualizedMoney;
    }
}
