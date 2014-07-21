package com.redcard.customer.entity;

import java.io.Serializable;

public class ImportEntity implements Serializable {
	//客户相关
	private String custName;//客户姓名
	private Integer source;//客户来源
	private String birthday;//出生日期
	private String phone;//联系方式
	private String identityNo;//身份证号码
	private String financialUserNo;//理财经理
	private String comment;//备注
	
	private String productName;//产品名称
	private String contractNo;//合同编号
	private String signDate;//合同签订日期
	private String establishDate;//成立日期
	private String valueDate;//起息日期
	private String dueDate;//到期日期
	private String clearDays;//实际天数
	private float purchaseMoney;//购买金额
	private float performanceRadio;//业绩系数
	private float performanceMoney;//业绩额度
	private float annualizedRate;//年化收益率
	private float annualizedMoney;//预期收益
	private String moneyDate;//打款日期
	private int fldCollectDays;//募集期天数
	private float fldDepositRate;//年化7天存款率
	private float fldCollectMoney;//募集期贴息
	private String bankNo;//银行卡号
	private String bankName;//开户银行
	private String cardMoney;//瑞得卡金额
	private Integer cardLevel;//瑞得卡等级
	private String fldCardNo;//瑞得卡卡号
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getClearDays() {
		return clearDays;
	}
	public void setClearDays(String clearDays) {
		this.clearDays = clearDays;
	}
	public float getPurchaseMoney() {
		return purchaseMoney;
	}
	public void setPurchaseMoney(float purchaseMoney) {
		this.purchaseMoney = purchaseMoney;
	}
	public float getPerformanceRadio() {
		return performanceRadio;
	}
	public void setPerformanceRadio(float performanceRadio) {
		this.performanceRadio = performanceRadio;
	}
	public float getPerformanceMoney() {
		return performanceMoney;
	}
	public void setPerformanceMoney(float performanceMoney) {
		this.performanceMoney = performanceMoney;
	}
	public float getAnnualizedRate() {
		return annualizedRate;
	}
	public void setAnnualizedRate(float annualizedRate) {
		this.annualizedRate = annualizedRate;
	}
	public float getAnnualizedMoney() {
		return annualizedMoney;
	}
	public void setAnnualizedMoney(float annualizedMoney) {
		this.annualizedMoney = annualizedMoney;
	}
	public String getMoneyDate() {
		return moneyDate;
	}
	public void setMoneyDate(String moneyDate) {
		this.moneyDate = moneyDate;
	}
	public int getFldCollectDays() {
		return fldCollectDays;
	}
	public void setFldCollectDays(int fldCollectDays) {
		this.fldCollectDays = fldCollectDays;
	}
	public float getFldDepositRate() {
		return fldDepositRate;
	}
	public void setFldDepositRate(float fldDepositRate) {
		this.fldDepositRate = fldDepositRate;
	}
	public float getFldCollectMoney() {
		return fldCollectMoney;
	}
	public void setFldCollectMoney(float fldCollectMoney) {
		this.fldCollectMoney = fldCollectMoney;
	}
	public String getFinancialUserNo() {
		return financialUserNo;
	}
	public void setFinancialUserNo(String financialUserNo) {
		this.financialUserNo = financialUserNo;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardMoney() {
		return cardMoney;
	}
	public void setCardMoney(String cardMoney) {
		this.cardMoney = cardMoney;
	}
	public Integer getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getFldCardNo() {
		return fldCardNo;
	}
	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
