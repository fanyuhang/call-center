/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2014-06-22 23:56:00                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('tblCertActive')
            and   type = 'U')
   drop table tblCertActive
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblDataPrivilege')
            and   type = 'U')
   drop table tblDataPrivilege
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblDept')
            and   type = 'U')
   drop table tblDept
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblDictionary')
            and   type = 'U')
   drop table tblDictionary
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblFavorite')
            and   type = 'U')
   drop table tblFavorite
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblLog')
            and   type = 'U')
   drop table tblLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblNode')
            and   type = 'U')
   drop table tblNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblNodeRoleLink')
            and   type = 'U')
   drop table tblNodeRoleLink
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblPrivilege')
            and   type = 'U')
   drop table tblPrivilege
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblRole')
            and   type = 'U')
   drop table tblRole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblSysJob')
            and   type = 'U')
   drop table tblSysJob
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblSysParameter')
            and   type = 'U')
   drop table tblSysParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblUser')
            and   type = 'U')
   drop table tblUser
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblUserDeptLink')
            and   type = 'U')
   drop table tblUserDeptLink
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tblUserRoleLink')
            and   type = 'U')
   drop table tblUserRoleLink
go

/*==============================================================*/
/* Table: tblCertActive                                         */
/*==============================================================*/
create table tblCertActive (
   fldCertCN            nvarchar(32)         not null,
   fldPublicKey         nvarchar(1000)       null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   fldGenerateDate      datetime             null,
   fldSystem            nvarchar(4)          null,
   constraint PK_TBLCERTACTIVE primary key (fldCertCN)
)
go

/*==============================================================*/
/* Table: tblDataPrivilege                                      */
/*==============================================================*/
create table tblDataPrivilege (
   fldDataPrivilegeId   int                  identity,
   fldDataPrivilegeMaster nvarchar(32)         not null,
   fldDataPrivilegeRule nvarchar(2000)       null,
   constraint PK_TBLDATAPRIVILEGE primary key (fldDataPrivilegeId)
)
go

/*==============================================================*/
/* Table: tblDept                                               */
/*==============================================================*/
create table tblDept (
   fldDeptCode          nvarchar(32)         not null,
   fldParent            nvarchar(32)         null,
   fldDeptName          nvarchar(64)         null,
   fldPosition          int                  null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   fldGenerateDate      datetime             null,
   fldSystem            nvarchar(4)          null,
   constraint PK_TBLDEPT primary key (fldDeptCode)
)
go

