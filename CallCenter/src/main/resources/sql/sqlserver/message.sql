/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014-07-03 19:05:18                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblMessageOperate')
            and   type = 'U')
   drop table tblMessageOperate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblMessageOperateDetail')
            and   type = 'U')
   drop table tblMessageOperateDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblMessageTemplate')
            and   type = 'U')
   drop table tblMessageTemplate
go

/*==============================================================*/
/* Table: tblMessageOperate                                     */
/*==============================================================*/
create table tblMessageOperate (
   fldId                nvarchar(40)         not null,
   fldMessageTemplateId nvarchar(40)         null,
   fldContent           nvarchar(1000)       null,
   fldMessageNum        int                  null,
   fldMobiles           nvarchar(1000)       null,
   fldComment           nvarchar(1000)       null,
   fldSendStatus        int                  null,
   fldSendResult        nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLMESSAGEOPERATE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblMessageOperateDetail                               */
/*==============================================================*/
create table tblMessageOperateDetail (
   fldId                int                  identity,
   fldMessageOperateId  nvarchar(40)         null,
   fldMobile            nvarchar(32)         null,
   fldContent           nvarchar(1000)       null,
   fldSendStatus        int                  null,
   fldSendResult        nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLMESSAGEOPERATEDETAIL primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblMessageTemplate                                    */
/*==============================================================*/
create table tblMessageTemplate (
   fldId                nvarchar(40)         not null,
   fldName              nvarchar(256)        null,
   fldContent           nvarchar(1000)       null,
   fldStatus            int                  null,
   fldComment           nvarchar(1000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLMESSAGETEMPLATE primary key (fldId)
)
go

