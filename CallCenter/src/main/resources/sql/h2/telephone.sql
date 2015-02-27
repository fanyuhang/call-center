DROP TABLE IF EXISTS tblTelephoneAssign;
DROP TABLE IF EXISTS tblTelephoneAssignDetail;
DROP TABLE IF EXISTS tblTelephoneCustomer;
DROP TABLE IF EXISTS tblTelephoneExchange;
DROP TABLE IF EXISTS tblTelephoneExchangeDetail;
DROP TABLE IF EXISTS tblTelephoneImport;
DROP TABLE IF EXISTS tblTelephoneImportDetail;
DROP TABLE IF EXISTS tblTelephoneTask;
DROP TABLE IF EXISTS tblTelephoneRecord;
DROP TABLE IF EXISTS tblTelephoneTrace;
DROP TABLE IF EXISTS tblTelephoneTraceLog;

/*==============================================================*/
/* Table: tblTelephoneAssign                                    */
/*==============================================================*/
CREATE TABLE tblTelephoneAssign (
  fldId             NVARCHAR(40)   NOT NULL,
  fldSource         INT            NULL,
  fldAssignNumber   INT            NULL,
  fldCallUserNumber INT            NULL,
  fldAverageNumber  INT            NULL,
  fldDayNumber      INT            NULL,
  fldBeginDate      DATETIME       NULL,
  fldEndDate        DATETIME       NULL,
  fldContentType    INT            NULL,
  fldComment        NVARCHAR(1000) NULL,
  fldOperateUserNo  NVARCHAR(32)   NULL,
  fldOperateDate    DATETIME       NULL,
  fldCreateUserNo   NVARCHAR(32)   NULL,
  fldCreateDate     DATETIME       NULL,
  fldImportId          nvarchar(40)         null,
  CONSTRAINT PK_TBLTELEPHONEASSIGN PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneAssignDetail                              */
/*==============================================================*/
CREATE TABLE tblTelephoneAssignDetail (
  fldId             NVARCHAR(40) NOT NULL,
  fldAssignId       NVARCHAR(40) NULL,
  fldCallUserNo     NVARCHAR(40) NULL,
  fldAssignUserNo   NVARCHAR(40) NULL,
  fldAssignDate     DATETIME     NULL,
  fldTaskDate       DATETIME     NULL,
  fldTaskNumber     INT          NULL,
  fldFinishNumber   INT          NULL,
  fldFollowNumber   INT          NULL,
  fldExchangeNumber INT          NULL,
  fldFinishStatus   INT          NULL,
  fldContentType    INT          NULL,
  fldOperateUserNo  NVARCHAR(32) NULL,
  fldOperateDate    DATETIME     NULL,
  fldCreateUserNo   NVARCHAR(32) NULL,
  fldCreateDate     DATETIME     NULL,
  fldTaskType          int                  null,
  fldImportId          nvarchar(40)         null,
  CONSTRAINT PK_TBLTELEPHONEASSIGNDETAIL PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneCustomer                                  */
/*==============================================================*/
CREATE TABLE tblTelephoneCustomer (
  fldId              NVARCHAR(40)   NOT NULL,
  fldCustomerName    NVARCHAR(40)   NULL,
  fldGender          INT            NULL,
  fldBirthday        DATETIME       NULL,
  fldMobile          NVARCHAR(256)  NULL,
  fldIdentityNo      NVARCHAR(256)  NULL,
  fldPhone           NVARCHAR(256)  NULL,
  fldAddress         NVARCHAR(1000) NULL,
  fldEmail           NVARCHAR(256)  NULL,
  fldSource          INT            NULL,
  fldLatestCallDate  DATETIME       NULL,
  fldResultStatus    INT            NULL,
  fldAssignStatus    INT            NULL,
  fldAssignDate      DATETIME       NULL,
  fldFinancialUserNo NVARCHAR(32)   NULL,
  fldCallUserNo      NVARCHAR(40)   NULL,
  fldOperateUserNo   NVARCHAR(32)   NULL,
  fldOperateDate     DATETIME       NULL,
  fldCreateUserNo    NVARCHAR(32)   NULL,
  fldCreateDate      DATETIME       NULL,
  fldComment           nvarchar(1000)       null,
  CONSTRAINT PK_TBLTELEPHONECUSTOMER PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneExchange                                  */
/*==============================================================*/
CREATE TABLE tblTelephoneExchange (
  fldId                NVARCHAR(40)   NOT NULL,
  fldOldAssignDetailId NVARCHAR(40)   NULL,
  fldNewAssignDetailId NVARCHAR(40)   NULL,
  fldExchangeDate      DATETIME       NULL,
  fldOldTaskDate       DATETIME       NULL,
  fldNewTaskDate       DATETIME       NULL,
  fldExchangeNumber    INT            NULL,
  fldOldCallUserNo     NVARCHAR(40)   NULL,
  fldNewCallUserNo     NVARCHAR(40)   NULL,
  fldOldBeforeNumber   INT            NULL,
  fldOldAfterNumber    INT            NULL,
  fldNewBeforeNumber   INT            NULL,
  fldNewAfterNumber    INT            NULL,
  fldComment           NVARCHAR(1000) NULL,
  fldOperateUserNo     NVARCHAR(32)   NULL,
  fldOperateDate       DATETIME       NULL,
  fldCreateUserNo      NVARCHAR(32)   NULL,
  fldCreateDate        DATETIME       NULL,
  CONSTRAINT PK_TBLTELEPHONEEXCHANGE PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneExchangeDetail                            */
/*==============================================================*/
CREATE TABLE tblTelephoneExchangeDetail (
  fldId            NUMERIC IDENTITY,
  fldExchangeId    NVARCHAR(40) NULL,
  fldTaskId        NUMERIC      NULL,
  fldOperateUserNo NVARCHAR(32) NULL,
  fldOperateDate   DATETIME     NULL,
  fldCreateUserNo  NVARCHAR(32) NULL,
  fldCreateDate    DATETIME     NULL,
  CONSTRAINT PK_TBLTELEPHONEEXCHANGEDETAIL PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneImport                                    */
/*==============================================================*/
CREATE TABLE tblTelephoneImport (
  fldId                   NVARCHAR(40)   NOT NULL,
  fldName                 NVARCHAR(256)  NULL,
  fldDuplicateStatus      INT            NULL,
  fldUploadFilePath       NVARCHAR(1000) NULL,
  fldTotalNumber          INT            NULL,
  fldDuplicateTotalNumber INT            NULL,
  fldDuplicateFilePath    NVARCHAR(1000) NULL,
  fldImportTotalNumber    INT            NULL,
  fldImportFilePath       NVARCHAR(1000) NULL,
  fldAssignTotalNumber    INT            NULL,
  fldComment              NVARCHAR(1000) NULL,
  fldOperateUserNo        NVARCHAR(32)   NULL,
  fldOperateDate          DATETIME       NULL,
  fldCreateUserNo         NVARCHAR(32)   NULL,
  fldCreateDate           DATETIME       NULL,
  CONSTRAINT PK_TBLTELEPHONEIMPORT PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneImportDetail                              */
/*==============================================================*/
CREATE TABLE tblTelephoneImportDetail (
  fldId              NVARCHAR(40)   NOT NULL,
  fldTelephoneId     NVARCHAR(40)   NULL,
  fldImportId        NVARCHAR(40)   NULL,
  fldCustomerName    NVARCHAR(256)  NULL,
  fldGender          INT            NULL,
  fldMobile          NVARCHAR(256)  NULL,
  fldPhone           NVARCHAR(256)  NULL,
  fldAddress         NVARCHAR(1000) NULL,
  fldDuplicateStatus INT            NULL,
  fldAssignStatus    INT            NULL,
  fldFinancialUserNo NVARCHAR(32)   NULL,
  fldCallUserNo      NVARCHAR(40)   NULL,
  fldAssignNumber    INT            NULL,
  fldComment         NVARCHAR(1000) NULL,
  fldOperateUserNo   NVARCHAR(32)   NULL,
  fldOperateDate     DATETIME       NULL,
  fldCreateUserNo    NVARCHAR(32)   NULL,
  fldCreateDate      DATETIME       NULL,
  CONSTRAINT PK_TBLTELEPHONEIMPORTDETAIL PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblTelephoneTask                                      */
/*==============================================================*/
CREATE TABLE tblTelephoneTask (
  fldId             NUMERIC IDENTITY,
  fldCustomerId     NVARCHAR(40)   NULL,
  fldAssignDetailId NVARCHAR(40)   NULL,
  fldCallUserNo     NVARCHAR(40)   NULL,
  fldCustomerName   NVARCHAR(256)  NULL,
  fldAssignDate     DATETIME       NULL,
  fldTaskDate       DATETIME       NULL,
  fldCallDate       DATETIME       NULL,
  fldTaskType       INT            NULL,
  fldCallStatus     INT            NULL,
  fldTaskStatus     INT            NULL,
  fldContentType    INT            NULL,
  fldResultType     INT            NULL,
  fldComment        NVARCHAR(1000) NULL,
  fldAuditStatus    INT            NULL,
  fldAuditComment   NVARCHAR(1000) NULL,
  fldAuditFraction  INT            NULL,
  fldAuditUserNo    NVARCHAR(40)   NULL,
  fldOperateUserNo  NVARCHAR(32)   NULL,
  fldOperateDate    DATETIME       NULL,
  fldCreateUserNo   NVARCHAR(32)   NULL,
  fldCreateDate     DATETIME       NULL,
  fldImportId          nvarchar(40)         null,
  CONSTRAINT PK_TBLTELEPHONETASK PRIMARY KEY (fldId)
);

CREATE TABLE tblTelephoneRecord (
  fldId             NUMERIC IDENTITY,
  fldTaskId         NUMERIC        NULL,
  fldCustomerName   NVARCHAR(256)  NULL,
  fldPhone          NVARCHAR(40)   NULL,
  fldCallDate       DATETIME       NULL,
  fldCallType       INT            NULL,
  fldResultType     INT            NULL,
  fldComment        NVARCHAR(1000) NULL,
  fldCallLong       INT            NULL,
  fldCallBeginTime  DATETIME       NULL,
  fldCallEndTime    DATETIME       NULL,
  fldRecordFilePath NVARCHAR(1000) NULL,
  fldCallButtons    NVARCHAR(1000) NULL,
  fldChannelNo      NVARCHAR(256)  NULL,
  fldAuditStatus    INT            NULL,
  fldAuditComment   NVARCHAR(1000) NULL,
  fldAuditFraction  INT            NULL,
  fldAuditUserNo    NVARCHAR(40)   NULL,
  fldOperateUserNo  NVARCHAR(32)   NULL,
  fldOperateDate    DATETIME       NULL,
  fldCreateUserNo   NVARCHAR(32)   NULL,
  fldCreateDate     DATETIME       NULL,
  fldTotalDuration     int                  null,
  fldWaitTime          int                  null,
  fldAnswerFlag        int                  null,
  CONSTRAINT PK_TBLTELEPHONERECORD PRIMARY KEY (fldId)
);

create table tblTelephoneTrace (
  fldId                bigint               identity,
  fldTaskId            bigint               null,
  fldCustomerId        nvarchar(40)         null,
  fldImportId          nvarchar(40)         null,
  fldCallUserNo        nvarchar(40)         null,
  fldCustomerName      nvarchar(256)        null,
  fldMobile            nvarchar(256)        null,
  fldPhone             nvarchar(256)        null,
  fldComment           nvarchar(1000)       null,
  fldStatus            int                  null,
  fldAssignStatus      int                  null,
  fldAssignUserNo      nvarchar(32)         null,
  fldAssignDate        datetime             null,
  fldAuditStatus       int                  null,
  fldAuditUserNo       nvarchar(40)         null,
  fldAuditDate         datetime             null,
  fldResultStatus      int                  null,
  fldFinancialUserNo   nvarchar(32)         null,
  fldServiceUserNo     nvarchar(32)         null,
  fldFinishDate        datetime             null,
  fldOperateUserNo     nvarchar(32)         null,
  fldOperateDate       datetime             null,
  fldCreateUserNo      nvarchar(32)         null,
  fldCreateDate        datetime             null,
  constraint PK_TBLTELEPHONETRACE primary key (fldId)
);

create table tblTelephoneTraceLog (
  fldId                bigint               identity,
  fldTraceId           bigint               null,
  fldStatusDesc        nvarchar(1000)       null,
  fldComment           nvarchar(1000)       null,
  fldOperateUserNo     nvarchar(32)         null,
  fldOperateDate       datetime             null,
  fldCreateUserNo      nvarchar(32)         null,
  fldCreateDate        datetime             null,
  constraint PK_TBLTELEPHONETRACELOG primary key (fldId)
);