/*==============================================================*/
/* Table: tblDictionary                                         */
/*==============================================================*/
create table tblDictionary (
   fldId                int                  identity,
   fldType              int                  null,
   fldName              nvarchar(64)         null,
   fldValue             nvarchar(100)        null,
   fldComment           nvarchar(200)        null,
   fldStatus            tinyint              null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLDICTIONARY primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblFavorite                                           */
/*==============================================================*/
create table tblFavorite (
   fldId                int                  identity,
   fldCode              nvarchar(32)         null,
   fldUserId            int                  null,
   fldAddTime           datetime             null,
   fldComment           nvarchar(64)         null,
   constraint PK_TBLFAVORITE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblLog                                                */
/*==============================================================*/
create table tblLog (
   fldId                bigint               identity,
   fldLoginName         nvarchar(32)         null,
   fldAction            nvarchar(32)         null,
   fldResource          nvarchar(200)        null,
   fldComment           nvarchar(1000)       null,
   fldBak               nvarchar(200)        null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLLOG primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblNode                                               */
/*==============================================================*/
create table tblNode (
   fldCode              nvarchar(32)         not null,
   fldParent            nvarchar(32)         null,
   fldName              nvarchar(64)         null,
   fldDesc              nvarchar(256)        null,
   fldTarget            nvarchar(32)         null,
   fldIcon              nvarchar(256)        null,
   fldLink              nvarchar(256)        null,
   fldType              tinyint              null,
   fldPosition          int                  null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLNODE primary key (fldCode)
)
go

/*==============================================================*/
/* Table: tblNodeRoleLink                                       */
/*==============================================================*/
create table tblNodeRoleLink (
   fldNodeId            nvarchar(32)         null,
   fldRoleId            int                  null
)
go

/*==============================================================*/
/* Table: tblPrivilege                                          */
/*==============================================================*/
create table tblPrivilege (
   fldPrivilegeId       int                  identity,
   fldPrivilegeMaster   nvarchar(32)         null,
   fldPrivilegeMasterValue int                  null,
   fldPrivilegeAccessValue nvarchar(32)         null,
   fldPrivilegeOperation int                  null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLPRIVILEGE primary key (fldPrivilegeId)
)
go

/*==============================================================*/
/* Table: tblRole                                               */
/*==============================================================*/
create table tblRole (
   fldId                int                  identity,
   fldRoleName          nvarchar(32)         null,
   fldDesc              nvarchar(256)        null,
   fldPosition          int                  null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLROLE primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblSysJob                                             */
/*==============================================================*/
create table tblSysJob (
   fldId                int                  identity,
   fldJobName           nvarchar(32)         null,
   fldJobGroup          nvarchar(32)         null,
   fldTriggerName       nvarchar(32)         null,
   fldTriggerGroup      nvarchar(32)         null,
   fldCronExpression    nvarchar(32)         null,
   fldIntervalInSeconds int                  null,
   fldRepeatCount       int                  null,
   fldTriggerType       int                  null,
   fldJobClass          nvarchar(100)        null,
   fldStatus            int                  null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   fldLastSuccessDate   datetime             null,
   fldNextFireTime      datetime             null,
   fldStartTime         datetime             null,
   fldEndTime           datetime             null,
   fldExecuteCount      int                  null,
   constraint PK_TBLSYSJOB primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblSysParameter                                       */
/*==============================================================*/
create table tblSysParameter (
   fldId                int                  identity,
   fldName              nvarchar(64)         null,
   fldValue             nvarchar(100)        null,
   fldUnit              nvarchar(64)         null,
   fldStatus            tinyint              null,
   fldDisplay           tinyint              null,
   fldComment           nvarchar(200)        null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   constraint PK_TBLSYSPARAMETER primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblUser                                               */
/*==============================================================*/
create table tblUser (
   fldId                int                  identity,
   fldLoginName         nvarchar(32)         null,
   fldUserName          nvarchar(32)         null,
   fldPassword          nvarchar(64)         null,
   fldLoginStatus       tinyint              null,
   fldUserStatus        tinyint              null,
   fldLoginErrCount     tinyint              null,
   fldModifyPwdCount    tinyint              null,
   fldGender            tinyint              null,
   fldEmail             nvarchar(256)        null,
   fldPhone             nvarchar(32)         null,
   fldMobile            nvarchar(32)         null,
   fldFax               nvarchar(32)         null,
   fldAddress           nvarchar(256)        null,
   fldCertCN            nvarchar(32)         null,
   fldPosition          int                  null,
   fldLastLoginTime     datetime             null,
   fldOperateId         int                  null,
   fldOperateDate       datetime             null,
   fldGenerateDate      datetime             null,
   fldSystem            nvarchar(4)          null,
   constraint PK_TBLUSER primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblUserDeptLink                                       */
/*==============================================================*/
create table tblUserDeptLink (
   fldId                int                  identity,
   fldLoginName         nvarchar(32)         null,
   fldDeptCode          nvarchar(32)         null,
   fldGenerateDate      datetime             null,
   fldSystem            nvarchar(4)          null,
   constraint PK_TBLUSERDEPTLINK primary key (fldId)
)
go

/*==============================================================*/
/* Table: tblUserRoleLink                                       */
/*==============================================================*/
create table tblUserRoleLink (
   fldId                int                  identity,
   fldUserId            int                  null,
   fldRoleId            int                  null,
   constraint PK_TBLUSERROLELINK primary key (fldId)
)
go

