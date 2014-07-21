/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014-07-14 10:56:51                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblEmailOperate')
            and   type = 'U')
   drop table tblEmailOperate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblEmailTemplate')
            and   type = 'U')
   drop table tblEmailTemplate
go

/*==============================================================*/
/* Table: tblEmailOperate                                       */
/*==============================================================*/
create table tblEmailOperate (
   fldId                nvarchar(40)         not null,
   fldEmailTemplateId   nvarchar(40)         null,
   fldSendDate          datetime             null,
   fldEmailNum          int                  null,
   fldSenderEmail       nvarchar(500)        null,
   fldContent           nvarchar(2000)       null,
   fldAttachPath        nvarchar(1000)       null,
   fldTitle             nvarchar(1000)       null,
   fldReceiverEmails    nvarchar(2000)       null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLEMAILOPERATE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblEmailTemplate                                      */
/*==============================================================*/
create table tblEmailTemplate (
   fldId                nvarchar(40)         not null,
   fldName              nvarchar(256)        null,
   fldTitle             nvarchar(256)        null,
   fldContent           nvarchar(2000)       null,
   fldAttachPath        nvarchar(1000)       null,
   fldStatus            int                  null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLEMAILTEMPLATE primary key (fldId)
)
go

