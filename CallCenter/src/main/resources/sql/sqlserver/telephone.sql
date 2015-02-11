/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2015-02-11 20:46:02                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneAssign')
            and   type = 'U')
   drop table tblTelephoneAssign
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneAssignDetail')
            and   type = 'U')
   drop table tblTelephoneAssignDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneCustomer')
            and   type = 'U')
   drop table tblTelephoneCustomer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneExchange')
            and   type = 'U')
   drop table tblTelephoneExchange
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneExchangeDetail')
            and   type = 'U')
   drop table tblTelephoneExchangeDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneImport')
            and   type = 'U')
   drop table tblTelephoneImport
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneImportDetail')
            and   type = 'U')
   drop table tblTelephoneImportDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneRecord')
            and   type = 'U')
   drop table tblTelephoneRecord
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblTelephoneTask')
            and   type = 'U')
   drop table tblTelephoneTask
go

/*==============================================================*/
/* Table: tblTelephoneAssign                                    */
/*==============================================================*/
create table tblTelephoneAssign (
   fldId                nvarchar(40)         not null,
   fldSource            int                  null,
   fldAssignNumber      int                  null,
   fldCallUserNumber    int                  null,
   fldAverageNumber     int                  null,
   fldDayNumber         int                  null,
   fldBeginDate         datetime             null,
   fldEndDate           datetime             null,
   fldContentType       int                  null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEASSIGN primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneAssignDetail                              */
/*==============================================================*/
create table tblTelephoneAssignDetail (
   fldId                nvarchar(40)         not null,
   fldAssignId          nvarchar(40)         null,
   fldCallUserNo        nvarchar(40)         null,
   fldAssignUserNo      nvarchar(40)         null,
   fldAssignDate        datetime             null,
   fldTaskDate          datetime             null,
   fldTaskNumber        int                  null,
   fldFinishNumber      int                  null,
   fldFollowNumber      int                  null,
   fldExchangeNumber    int                  null,
   fldFinishStatus      int                  null,
   fldContentType       int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEASSIGNDETAIL primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneCustomer                                  */
/*==============================================================*/
create table tblTelephoneCustomer (
   fldId                nvarchar(40)         not null,
   fldCustomerName      nvarchar(40)         null,
   fldGender            int                  null,
   fldBirthday          datetime             null,
   fldIdentityNo        nvarchar(256)        null,
   fldMobile            nvarchar(256)        null,
   fldPhone             nvarchar(256)        null,
   fldAddress           nvarchar(1000)       null,
   fldEmail             nvarchar(256)        null,
   fldSource            int                  null,
   fldLatestCallDate    datetime             null,
   fldResultStatus      int                  null,
   fldAssignStatus      int                  null,
   fldAssignDate        datetime             null,
   fldFinancialUserNo   nvarchar(32)         null,
   fldCallUserNo        nvarchar(40)         null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONECUSTOMER primary key (fldId)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：导入话单
   1：录入客户
   ',
   'user', @CurrentUser, 'table', 'tblTelephoneCustomer', 'column', 'fldSource'
go

/*==============================================================*/
/* Table: tblTelephoneExchange                                  */
/*==============================================================*/
create table tblTelephoneExchange (
   fldId                nvarchar(40)         not null,
   fldOldAssignDetailId nvarchar(40)         null,
   fldNewAssignDetailId nvarchar(40)         null,
   fldExchangeDate      datetime             null,
   fldOldTaskDate       datetime             null,
   fldNewTaskDate       datetime             null,
   fldExchangeNumber    int                  null,
   fldOldCallUserNo     nvarchar(40)         null,
   fldNewCallUserNo     nvarchar(40)         null,
   fldOldBeforeNumber   int                  null,
   fldOldAfterNumber    int                  null,
   fldNewBeforeNumber   int                  null,
   fldNewAfterNumber    int                  null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEEXCHANGE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneExchangeDetail                            */
/*==============================================================*/
create table tblTelephoneExchangeDetail (
   fldId                numeric              identity,
   fldExchangeId        nvarchar(40)         null,
   fldTaskId            numeric              null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEEXCHANGEDETAIL primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneImport                                    */
/*==============================================================*/
create table tblTelephoneImport (
   fldId                nvarchar(40)         not null,
   fldName              nvarchar(256)        null,
   fldDuplicateStatus   int                  null,
   fldUploadFilePath    nvarchar(1000)       null,
   fldTotalNumber       int                  null,
   fldDuplicateTotalNumber int                  null,
   fldDuplicateFilePath nvarchar(1000)       null,
   fldImportTotalNumber int                  null,
   fldImportFilePath    nvarchar(1000)       null,
   fldAssignTotalNumber int                  null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEIMPORT primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneImportDetail                              */
/*==============================================================*/
create table tblTelephoneImportDetail (
   fldId                nvarchar(40)         not null,
   fldTelephoneId       nvarchar(40)         null,
   fldImportId          nvarchar(40)         null,
   fldCustomerName      nvarchar(256)        null,
   fldGender            int                  null,
   fldMobile            nvarchar(256)        null,
   fldPhone             nvarchar(256)        null,
   fldAddress           nvarchar(1000)       null,
   fldDuplicateStatus   int                  null,
   fldAssignStatus      int                  null,
   fldFinancialUserNo   nvarchar(32)         null,
   fldCallUserNo        nvarchar(40)         null,
   fldAssignNumber      int                  null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONEIMPORTDETAIL primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblTelephoneRecord                                    */
/*==============================================================*/
create table tblTelephoneRecord (
   fldId                numeric              identity,
   fldTaskId            numeric              null,
   fldCustomerName      nvarchar(256)        null,
   fldPhone             nvarchar(40)         null,
   fldCallDate          datetime             null,
   fldCallType          int                  null,
   fldResultType        int                  null,
   fldComment           nvarchar(1000)       null,
   fldCallLong          int                  null,
   fldCallBeginTime     datetime             null,
   fldCallEndTime       datetime             null,
   fldRecordFilePath    nvarchar(1000)       null,
   fldCallButtons       nvarchar(1000)       null,
   fldChannelNo         nvarchar(256)        null,
   fldAuditStatus       int                  null,
   fldAuditComment      nvarchar(1000)       null,
   fldAuditFraction     int                  null,
   fldAuditUserNo       nvarchar(40)         null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   fldTotalDuration     int                  null,
   fldWaitTime          int                  null,
   fldAnswerFlag        int                  null,
   constraint PK_TBLTELEPHONERECORD primary key (fldId)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：呼出
   1：呼入',
   'user', @CurrentUser, 'table', 'tblTelephoneRecord', 'column', 'fldCallType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：未知
   1：错号
   2：空号
   3：待跟踪
   4：有意向
   9：其他',
   'user', @CurrentUser, 'table', 'tblTelephoneRecord', 'column', 'fldResultType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：待审查
   1：已审查',
   'user', @CurrentUser, 'table', 'tblTelephoneRecord', 'column', 'fldAuditStatus'
go

/*==============================================================*/
/* Table: tblTelephoneTask                                      */
/*==============================================================*/
create table tblTelephoneTask (
   fldId                numeric              identity,
   fldCustomerId        nvarchar(40)         null,
   fldAssignDetailId    nvarchar(40)         null,
   fldCallUserNo        nvarchar(40)         null,
   fldCustomerName      nvarchar(256)        null,
   fldAssignDate        datetime             null,
   fldTaskDate          datetime             null,
   fldCallDate          datetime             null,
   fldTaskType          int                  null,
   fldCallStatus        int                  null,
   fldTaskStatus        int                  null,
   fldContentType       int                  null,
   fldResultType        int                  null,
   fldComment           nvarchar(1000)       null,
   fldAuditStatus       int                  null,
   fldAuditComment      nvarchar(1000)       null,
   fldAuditFraction     int                  null,
   fldAuditUserNo       nvarchar(40)         null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLTELEPHONETASK primary key (fldId)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:自动分配任务
   1:手动分配任务',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldTaskType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：未拨打
   1：已拨打',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldCallStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：未完成
   1：待跟踪
   9：已完成',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldTaskStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：拨打
   1：业务受理
   2：投诉及建议
   9：其他',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldContentType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：未知
   1：错号
   2：空号
   3：待跟踪
   4：有意向
   9：其他',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldResultType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：待审查
   1：已审查',
   'user', @CurrentUser, 'table', 'tblTelephoneTask', 'column', 'fldAuditStatus'
go

