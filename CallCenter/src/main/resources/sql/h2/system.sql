/********************security***************************/
DROP TABLE IF EXISTS tblDept;

DROP TABLE IF EXISTS tblNode;

DROP TABLE IF EXISTS tblNodeRoleLink;

DROP TABLE IF EXISTS tblRole;

DROP TABLE IF EXISTS tblPrivilege;

DROP TABLE IF EXISTS tblDataPrivilege;

DROP TABLE IF EXISTS tblUser;

DROP TABLE IF EXISTS tblUserDeptLink;

DROP TABLE IF EXISTS tblUserRoleLink;

DROP TABLE IF EXISTS tblFavorite;

CREATE TABLE tblDept (
  fldDeptCode     VARCHAR(32) NOT NULL,
  fldParent       VARCHAR(32) NULL,
  fldDeptName     VARCHAR(64) NULL,
  fldPosition     INT         NULL,
  fldOperateId    INT         NULL,
  fldOperateDate  DATETIME    NULL,
  fldGenerateDate DATETIME    NULL,
  fldSystem       NVARCHAR(4) NULL,
  CONSTRAINT PK_TBLDEPT PRIMARY KEY (fldDeptCode)
);

CREATE TABLE tblNode (
  fldCode        VARCHAR(32)  NOT NULL,
  fldParent      VARCHAR(32)  NULL,
  fldName        VARCHAR(64)  NULL,
  fldDesc        VARCHAR(256) NULL,
  fldTarget      VARCHAR(32)  NULL,
  fldIcon        VARCHAR(256) NULL,
  fldLink        VARCHAR(256) NULL,
  fldType        TINYINT      NULL,
  fldPosition    INT          NULL,
  fldOperateId   INT          NULL,
  fldOperateDate DATETIME     NULL,
  CONSTRAINT PK_TBLNODE PRIMARY KEY (fldCode)
);
CREATE TABLE tblPrivilege (
  fldPrivilegeId          INT IDENTITY,
  fldPrivilegeMaster      VARCHAR(32) NOT NULL,
  fldPrivilegeMasterValue INT         NOT NULL,
  fldPrivilegeAccessValue VARCHAR(32) NOT NULL,
  fldPrivilegeOperation   INT         NOT NULL,
  fldOperateId            INT         NULL,
  fldOperateDate          DATETIME    NULL,
  CONSTRAINT PK_TBLPRIVILEGE PRIMARY KEY (fldPrivilegeId)
);

CREATE TABLE tblDataPrivilege (
  fldDataPrivilegeId     INT IDENTITY,
  fldDataPrivilegeMaster VARCHAR(32)   NOT NULL,
  fldDataPrivilegeRule   VARCHAR(2000) NULL,
  CONSTRAINT PK_TBLDATAPRIVILEGE PRIMARY KEY (fldDataPrivilegeId)
);

CREATE TABLE tblNodeRoleLink (
  fldNodeId VARCHAR(32) NULL,
  fldRoleId INT         NULL
);

CREATE TABLE tblRole (
  fldId          INT IDENTITY,
  fldRoleName    VARCHAR(32)  NULL,
  fldPosition    INT          NULL,
  fldDesc        VARCHAR(256) NULL,
  fldOperateId   INT          NULL,
  fldOperateDate DATETIME     NULL,
  CONSTRAINT PK_TBLROLE PRIMARY KEY (fldId)
);

CREATE TABLE tblUser (
  fldId             INT IDENTITY,
  fldLoginName      VARCHAR(32)  NULL,
  fldUserName       VARCHAR(32)  NULL,
  fldPassword       VARCHAR(64)  NULL,
  fldLoginStatus    TINYINT      NULL,
  fldUserStatus     TINYINT      NULL,
  fldLoginErrCount  TINYINT      NULL,
  fldModifyPwdCount TINYINT      NULL,
  fldGender         TINYINT      NULL,
  fldEmail          VARCHAR(256) NULL,
  fldPhone          VARCHAR(32)  NULL,
  fldMobile         VARCHAR(32)  NULL,
  fldFax            VARCHAR(32)  NULL,
  fldAddress        VARCHAR(256) NULL,
  fldPosition       INT          NULL,
  fldOperateId      INT          NULL,
  fldOperateDate    DATETIME     NULL,
  fldCertCN         VARCHAR(32)  NULL,
  fldAccountNo      NVARCHAR(64) NULL,
  fldLastLoginTime  DATETIME     NULL,
  fldGenerateDate   DATETIME     NULL,
  fldSystem         NVARCHAR(4)  NULL,
  fldPhoneExtension    nvarchar(32)         null,
  fldType              nvarchar(4)          null,
  fldPhoneType         nvarchar(4)          null,
  fldPhonePassword     nvarchar(64)         null,
  CONSTRAINT PK_TBLUSER PRIMARY KEY (fldId)
);

CREATE TABLE tblUserDeptLink (
  fldId           INT IDENTITY,
  fldLoginName    NVARCHAR(32) NULL,
  fldDeptCode     VARCHAR(32)  NULL,
  fldGenerateDate DATETIME     NULL,
  fldSystem       NVARCHAR(4)  NULL,
  CONSTRAINT PK_TBLUSERDEPTLINK PRIMARY KEY (fldId)
);

CREATE TABLE tblUserRoleLink (
  fldId     INT IDENTITY,
  fldUserId INT NULL,
  fldRoleId INT NULL,
  CONSTRAINT PK_TBLUSERROLELINK PRIMARY KEY (fldId)
);

