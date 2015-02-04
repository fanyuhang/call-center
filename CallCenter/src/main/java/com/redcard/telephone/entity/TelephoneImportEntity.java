package com.redcard.telephone.entity;

import java.io.Serializable;

public class TelephoneImportEntity implements Serializable {
	private String custName;
	private String mobile;
	private String telephone;
	private String address;
	private Integer gender;
	private String fldTelephoneId;
    private String fldComment;
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getFldTelephoneId() {
		return fldTelephoneId;
	}
	public void setFldTelephoneId(String fldTelephoneId) {
		this.fldTelephoneId = fldTelephoneId;
	}

    public String getFldComment() {
        return fldComment;
    }

    public void setFldComment(String fldComment) {
        this.fldComment = fldComment;
    }
}
