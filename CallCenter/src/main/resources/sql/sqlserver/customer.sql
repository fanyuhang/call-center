/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014-07-18 21:29:59                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomer')
            and   type = 'U')
   drop table tblCustomer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerContract')
            and   type = 'U')
   drop table tblCustomerContract
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerExchange')
            and   type = 'U')
   drop table tblCustomerExchange
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerExchangeContract')
            and   type = 'U')
   drop table tblCustomerExchangeContract
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerExchangeCustomer')
            and   type = 'U')
   drop table tblCustomerExchangeCustomer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerProduct')
            and   type = 'U')
   drop table tblCustomerProduct
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblCustomerProductDetail')
            and   type = 'U')
   drop table tblCustomerProductDetail
go

/*==============================================================*/
/* Table: tblCustomer                                           */
/*==============================================================*/
create table tblCustomer (
   fldId                nvarchar(40)         not null,
   fldName              nvarchar(256)        null,
   fldSource            int                  null,
   fldGender            int                  null,
   fldBirthday          datetime             null,
   fldIdentityNo        nvarchar(256)        null,
   fldPhone             nvarchar(256)        null,
   fldMobile            nvarchar(256)        null,
   fldAddress           nvarchar(256)        null,
   fldEmail             nvarchar(256)        null,
   fldComment           nvarchar(256)        null,
   fldFinancialUserNo   nvarchar(32)         null,
   fldCustomerUserNo    nvarchar(32)         null,
   fldServiceUserNo     nvarchar(32)         null,
   fldCardNo            nvarchar(32)         null,
   fldCardLevel         int                  null,
   fldCardTotalMoney    money                null,
   fldCardStatus        int                  null,
   fldStatus            int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLCUSTOMER primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblCustomerContract                                   */
/*==============================================================*/
create table tblCustomerContract (
   fldId                nvarchar(40)         not null,
   fldCustomerId        nvarchar(40)         null,
   fldProductId         nvarchar(40)         null,
   fldProductDetailId   nvarchar(40)         null,
   fldSignDate          datetime             null,
   fldMoneyDate         datetime             null,
   fldCollectDays       int                  null,
   fldDepositRate       money                null,
   fldCollectMoney      money                null,
   fldAnnualizedRate    money                null,
   fldAnnualizedMoney   money                null,
   fldPurchaseMoney     money                null,
   fldCommissionRadio   money                null,
   fldCommissionMoney   money                null,
   fldPerformanceRadio  money                null,
   fldPerformanceMoney  money                null,
   fldBankNo            nvarchar(256)        null,
   fldBankName          nvarchar(256)        null,
   fldFinancialUserNo   nvarchar(32)         null,
   fldCustomerUserNo    nvarchar(32)         null,
   fldServiceUserNo     nvarchar(32)         null,
   fldSource            int                  null,
   fldCardMoney         money                null,
   fldCardNo            nvarchar(32)         null,
   fldCardLevel         int                  null,
   fldCardStatus        int                  null,
   fldStatus            int                  null,
   fldFinishStatus      int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLCUSTOMERCONTRACT primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblCustomerExchange                                   */
/*==============================================================*/
create table tblCustomerExchange (
   fldId                nvarchar(40)         not null,
   fldOldUserNo         nvarchar(16)         null,
   fldNewUserNo         nvarchar(16)         null,
   fldOldCustomerNum    int                  null,
   fldOldContractNum    int                  null,
   fldNewCustomerNum    int                  null,
   fldNewContractNum    int                  null,
   fldCustomerNum       int                  null,
   fldContractNum       int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLCUSTOMEREXCHANGE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblCustomerExchangeContract                           */
/*==============================================================*/
create table tblCustomerExchangeContract (
   ID                   int                  identity,
   fldCustomerExchangeId nvarchar(40)         null,
   fldContractId        nvarchar(40)         null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLCUSTOMEREXCHANGECONTRACT primary key (ID)
)
go

/*==============================================================*/
/* Table: tblCustomerExchangeCustomer                           */
/*==============================================================*/
create table tblCustomerExchangeCustomer (
   fldId                int                  identity,
   fldCustomerExchangeId nvarchar(40)         null,
   fldCustomerId        nvarchar(40)         null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLCUSTOMEREXCHANGECUSTOMER primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblCustomerProduct                                    */
/*==============================================================*/
create table tblCustomerProduct (
   fldId                nvarchar(40)         null,
   fldFullName          nvarchar(256)        null,
   fldShortName         nvarchar(256)        null,
   fldType              int                  null,
   fldDescription       nvarchar(256)        null,
   fldEstablishDate     datetime             null,
   fldValueDate         datetime             null,
   fldComment           nvarchar(256)        null,
   fldStatus            int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null
)
go

/*==============================================================*/
/* Table: tblCustomerProductDetail                              */
/*==============================================================*/
create table tblCustomerProductDetail (
   fldId                nvarchar(40)         null,
   fldProductId         nvarchar(40)         null,
   fldClearDays         int                  null,
   fldDayUnit           int                  null,
   fldDueDate           datetime             null,
   fldMinPurchaseMoney  money                null,
   fldMaxPurchaseMoney  money                null,
   fldAnnualizedRate    money                null,
   fldDepositRate       money                null,
   fldPerformanceRadio  money                null,
   fldCommissionRadio   money                null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null
)
go

