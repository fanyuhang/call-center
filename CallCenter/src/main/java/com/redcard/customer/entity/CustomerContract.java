package com.redcard.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.common.core.util.JsonDateSerializer;
import com.common.core.util.JsonTimestampSerializer;
import com.common.security.entity.User;
import com.common.security.util.SecurityUtil;

/**
 * CustomerContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBLCUSTOMERCONTRACT")
public class CustomerContract implements java.io.Serializable {

    // Fields

    private String fldId;
    private String fldCustomerId;
    private String fldProductId;
    private String fldProductDetailId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fldSignDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fldMoneyDate;
    private Integer fldCollectDays;
    private Double fldDepositRate;
    private Double fldCollectMoney;
    private Double fldAnnualizedRate;
    private Double fldAnnualizedMoney;
    private Double fldPurchaseMoney;
    private Double fldCommissionRadio;
    private Double fldCommissionMoney;
    private Double fldPerformanceRadio;
    private Double fldPerformanceMoney;
    private String fldBankNo;
    private String fldBankName;
    private String fldFinancialUserNo;
    private String fldCustomerUserNo;
    private String fldServiceUserNo;
    private Integer fldSource;
    private Double fldCardMoney;
    private String fldCardNo;
    private Integer fldCardLevel;
    private Integer fldCardStatus;
    private Integer fldStatus;
    private Integer fldFinishStatus;
    private String fldOperateUserNo = SecurityUtil.getCurrentUserLoginName();
    private Date fldOperateDate;
    private String fldCreateUserNo;
    private Date fldCreateDate;

    // Constructors

    /**
     * default constructor
     */
    public CustomerContract() {
    }

    /**
     * minimal constructor
     */
    public CustomerContract(String fldId) {
        this.fldId = fldId;
    }

    /**
     * full constructor
     */
    public CustomerContract(String fldId, String fldCustomerId,
                            String fldProductId, String fldProductDetailId,
                            Date fldSignDate, Date fldMoneyDate,
                            Integer fldCollectDays, Double fldDepositRate,
                            Double fldCollectMoney, Double fldAnnualizedRate,
                            Double fldAnnualizedMoney, Double fldPurchaseMoney,
                            Double fldCommissionRadio, Double fldCommissionMoney,
                            Double fldPerformanceRadio, Double fldPerformanceMoney,
                            String fldBankNo, String fldBankName, String fldFinancialUserNo,
                            String fldCustomerUserNo, String fldServiceUserNo,
                            Integer fldSource, Double fldCardMoney, String fldCardNo,
                            Integer fldCardLevel, Integer fldCardStatus, Integer fldStatus,
                            Integer fldFinishStatus, String fldOperateUserNo,
                            Date fldOperateDate, String fldCreateUserNo,
                            Date fldCreateDate) {
        this.fldId = fldId;
        this.fldCustomerId = fldCustomerId;
        this.fldProductId = fldProductId;
        this.fldProductDetailId = fldProductDetailId;
        this.fldSignDate = fldSignDate;
        this.fldMoneyDate = fldMoneyDate;
        this.fldCollectDays = fldCollectDays;
        this.fldDepositRate = fldDepositRate;
        this.fldCollectMoney = fldCollectMoney;
        this.fldAnnualizedRate = fldAnnualizedRate;
        this.fldAnnualizedMoney = fldAnnualizedMoney;
        this.fldPurchaseMoney = fldPurchaseMoney;
        this.fldCommissionRadio = fldCommissionRadio;
        this.fldCommissionMoney = fldCommissionMoney;
        this.fldPerformanceRadio = fldPerformanceRadio;
        this.fldPerformanceMoney = fldPerformanceMoney;
        this.fldBankNo = fldBankNo;
        this.fldBankName = fldBankName;
        this.fldFinancialUserNo = fldFinancialUserNo;
        this.fldCustomerUserNo = fldCustomerUserNo;
        this.fldServiceUserNo = fldServiceUserNo;
        this.fldSource = fldSource;
        this.fldCardMoney = fldCardMoney;
        this.fldCardNo = fldCardNo;
        this.fldCardLevel = fldCardLevel;
        this.fldCardStatus = fldCardStatus;
        this.fldStatus = fldStatus;
        this.fldFinishStatus = fldFinishStatus;
        this.fldOperateUserNo = fldOperateUserNo;
        this.fldOperateDate = fldOperateDate;
        this.fldCreateUserNo = fldCreateUserNo;
        this.fldCreateDate = fldCreateDate;
    }

    // Property accessors
    @Id
    @Column(name = "FLDID", unique = true, nullable = false)
    public String getFldId() {
        return this.fldId;
    }

    public void setFldId(String fldId) {
        this.fldId = fldId;
    }

    @Column(name = "FLDCUSTOMERID")
    public String getFldCustomerId() {
        return this.fldCustomerId;
    }

    public void setFldCustomerId(String fldCustomerId) {
        this.fldCustomerId = fldCustomerId;
    }

    @Column(name = "FLDPRODUCTID")
    public String getFldProductId() {
        return this.fldProductId;
    }

    public void setFldProductId(String fldProductId) {
        this.fldProductId = fldProductId;
    }

    @Column(name = "FLDPRODUCTDETAILID")
    public String getFldProductDetailId() {
        return this.fldProductDetailId;
    }

    public void setFldProductDetailId(String fldProductDetailId) {
        this.fldProductDetailId = fldProductDetailId;
    }

    @Column(name = "FLDSIGNDATE")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFldSignDate() {
        return this.fldSignDate;
    }

    public void setFldSignDate(Date fldSignDate) {
        this.fldSignDate = fldSignDate;
    }

    @Column(name = "FLDMONEYDATE")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFldMoneyDate() {
        return this.fldMoneyDate;
    }

    public void setFldMoneyDate(Date fldMoneyDate) {
        this.fldMoneyDate = fldMoneyDate;
    }

    @Column(name = "FLDCOLLECTDAYS")
    public Integer getFldCollectDays() {
        return this.fldCollectDays;
    }

    public void setFldCollectDays(Integer fldCollectDays) {
        this.fldCollectDays = fldCollectDays;
    }

    @Column(name = "FLDDEPOSITRATE")
    public Double getFldDepositRate() {
        return this.fldDepositRate;
    }

    public void setFldDepositRate(Double fldDepositRate) {
        this.fldDepositRate = fldDepositRate;
    }

    @Column(name = "FLDCOLLECTMONEY")
    public Double getFldCollectMoney() {
        return this.fldCollectMoney;
    }

    public void setFldCollectMoney(Double fldCollectMoney) {
        this.fldCollectMoney = fldCollectMoney;
    }

    @Column(name = "FLDANNUALIZEDRATE")
    public Double getFldAnnualizedRate() {
        return this.fldAnnualizedRate;
    }

    public void setFldAnnualizedRate(Double fldAnnualizedRate) {
        this.fldAnnualizedRate = fldAnnualizedRate;
    }

    @Column(name = "FLDANNUALIZEDMONEY")
    public Double getFldAnnualizedMoney() {
        return this.fldAnnualizedMoney;
    }

    public void setFldAnnualizedMoney(Double fldAnnualizedMoney) {
        this.fldAnnualizedMoney = fldAnnualizedMoney;
    }

    @Column(name = "FLDPURCHASEMONEY")
    public Double getFldPurchaseMoney() {
        return this.fldPurchaseMoney;
    }

    public void setFldPurchaseMoney(Double fldPurchaseMoney) {
        this.fldPurchaseMoney = fldPurchaseMoney;
    }

    @Column(name = "FLDCOMMISSIONRADIO")
    public Double getFldCommissionRadio() {
        return this.fldCommissionRadio;
    }

    public void setFldCommissionRadio(Double fldCommissionRadio) {
        this.fldCommissionRadio = fldCommissionRadio;
    }

    @Column(name = "FLDCOMMISSIONMONEY")
    public Double getFldCommissionMoney() {
        return this.fldCommissionMoney;
    }

    public void setFldCommissionMoney(Double fldCommissionMoney) {
        this.fldCommissionMoney = fldCommissionMoney;
    }

    @Column(name = "FLDPERFORMANCERADIO")
    public Double getFldPerformanceRadio() {
        return this.fldPerformanceRadio;
    }

    public void setFldPerformanceRadio(Double fldPerformanceRadio) {
        this.fldPerformanceRadio = fldPerformanceRadio;
    }

    @Column(name = "FLDPERFORMANCEMONEY")
    public Double getFldPerformanceMoney() {
        return this.fldPerformanceMoney;
    }

    public void setFldPerformanceMoney(Double fldPerformanceMoney) {
        this.fldPerformanceMoney = fldPerformanceMoney;
    }

    @Column(name = "FLDBANKNO")
    public String getFldBankNo() {
        return this.fldBankNo;
    }

    public void setFldBankNo(String fldBankNo) {
        this.fldBankNo = fldBankNo;
    }

    @Column(name = "FLDBANKNAME")
    public String getFldBankName() {
        return this.fldBankName;
    }

    public void setFldBankName(String fldBankName) {
        this.fldBankName = fldBankName;
    }

    @Column(name = "FLDFINANCIALUSERNO")
    public String getFldFinancialUserNo() {
        return this.fldFinancialUserNo;
    }

    public void setFldFinancialUserNo(String fldFinancialUserNo) {
        this.fldFinancialUserNo = fldFinancialUserNo;
    }

    @Column(name = "FLDCUSTOMERUSERNO")
    public String getFldCustomerUserNo() {
        return this.fldCustomerUserNo;
    }

    public void setFldCustomerUserNo(String fldCustomerUserNo) {
        this.fldCustomerUserNo = fldCustomerUserNo;
    }

    @Column(name = "FLDSERVICEUSERNO")
    public String getFldServiceUserNo() {
        return this.fldServiceUserNo;
    }

    public void setFldServiceUserNo(String fldServiceUserNo) {
        this.fldServiceUserNo = fldServiceUserNo;
    }

    @Column(name = "FLDSOURCE")
    public Integer getFldSource() {
        return this.fldSource;
    }

    public void setFldSource(Integer fldSource) {
        this.fldSource = fldSource;
    }

    @Column(name = "FLDCARDMONEY")
    public Double getFldCardMoney() {
        return this.fldCardMoney;
    }

    public void setFldCardMoney(Double fldCardMoney) {
        this.fldCardMoney = fldCardMoney;
    }

    @Column(name = "FLDCARDNO")
    public String getFldCardNo() {
        return this.fldCardNo;
    }

    public void setFldCardNo(String fldCardNo) {
        this.fldCardNo = fldCardNo;
    }

    @Column(name = "FLDCARDLEVEL")
    public Integer getFldCardLevel() {
        return this.fldCardLevel;
    }

    public void setFldCardLevel(Integer fldCardLevel) {
        this.fldCardLevel = fldCardLevel;
    }

    @Column(name = "FLDCARDSTATUS")
    public Integer getFldCardStatus() {
        return this.fldCardStatus;
    }

    public void setFldCardStatus(Integer fldCardStatus) {
        this.fldCardStatus = fldCardStatus;
    }

    @Column(name = "FLDSTATUS")
    public Integer getFldStatus() {
        return this.fldStatus;
    }

    public void setFldStatus(Integer fldStatus) {
        this.fldStatus = fldStatus;
    }

    @Column(name = "FLDFINISHSTATUS")
    public Integer getFldFinishStatus() {
        return this.fldFinishStatus;
    }

    public void setFldFinishStatus(Integer fldFinishStatus) {
        this.fldFinishStatus = fldFinishStatus;
    }

    @Column(name = "FLDOPERATEUSERNO")
    public String getFldOperateUserNo() {
        return this.fldOperateUserNo;
    }

    public void setFldOperateUserNo(String fldOperateUserNo) {
        this.fldOperateUserNo = fldOperateUserNo;
    }

    @Column(name = "FLDOPERATEDATE")
    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getFldOperateDate() {
        return this.fldOperateDate;
    }

    public void setFldOperateDate(Date fldOperateDate) {
        this.fldOperateDate = fldOperateDate;
    }

    @Column(name = "FLDCREATEUSERNO")
    public String getFldCreateUserNo() {
        return this.fldCreateUserNo;
    }

    public void setFldCreateUserNo(String fldCreateUserNo) {
        this.fldCreateUserNo = fldCreateUserNo;
    }

    @Column(name = "FLDCREATEDATE")
    public Date getFldCreateDate() {
        return this.fldCreateDate;
    }

    public void setFldCreateDate(Date fldCreateDate) {
        this.fldCreateDate = fldCreateDate;
    }

    private Customer customer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCUSTOMERID", insertable = false, updatable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Transient
    private String customerName;

    @Transient
    public String getCustomerName() {
        return customer.getFldName();
    }

    @Transient
    private String identityNo;

    @Transient
    public String getIdentityNo() {
        return customer.getFldIdentityNo();
    }

    private CustomerProductDetail productDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDPRODUCTDETAILID", insertable = false, updatable = false)
    public CustomerProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(CustomerProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Transient
    private String productFullName;

    @Transient
    public String getProductFullName() {
        return productDetail.getFldFullName();
    }

    @Transient
    private Integer productClearDays;

    @Transient
    public Integer getProductClearDays() {
        return productDetail.getFldClearDays();
    }

    protected User operateUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDOPERATEUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
    public User getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }

    @Transient
    private String operateUserName;

    @Transient
    public String getOperateUserName() {
        return operateUser != null ? operateUser.getUserName() : "";
    }

    protected User financialUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDFINANCIALUSERNO", referencedColumnName = "FLDLOGINNAME", insertable = false, updatable = false)
    public User getFinancialUser() {
        return financialUser;
    }

    public void setFinancialUser(User financialUser) {
        this.financialUser = financialUser;
    }

    @Transient
    private String financialUserName;

    @Transient
    public String getFinancialUserName() {
        return financialUser != null ? financialUser.getUserName() : "";
    }

    private CustomerProduct customerProduct;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDPRODUCTID", referencedColumnName = "FLDID", insertable = false, updatable = false)
    public CustomerProduct getCustomerProduct() {
        return customerProduct;
    }

    public void setCustomerProduct(CustomerProduct customerProduct) {
        this.customerProduct = customerProduct;
    }

    @Transient
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getEstablishDate() {
        return customerProduct.getFldEstablishDate();
    }

    private CustomerProductDetail customerProductDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDPRODUCTDETAILID", referencedColumnName = "FLDID", insertable = false, updatable = false)
    public CustomerProductDetail getCustomerProductDetail() {
        return customerProductDetail;
    }

    public void setCustomerProductDetail(CustomerProductDetail customerProductDetail) {
        this.customerProductDetail = customerProductDetail;
    }

    @Transient
    public Integer getClearDays() {
        return customerProductDetail.getFldClearDays();
    }

    @Transient
    public Integer getDayUnit() {
        return customerProductDetail.getFldDayUnit();
    }

    @Transient
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDueDate() {
        return customerProductDetail.getFldDueDate();
    }

    protected User customerUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDCUSTOMERUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
    }

    @Transient
    public String getCustomerUserName() {
        return customerUser!=null ? customerUser.getUserName() : "";
    }

    protected User serviceUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLDSERVICEUSERNO", referencedColumnName="FLDLOGINNAME", insertable = false, updatable = false)
    public User getServiceUser() {
        return serviceUser;
    }

    public void setServiceUser(User serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Transient
    public String getServiceUserName() {
        return serviceUser!=null ? serviceUser.getUserName() : "";
    }

}