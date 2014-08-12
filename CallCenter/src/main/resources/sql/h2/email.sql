DROP TABLE IF EXISTS tblEmailOperate;
DROP TABLE IF EXISTS tblEmailTemplate;

/*==============================================================*/
/* Table: tblEmailOperate                                       */
/*==============================================================*/
CREATE TABLE tblEmailOperate (
  fldId              NVARCHAR(40)   NOT NULL,
  fldEmailTemplateId NVARCHAR(40)   NULL,
  fldSendDate        DATETIME       NULL,
  fldEmailNum        INT            NULL,
  fldSenderEmail     NVARCHAR(500)  NULL,
  fldContent         NVARCHAR(2000) NULL,
  fldAttachPath      NVARCHAR(1000) NULL,
  fldTitle           NVARCHAR(1000) NULL,
  fldReceiverEmails  NVARCHAR(2000) NULL,
  fldOperateUserNo   NVARCHAR(32)   NULL,
  fldOperateDate     DATETIME       NULL,
  fldCreateUserNo    NVARCHAR(32)   NULL,
  fldCreateDate      DATETIME       NULL,
  CONSTRAINT PK_TBLEMAILOPERATE PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblEmailTemplate                                      */
/*==============================================================*/
CREATE TABLE tblEmailTemplate (
  fldId            NVARCHAR(40)   NOT NULL,
  fldName          NVARCHAR(256)  NULL,
  fldTitle         NVARCHAR(256)  NULL,
  fldContent       NVARCHAR(2000) NULL,
  fldAttachPath    NVARCHAR(1000) NULL,
  fldStatus        INT            NULL,
  fldOperateUserNo NVARCHAR(32)   NULL,
  fldOperateDate   DATETIME       NULL,
  fldCreateUserNo  NVARCHAR(32)   NULL,
  fldCreateDate    DATETIME       NULL,
  CONSTRAINT PK_TBLEMAILTEMPLATE PRIMARY KEY (fldId)
);

