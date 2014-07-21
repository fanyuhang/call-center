/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014-07-14 11:05:42                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblMessageOperate')
            and   type = 'U')
   drop table tblMessageOperate
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
   fldMobiles           text                 null,
   fldComment           nvarchar(1000)       null,
   fldSendStatus        int                  null,
   fldSendResult        nvarchar(1000)       null,
   fldTaskId            nvarchar(300)        null,
   fldOperateUserNo     nvarchar(32)         null,
   fldOperateDate       datetime             null,
   fldCreateUserNo      nvarchar(32)         null,
   fldCreateDate        datetime             null,
   constraint PK_TBLMESSAGEOPERATE primary key (fldId)
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

