DROP TABLE IF EXISTS tblCustomer;
DROP TABLE IF EXISTS tblCustomerContract;
DROP TABLE IF EXISTS tblCustomerExchange;
DROP TABLE IF EXISTS tblCustomerExchangeContract;
DROP TABLE IF EXISTS tblCustomerExchangeCustomer;
DROP TABLE IF EXISTS tblCustomerProduct;
DROP TABLE IF EXISTS tblCustomerProductDetail;

/*==============================================================*/
/* Table: tblCustomer                                           */
/*==============================================================*/
CREATE TABLE tblCustomer (
  fldId              NVARCHAR(40)  NOT NULL,
  fldName            NVARCHAR(256) NULL,
  fldSource          INT           NULL,
  fldGender          INT           NULL,
  fldBirthday        DATETIME      NULL,
  fldIdentityNo      NVARCHAR(256) NULL,
  fldPhone           NVARCHAR(256) NULL,
  fldMobile          NVARCHAR(256) NULL,
  fldAddress         NVARCHAR(256) NULL,
  fldEmail           NVARCHAR(256) NULL,
  fldComment         NVARCHAR(256) NULL,
  fldFinancialUserNo NVARCHAR(32)  NULL,
  fldCustomerUserNo  NVARCHAR(32)  NULL,
  fldServiceUserNo   NVARCHAR(32)  NULL,
  fldCardNo          NVARCHAR(32)  NULL,
  fldCardLevel       INT           NULL,
  fldCardTotalMoney    decimal(20,2) NULL,
  fldCardStatus      INT           NULL,
  fldStatus          INT           NULL,
  fldOperateUserNo   NVARCHAR(32)  NULL,
  fldOperateDate     DATETIME      NULL,
  fldCreateUserNo    NVARCHAR(32)  NULL,
  fldCreateDate      DATETIME      NULL,
  CONSTRAINT PK_TBLCUSTOMER PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblCustomerContract                                   */
/*==============================================================*/
CREATE TABLE tblCustomerContract (
  fldId              NVARCHAR(40)  NOT NULL,
  fldCustomerId      NVARCHAR(40)  NULL,
  fldProductId       NVARCHAR(40)  NULL,
  fldProductDetailId NVARCHAR(40)  NULL,
  fldSignDate        DATETIME      NULL,
  fldMoneyDate       DATETIME      NULL,
  fldCollectDays     INT           NULL,
  fldDepositRate       decimal(20,2) NULL,
  fldCollectMoney      decimal(20,2) NULL,
  fldAnnualizedRate    decimal(20,2) NULL,
  fldAnnualizedMoney   decimal(20,2) NULL,
  fldPurchaseMoney     decimal(20,2) NULL,
  fldCommissionRadio   decimal(20,2) NULL,
  fldCommissionMoney   decimal(20,2) NULL,
  fldPerformanceRadio  decimal(20,2) NULL,
  fldPerformanceMoney  decimal(20,2) NULL,
  fldBankNo          NVARCHAR(256) NULL,
  fldBankName        NVARCHAR(256) NULL,
  fldFinancialUserNo NVARCHAR(32)  NULL,
  fldCustomerUserNo  NVARCHAR(32)  NULL,
  fldServiceUserNo   NVARCHAR(32)  NULL,
  fldSource          INT           NULL,
  fldCardMoney         decimal(20,2) NULL,
  fldCardNo          NVARCHAR(32)  NULL,
  fldCardLevel       INT           NULL,
  fldCardStatus      INT           NULL,
  fldStatus          INT           NULL,
  fldFinishStatus    INT           NULL,
  fldOperateUserNo   NVARCHAR(32)  NULL,
  fldOperateDate     DATETIME      NULL,
  fldCreateUserNo    NVARCHAR(32)  NULL,
  fldCreateDate      DATETIME      NULL,
  CONSTRAINT PK_TBLCUSTOMERCONTRACT PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblCustomerExchange                                   */
/*==============================================================*/
CREATE TABLE tblCustomerExchange (
  fldId             NVARCHAR(40) NULL,
  fldOldUserNo      NVARCHAR(16) NULL,
  fldNewUserNo      NVARCHAR(16) NULL,
  fldOldCustomerNum INT          NULL,
  fldOldContractNum INT          NULL,
  fldNewCustomerNum INT          NULL,
  fldNewContractNum INT          NULL,
  fldCustomerNum    INT          NULL,
  fldContractNum    INT          NULL,
  fldOperateUserNo  NVARCHAR(32) NULL,
  fldOperateDate    DATETIME     NULL,
  fldCreateUserNo   NVARCHAR(32) NULL,
  fldCreateDate     DATETIME     NULL,
  CONSTRAINT PK_TBLCUSTOMEREXCHANGE PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblCustomerExchangeContract                           */
/*==============================================================*/
CREATE TABLE tblCustomerExchangeContract (
  ID                    INT IDENTITY,
  fldCustomerExchangeId NVARCHAR(40) NULL,
  fldContractId         NVARCHAR(40) NULL,
  fldOperateUserNo      NVARCHAR(32) NULL,
  fldOperateDate        DATETIME     NULL,
  fldCreateUserNo       NVARCHAR(32) NULL,
  fldCreateDate         DATETIME     NULL,
  CONSTRAINT PK_TBLCUSTOMEREXCHANGECONTRACT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: tblCustomerExchangeCustomer                           */
/*==============================================================*/
CREATE TABLE tblCustomerExchangeCustomer (
  fldId                 INT IDENTITY,
  fldCustomerExchangeId NVARCHAR(40) NULL,
  fldCustomerId         NVARCHAR(40) NULL,
  fldOperateUserNo      NVARCHAR(32) NULL,
  fldOperateDate        DATETIME     NULL,
  fldCreateUserNo       NVARCHAR(32) NULL,
  fldCreateDate         DATETIME     NULL,
  CONSTRAINT PK_TBLCUSTOMEREXCHANGECUSTOMER PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblCustomerProduct                                    */
/*==============================================================*/
CREATE TABLE tblCustomerProduct (
  fldId            NVARCHAR(40)  NULL,
  fldFullName      NVARCHAR(256) NULL,
  fldShortName     NVARCHAR(256) NULL,
  fldDescription   NVARCHAR(256) NULL,
  fldType              int                  null,
  fldEstablishDate DATETIME      NULL,
  fldValueDate     DATETIME      NULL,
  fldComment       NVARCHAR(256) NULL,
  fldStatus        INT           NULL,
  fldOperateUserNo NVARCHAR(32)  NULL,
  fldOperateDate   DATETIME      NULL,
  fldCreateUserNo  NVARCHAR(32)  NULL,
  fldCreateDate    DATETIME      NULL
);

/*==============================================================*/
/* Table: tblCustomerProductDetail                              */
/*==============================================================*/
CREATE TABLE tblCustomerProductDetail (
  fldId            NVARCHAR(40) NULL,
  fldProductId     NVARCHAR(40) NULL,
  fldClearDays     INT          NULL,
  fldDueDate       DATETIME     NULL,
  fldDayUnit           int                  null,
  fldMinPurchaseMoney  decimal(20,2) NULL,
  fldMaxPurchaseMoney  decimal(20,2) NULL,
  fldAnnualizedRate    decimal(20,2) NULL,
  fldDepositRate       decimal(20,2) NULL,
  fldPerformanceRadio  decimal(20,2) NULL,
  fldCommissionRadio   decimal(20,2) NULL,
  fldStatus            INT           NULL,
  fldOperateUserNo NVARCHAR(32) NULL,
  fldOperateDate   DATETIME     NULL,
  fldCreateUserNo  NVARCHAR(32) NULL,
  fldCreateDate    DATETIME     NULL
);

