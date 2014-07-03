DROP TABLE IF EXISTS tblMessageOperate;
DROP TABLE IF EXISTS tblMessageOperateDetail;
DROP TABLE IF EXISTS tblMessageTemplate;

/*==============================================================*/
/* Table: tblMessageOperate                                     */
/*==============================================================*/
CREATE TABLE tblMessageOperate (
  fldId                NVARCHAR(40)   NOT NULL,
  fldMessageTemplateId NVARCHAR(40)   NULL,
  fldContent           NVARCHAR(1000) NULL,
  fldMessageNum        INT            NULL,
  fldMobiles           NVARCHAR(1000) NULL,
  fldComment           NVARCHAR(1000) NULL,
  fldSendStatus        INT            NULL,
  fldSendResult        NVARCHAR(1000) NULL,
  fldOperateUserNo     NVARCHAR(32)   NULL,
  fldOperateDate       DATETIME       NULL,
  fldCreateUserNo      NVARCHAR(32)   NULL,
  fldCreateDate        DATETIME       NULL,
  CONSTRAINT PK_TBLMESSAGEOPERATE PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblMessageOperateDetail                               */
/*==============================================================*/
CREATE TABLE tblMessageOperateDetail (
  fldId               INT IDENTITY,
  fldMessageOperateId NVARCHAR(40)   NULL,
  fldMobile           NVARCHAR(32)   NULL,
  fldContent          NVARCHAR(1000) NULL,
  fldSendStatus       INT            NULL,
  fldSendResult       NVARCHAR(1000) NULL,
  fldOperateUserNo    NVARCHAR(32)   NULL,
  fldOperateDate      DATETIME       NULL,
  fldCreateUserNo     NVARCHAR(32)   NULL,
  fldCreateDate       DATETIME       NULL,
  CONSTRAINT PK_TBLMESSAGEOPERATEDETAIL PRIMARY KEY (fldId)
);

/*==============================================================*/
/* Table: tblMessageTemplate                                    */
/*==============================================================*/
CREATE TABLE tblMessageTemplate (
  fldId            NVARCHAR(40)   NOT NULL,
  fldName          NVARCHAR(256)  NULL,
  fldContent       NVARCHAR(1000) NULL,
  fldStatus        INT            NULL,
  fldComment       NVARCHAR(1000) NULL,
  fldOperateUserNo NVARCHAR(32)   NULL,
  fldOperateDate   DATETIME       NULL,
  fldCreateUserNo  NVARCHAR(32)   NULL,
  fldCreateDate    DATETIME       NULL,
  CONSTRAINT PK_TBLMESSAGETEMPLATE PRIMARY KEY (fldId)
);