ALTER TABLE tblNodeRoleLink ADD CONSTRAINT FK_TBLNODER_REFERENCE_TBLROLE FOREIGN KEY (fldNodeId) REFERENCES tblRole (fldId);

ALTER TABLE tblNodeRoleLink ADD CONSTRAINT FK_TBLNODER_REFERENCE_TBLNODE FOREIGN KEY (fldNodeId) REFERENCES tblNode (fldCode);

ALTER TABLE tblUserDeptLink ADD CONSTRAINT FK_TBLUSERD_REFERENCE_TBLDEPT FOREIGN KEY (fldDeptCode) REFERENCES tblDept (fldDeptCode);

ALTER TABLE tblUserRoleLink ADD CONSTRAINT FK_TBLUSERR_REFERENCE_TBLUSER FOREIGN KEY (fldUserId) REFERENCES tblUser (fldId);

ALTER TABLE tblUserRoleLink ADD CONSTRAINT FK_TBLUSERR_REFERENCE_TBLROLE FOREIGN KEY (fldRoleId) REFERENCES tblRole (fldId);

CREATE TABLE tblFavorite (
  fldId      INT IDENTITY,
  fldCode    VARCHAR(32) NULL,
  fldUserId  INT         NULL,
  fldAddTime DATETIME    NULL,
  fldComment VARCHAR(64) NULL,
  CONSTRAINT PK_TBLFAVORITE PRIMARY KEY (fldId)
);

ALTER TABLE tblFavorite ADD CONSTRAINT FK_TBLFAVOR_REFERENCE_TBLUSER FOREIGN KEY (fldUserId) REFERENCES tblUser (fldId);

ALTER TABLE tblFavorite ADD CONSTRAINT FK_TBLFAVOR_REFERENCE_TBLNODE FOREIGN KEY (fldCode) REFERENCES tblNode (fldCode);

/********************system***************************/
DROP TABLE IF EXISTS tblDictionary;
CREATE TABLE tblDictionary (
  fldId          INT IDENTITY,
  fldType        INT          NOT NULL,
  fldName        VARCHAR(64)  NOT NULL,
  fldValue       VARCHAR(100) NOT NULL,
  fldStatus      TINYINT      NULL,
  fldComment     VARCHAR(400) NULL,
  fldOperateId   INT          NULL,
  fldOperateDate DATETIME     NULL,
  CONSTRAINT PK_TBLDICTIONARY PRIMARY KEY (fldId)
);

DROP TABLE IF EXISTS tblSysParameter;
CREATE TABLE tblSysParameter (
  fldId          INT IDENTITY,
  fldName        VARCHAR(64)  NOT NULL,
  fldValue       VARCHAR(100) NOT NULL,
  fldUnit        VARCHAR(64)  NULL,
  fldStatus      TINYINT      NULL,
  fldDisplay     TINYINT      NULL,
  fldComment     VARCHAR(400) NULL,
  fldOperateId   INT          NULL,
  fldOperateDate DATETIME     NULL,
  CONSTRAINT PK_TBLSYSPARAMETER PRIMARY KEY (fldId)
);

DROP TABLE IF EXISTS tblLog;
CREATE TABLE tblLog (
  fldId          BIGINT IDENTITY,
  fldOperateId   INT           NULL,
  fldOperateDate DATETIME      NULL,
  fldLoginName   VARCHAR(32)   NULL,
  fldAction      VARCHAR(32)   NULL,
  fldResource    VARCHAR(200)  NULL,
  fldComment     VARCHAR(1000) NULL,
  fldBak         VARCHAR(200)  NULL,
  CONSTRAINT PK_TBLLOG PRIMARY KEY (fldId)
);

DROP TABLE IF EXISTS tblSysJob;
CREATE TABLE tblSysJob (
  fldId                INT IDENTITY,
  fldJobName           VARCHAR(32)  NOT NULL,
  fldJobGroup          VARCHAR(32)  NOT NULL,
  fldTriggerName       VARCHAR(32)  NOT NULL,
  fldTriggerGroup      VARCHAR(32)  NOT NULL,
  fldCronExpression    VARCHAR(32)  NULL,
  fldIntervalInSeconds INT          NULL,
  fldRepeatCount       INT          NULL,
  fldTriggerType       INT          NOT NULL,
  fldJobClass          VARCHAR(100) NULL,
  fldStatus            INT          NULL,
  fldOperateId         INT          NULL,
  fldOperateDate       DATETIME     NULL,
  fldLastSuccessDate   DATETIME     NULL,
  fldNextFireTime      DATETIME     NULL,
  fldStartTime         DATETIME     NULL,
  fldEndTime           DATETIME     NULL,
  fldExecuteCount      INT          NULL,
  CONSTRAINT PK_TBLSYSJOB PRIMARY KEY (fldId)
);

DROP TABLE IF EXISTS tblnotice;
create table tblNotice (
  fldId                int                  identity,
  fldTitle             nvarchar(3000)       null,
  fldContent           nvarchar(4000)       null,
  fldLevel             int                  null,
  fldStatus            int                  null,
  fldOperateUserNo     nvarchar(32)         null,
  fldOperateDate       datetime             null,
  fldCreateUserNo      nvarchar(32)         null,
  fldCreateDate        datetime             null,
  constraint PK_TBLNOTICE primary key (fldId)
);
