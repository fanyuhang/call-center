package com.common;

import java.io.File;

public class Constant {

	public static final String NODE_ROOT_CODE = "01"; // 模块根节点编码

	public static final String CURRENT_USER = "currentUser"; // session中当前用户对应的key

	public static final String PRIVILEGE_MASTER_ROLE = "ROLE"; // 权限主体----角色

	public static final String PRIVILEGE_MASTER_USER = "USER"; // 权限主体----用户

	public static final int PRIVILEGE_OPERATION_PERMIT = 1; // 权限操作类型----允许

	public static final int PRIVILEGE_OPERATION_FORBID = 0; // 权限操作类型----禁止

	public static final int LOGIN_STATUS_LOGIN = 1; // 登录状态---已登录

	public static final int LOGIN_STATUS_LOGOUT = 0; // 登录状态---未登录

	public static final String PARAM_MAX_ERROR_LOGIN = "MAX_ERROR_LOGIN"; // 系统参数---最大登陆失败次数

	public static final String PARAM_NODE_ROOT_NAME = "NODE_ROOT_NAME"; // 系统参数---模块管理根节点名称

	public static final String PARAM_HISTORY_COUNTMONEY = "HISTORY_COUNTMONEY"; // 系统参数---历史单次购买金额

	public static final String PARAM_HISTORY_SEVRAL_DAYS = "HISTORY_SEVRAL_DAYS"; // 系统参数---历史单次购买金额

	public static final String PARAM_HISTORY_COUNTMONEY_WITHIN_SEVRAL_DAYS = "HISTORY_COUNTMONEY_WITHIN_SEVRAL_DAYS"; // 系统参数---10天内购买付款金额

	public static final String PARAM_RATE_FOR_MONEY_AND_COUNT = "RATE_FOR_MONEY_AND_COUNT"; // 系统参数---单笔订单总收款金额/卡片数比率

	public static final String PARAM_RATE_FOR_COUNTMONEY_AND_RECEIVABLEMONEY = "RATE_FOR_COUNTMONEY_AND_RECEIVABLEMONEY"; // 系统参数---充值金额/收款金额比率

	public static final String PARAM_SYS_ADMIN = "SYS_ADMIN"; // 系统参数--系统管理员ID

	public static final String PARAM_ID_CARD_PORT = "ID_CARD_PORT"; // 系统参数--身份证读卡器端口

	public static final String PARAM_IC_MAIN_KEY = "IC_MAIN_KEY"; // IC卡根密钥

	public static final String USER_NAME_FORMAT = "%1$s(%2$s)";

	public static final int USER_STATUS_NORMAL = 0; // 用户状态---正常

	public static final int USER_STATUS_NOT_VALID = 1; // 用户状态---注销

	public static final int USER_STATUS_LOCKED = 2; // 用户状态---锁定

	public static final int CARD_TYPE_VALIDATE_UNIT_DAY = 1; // 卡有效期单位 天

	public static final int CARD_TYPE_VALIDATE_UNIT_MONTH = 2; // 月

	public static final int CARD_TYPE_VALIDATE_UNIT_YEAR = 3; // 年

	public static final int NODE_TYPE_MENU = 1; // 模块节点类型----菜单

	public static final int NODE_TYPE_BUTTON = 2; // 模块节点类型----按钮

	public static final String GRID_ORDER_ASC = "asc"; // 排序---升序

	public static final String GRID_ORDER_DESC = "desc"; // 排序---降序
	public static final Integer CARD_STATUS_MAKE = 0; // 卡状态：制卡
	public static final Integer CARD_STATUS_STORE = 1; // 卡状态：入库
	public static final Integer CARD_STATUS_ASSIGN = 2; // 卡状态：领卡
	public static final Integer CARD_STATUS_ACTIVE = 3; // 卡状态：激活
	public static final Integer CARD_STATUS_LOSS = 4; // 卡状态：挂失
	public static final Integer CARD_STATUS_LOGOUT = 5; // 卡状态：销户
	// public static final Integer CARD_STATUS_APPOINT = 2; //卡状态：配卡
	public static final Integer CARDACCOUNT_STATUS_ACTIVE = 1; // 卡户状态：激活
	public static final Integer CARDACCOUNT_STATUS_FREEZE = 2; // 卡户状态：冻结
	public static final Integer CARDACCOUNT_STATUS_LOCK = 3; // 卡户状态：锁定
	public static final Integer CARDACCOUNT_STATUS_EXPIRED_TRANSFER = 4; // 卡户状态：过期余额转出
	public static final Integer CARDACCOUNT_STATUS_LOGOUT = 5; // 卡户状态：销户
	public static final Integer CARD_VALIDATE_UNIT_DAY = 1;
	public static final Integer CARD_VALIDATE_UNIT_MONTH = 2;
	public static final Integer CARD_VALIDATE_UNIT_YEAR = 3;
	public static final Integer TRADE_TYPE_RECHARGE = 2; // 交易类型 充值
	public static final Integer TRADE_TYPE_CHARGE = 3; // 交易类型 消费
	public static final Integer TRADE_TYPE_CANCEL = 4; // 交易类型 退货
	public static final Integer TRADE_TYPE_DELAY_FEE = 5; // 交易类型 延期手续费
	public static final Integer TRADE_TYPE_LOST_FEE = 6; // 交易类型 挂失手续费
	public static final Integer TRADE_TYPE_EXPRIED_TRANSFER = 7; // 交易类型 过期转出
	public static final Integer TRADE_TYPE_REVOKE = 8; // 交易类型 当日撤销
	public static final Integer TRADE_TYPE_REVERSAL = 9; // 交易类型 冲正
	public static final Integer TRADE_TYPE_TRANSFER_OUT = 10; // 交易类型 转出
	public static final Integer TRADE_TYPE_TRANSFER_IN = 11; // 交易类型 转入
	public static final Integer TRADE_TYPE_DELAY_IN = 12; // 交易类型 延期转入
	public static final Integer TRADE_TYPE_CARD_FEE = 13; // 交易类型 押金
	public static final Integer TRADE_TYPE_LOGOUT_RETURN = 14; // 交易类型 销户退款

	public static final Integer CARD_CANCEL_CONFIRM_STATUS_NOT_CONFIRM = 0; // 退货状态申请
	public static final Integer CARD_CANCEL_CONFIRM_STATUS_CONFIRM = 1; // 退货状态批准
	public static final Integer CARD_COMMISSION_FEE_MODE_CARD = 0; // 手续费收取方式，卡中扣除
	public static final Integer CARD_COMMISSION_FEE_MODE_CASH = 1; // 手续费收取方式，现金

	public static final Integer CARD_BIN_LENGTH = 6; // 卡bin号长度 6位
	public static final Integer CARD_AREA_LENGTH = 5; // 区域码长度5位
	public static final Integer CARD_SEQUENCE_LENGTH = 7; // 序列号长度7位
	public static final Integer CARD_NO_LENGTH = 19; // 卡号长度 19位
	public static final Integer CARD_PASSWORD_LENGTH = 6; // 随机密码长度6位

	public static final Integer IDENTITY_TYPE_IDENTITY = 1; // 证件类型 身份请
	public static final Integer CARD_EXPIRED_TRANSFER_TRANSFERBACK = 1; // 过期转出记录，已转回

	public static final Integer CARD_FREEZE_TYPE_FREEZE = 0; // 卡冻结操作，冻结
	public static final Integer CARD_FREEZE_TYPE_UNFREEZE = 1; // 卡冻结操作，解冻

	public static final Integer CARD_LOCK_TYPE_LOCK = 0; // 卡锁定操作，锁定
	public static final Integer CARD_LOCK_TYPE_UNLOCK = 1; // 卡锁定操作，解锁

	public static final Integer CARD_TRNSFER_TYPE_OUT = 0; // 卡转出操作，转出
	public static final Integer CARD_TRNSFER_TYPE_BACK = 1; // 卡转出操作，转回

	public static final Integer ORDERRECHARGE_TYPE_ACTIVE = 1; // 充值队列类型，激活
	public static final Integer ORDERRECHARGE_TYPE_CANCEL = 2; // 充值队列类型，退货

	public static final Integer ORDERRECHARGE_TYPE_ADD = 3; // 充值队列类型，充值

	public static final Integer CARD_IS_REALNAME_REAL = 1; // 卡类型：是否实名，是
	public static final Integer CARD_IS_REALNAME_NOTREAL = 0; // 卡类型：是否实名，否
	public static final Integer CARD_IS_RREE_FREE = 1; // 卡类型：是否赠卡，是
	public static final Integer CARD_IS_FREE_NOTFREE = 0; // 卡类型：是否赠卡，否

	public static final Integer TRADE_SOURCE_REAL_TIME = 0; // 交易来源，实时
	public static final Integer TRADE_SOURCE_QUEUE = 1; // 交易来源，队列
	public static final Integer TRADE_SOURCE_OTHER = 2; // 交易来源，其它

	public static final Integer CUSTOMER_TYPE_ENTERPRISE = 0;
	public static final Integer CUSTOMER_TYPE_PERSONAL = 1;
	// --------------------------------start
	// filter------------------------------------------------
	public static final String FILTER_OP_ADD = "add"; // 查询条件操作符---- +

	public static final String FILTER_OP_BITWISEAND = "bitwiseand"; // 查询条件操作符----
																	// &

	public static final String FILTER_OP_BITWISENOT = "bitwisenot"; // 查询条件操作符----
																	// ~

	public static final String FILTER_OP_BITWISEOR = "bitwiseor"; // 查询条件操作符----
																	// |

	public static final String FILTER_OP_BITWISEXOR = "bitwisexor"; // 查询条件操作符----
																	// ^

	public static final String FILTER_OP_DIVIDE = "divide"; // 查询条件操作符---- /

	public static final String FILTER_OP_EQUAL = "equal"; // 查询条件操作符---- =

	public static final String FILTER_OP_GREATER = "greater"; // 查询条件操作符---- >

	public static final String FILTER_OP_GREATEROREQUAL = "greaterorequal"; // 查询条件操作符----
																			// >=

	public static final String FILTER_OP_ISNULL = "isnull"; // 查询条件操作符---- is
															// null

	public static final String FILTER_OP_ISNOTNULL = "isnotnull"; // 查询条件操作符----
																	// is not
																	// null

	public static final String FILTER_OP_LESS = "less"; // 查询条件操作符---- <

	public static final String FILTER_OP_LESSOREQUAL = "lessorequal";// 查询条件操作符----
																		// <=

	public static final String FILTER_OP_LIKE = "like"; // 查询条件操作符---- like

	public static final String FILTER_OP_STARTWITH = "startwith"; // 查询条件操作符----
																	// like

	public static final String FILTER_OP_ENDWITH = "endwith"; // 查询条件操作符----
																// like

	public static final String FILTER_OP_MODULO = "modulo"; // 查询条件操作符---- %

	public static final String FILTER_OP_MULTIPLY = "multiply"; // 查询条件操作符---- *

	public static final String FILTER_OP_NOTEQUAL = "notequal"; // 查询条件操作符----
																// <>

	public static final String FILTER_OP_SUBTRACT = "subtract"; // 查询条件操作符---- -

	public static final String FILTER_OP_AND = "and"; // 查询条件操作符---- and

	public static final String FILTER_OP_OR = "or"; // 查询条件操作符---- or

	public static final String FILTER_OP_IN = "in"; // 查询条件操作符---- in

	public static final String FILTER_OP_NOTIN = "notin"; // 查询条件操作符---- not in

	public static final String FILTER_TYPE_INT = "int"; // 查询条件数据类型---- int

	public static final String FILTER_TYPE_FLOAT = "float"; // 查询条件数据类型----
															// float

	public static final String FILTER_TYPE_LONG = "long"; // 查询条件数据类型---- long

	public static final String FILTER_TYPE_NUMBER = "number"; // 查询条件数据类型----
																// number

	public static final String FILTER_TYPE_DATE = "date"; // 查询条件数据类型---- date

	public static final String FILTER_TYPE_STRING = "string"; // 查询条件数据类型----
																// string

	public static final String FILTER_CONTEXT_USERID = "{CurrentUserID}"; // 查询条件用户变量----
																			// 用户ID

	public static final String FILTER_CONTEXT_ROLEID = "{CurrentRoleID}"; // 查询条件用户变量----
																			// 角色ID

	public static final String FILTER_CONTEXT_DEPTID = "{CurrentDeptID}"; // 查询条件用户变量----
																			// 部门代码
	// -------------------------------end
	// filter-----------------------------------------------

	public static final int CACHE_NORMAL = 0; // 缓存状态-----正常

	public static final int CACHE_EXPIRED = 1; // 缓存状态-----已过期

	public static final int SYSTEM_ENABLE = 0; // 启用

	public static final int SYSTEM_DISABLE = 1; // 禁用

	public static final String CERTIFICATE_KEY = "javax.servlet.request.X509Certificate";

	public static final String LOG_ADD = "增加";

	public static final String LOG_UPDATE = "修改";

	public static final String LOG_DELETE = "删除";

	public static final String LOG_BATCH_UPDATE = "批量修改";

	public static final String LOG_BATCH_DELETE = "批量删除";

	public enum TriggerType {
		SIMPLE, CRON
	} // 定时任务类型

	public static final Integer JOB_ENABLE = 0;

	public static final Integer JOB_DISABLE = 1;

	public static final Integer JOB_DELETE = 2;

	/**
	 * 订单收款状态,通过审核*
	 */
	public static final Integer ORDER_RECEIPT_STATUS_ACTIVE = 1;
	/**
	 * 订单收款状态,未审核*
	 */
	public static final Integer ORDER_RECEIPT_STATUS_NOT_ACTIVE = 0;

	/**
	 * 配卡签收单导出文件名*
	 */
	public static final String ORDER_ASSIGN_EXPORT_NAME = "配卡签收单.xls";

	/**
	 * 卡延期签收单导出文件名*
	 */
	public static final String CARD_DELAY_EXPORT_NAME = "卡延期签收单.xls";
	public static final String CARD_LOSS_EXPORT_NAME = "卡挂失签收单.xls";

	/**
	 * 订单消费比率导出文件名*
	 */
	public static final String ORDER_TRADE_EXPORT_NAME = "订单消费比率表.xls";

	public static final Integer CUSTOMER_ORDER_RELATED = 1; // 客户已经关联订单

	public static final Integer CUSTOMER_ORDER_NOT_RELATED = 0; // 客户未关联订单

	public static final Integer CUSTOMER_RISK_LEVEL_ONE = 1; // 客户风险等级：1
	public static final Integer CUSTOMER_RISK_LEVEL_TWO = 2; // 客户风险等级：2
	public static final Integer CUSTOMER_RISK_LEVEL_THREE = 3; // 客户风险等级：3
	public static final Integer CUSTOMER_RISK_LEVEL_FOUR = 4; // 客户风险等级：4

	public static final Integer CUSTOMER_ORDER_AUDIT_NEEDED = 1; // 客户下单需要审核(与订单金额关联，在定时任务中更新)
	public static final Integer CUSTOMER_ORDER_AUDIT_NOT_NEEDED = 0; // 客户下单不需要审核(与订单金额关联，在定时任务中更新)

	public static final int MERCHANT_VALID = 0; // 商户状态---有效

	public static final int MERCHANT_INVALID = 1; // 商户状态---无效

	public static final int POS_AVAILABLE = 2; // POS状态---已开通

	public static final int RECEIVED_FEE_WAY_MERCHANT = 0; // 手续费收取方式---商户支出

	public static final int RECEIVED_FEE_WAY_CUSTOMER = 1; // 手续费收取方式---客户支出

	public static final int FEE_WAY_PERCENTAGE = 0; // 扣率方式---百分比

	public static final int FEE_WAY_FIXED = 1; // 扣率方式---固定值

	public static final int INDICATE_TRUE = 1; // 标记，用于冲正标记，撤销标记，退货标记

	public static final int INDICATE_FALSE = 0; // 标记，用于冲正标记，撤销标记，退货标记

	public static final int POS_FEE_ENABLE = 0; // POS费率---启用

	public static final Integer PURCHASE_USAGE_GIFT = 1; // 采购用途 礼品
	public static final Integer PURCHASE_USAGE_WELFARE = 2; // 采购用途 薪资福利
	public static final Integer PURCHASE_USAGE_OTHER = 3; // 采购用途 其它
	public static final Integer PURCHASE_USAGE_LABOR_PROTECTION = 4; // 劳防

	public static final Integer CUSTOMER_STAUTS_AVAILABLE = 1;

	public static final Integer RISK_RECORD_OBJECT_TYPE_MERCHANT = 1; // 可疑交易对象类型
																		// 商户
	public static final Integer RISK_RECORD_OBJECT_TYPE_CARD = 2; // 可疑交易对象类型 卡
	public static final Integer RISK_RECORD_OBJECT_TYPE_CUSTOMER = 3; // 可疑交易对象类型
																		// 客户

	public static final Integer ORDER_INVOICE_STATUS_NOT_AUDITED = 0; // 发票状态
																		// 未复核
	public static final Integer ORDER_INVOICE_STATUS_AUDITED = 1; // 发票状态 已复核
	public static final Integer ORDER_INVOICE_STATUS_RE_PRINT = 2; // 发票状态 重打

	public static final String REPORT_JOB_NAME_PREFIX = "REPORT_JOB_"; // 报表任务默认任务前缀

	public static final String REPORT_JOB_GROUP_NAME_PREFIX = "REPORT_JOB_GROUP_"; // 报表任务默认任务组前缀

	public static final String REPORT_JOB_TRIGGER_NAME_PREFIX = "REPORT_TRIGGER_"; // 报表任务默认周期前缀

	public static final String REPORT_JOB_TRIGGER_GROUP_NAME_PREFIX = "REPORT_TRIGGER_GROUP_";// 报表任务默认周期组前缀

	public static final Integer REPORT_RESULT_SUCCESS = 0; // 报表结果状态 成功

	public static final Integer REPORT_RESULT_FAILURE = 1; // 报表结果状态 失败

	public static final Integer REPORT_EXECUTE_NOT_RUN = 0; // 报表执行状态 未执行
	public static final Integer REPORT_EXECUTE_RUNNING = 1; // 报表执行状态 正在执行
	public static final Integer REPORT_EXECUTE_RUN_SUCCESS = 2; // 报表执行状态 执行成功
	public static final Integer REPORT_EXECUTE_RUN_FAILURE = 3; // 报表执行状态 执行失败

	public static final Integer REPORT_PARAMETER_TYPE_STRING = 0; // 报表参数类型 文本型
	public static final Integer REPORT_PARAMETER_TYPE_INT = 1; // 报表参数类型 数字型
	public static final Integer REPORT_PARAMETER_TYPE_FLOAT = 2; // 报表参数类型 浮点型
	public static final Integer REPORT_PARAMETER_TYPE_DATE = 3; // 报表参数类型 日期型

	public static final Integer MERCHANT_PAYMENT_STATUS_NOT_AUDIT = 0; // 付款审核状态
																		// 待复核
	public static final Integer MERCHANT_PAYMENT_STATUS_AUDITED = 1; // 付款审核状态
																		// 已复核
	public static final Integer MERCHANT_PAYMENT_STATUS_CANCELED = 2; // 付款审核状态
																		// 已取消

	public static final Integer ORDER_PRERECEIPT_STATUS_RECEIVED = 0; // 预收款类型，已收款
	public static final Integer ORDER_PRERECEIPT_STATUS_CANCELED = 1; // 预收款类型，已退款

	public static final Integer ORDER_CUSTOMER_STATUS_DELETE = 0; // 客户状态, 删除
	public static final Integer ORDER_CUSTOMER_STATUS_NORMAL = 1; // 客户状态, 正常

	public static final Integer ORDER_CONSTACTER_STATUS_NORMAL = 0; // 联系人状态, 正常
	public static final Integer ORDER_CONSTACTER_STATUS_DELETE = 1; // 联系人状态, 删除
	public static final Integer ORDER_CONSTACTER_STATUS_NOT_CONFIRM = 2; // 联系人状态，待确认
	public static final Integer ORDER_CONSTACTER_STATUS_CONFIRMED = 3; // 联系人状态，已确认

	public static final Integer MERCHANT_CLEARING_ITEM_START = 0; // 结算项，期初余额
	public static final Integer MERCHANT_CLEARING_ITEM_TRADE = 1; // 结算项，本期消费
	public static final Integer MERCHANT_CLEARING_ITEM_RECEIVE = 2; // 结算项，本期付款
	public static final Integer MERCHANT_CLEARING_ITEM_FEE = 3; // 结算项，本期手续费
	public static final Integer MERCHANT_CLEARING_ITEM_RETURN = 4; // 结算项，本期退货
	public static final Integer MERCHANT_CLEARING_ITEM_END = 5; // 结算项，期末余额

	public static final Integer MERCHANT_CLEARING_STATUS_NOT_YET = 0; // 商户结算状态，待确认
	public static final Integer MERCHANT_CLEARING_STATUS_AUDITED = 1; // 商户结算状态，已确认

	public static final Integer AUDIT_PASSED_STATUS = 2; // 订单状态，已审核
	public static final Integer AUDIT_NOT_PASSED_STATUS = 0; // 订单状态，未审核
	public static final Integer CUSTOMER_AUDIT_NEEDED = 1; // 是否需要审核，需要审核
	public static final Integer CUSTOMER_AUDIT_NOT_NEEDED = 0; // 是否需要审核，不需要审核

	public static final Integer ORDER_RECEIPT_SOURCE_ORDER = 0; // 收款来源, 订单收款
	public static final Integer ORDER_RECEIPT_SOURCE_PRE = 1; // 收款来源, 预收款

	public static final Integer ORDER_RECEIPT_STATUS_NOT_AUDIT = 0; // 收款审核状态,
																	// 未审核
	public static final Integer ORDER_RECEIPT_STATUS_NOT_PASS = 1; // 收款审核状态,
																	// 审核未通过
	public static final Integer ORDER_RECEIPT_STATUS_PASS = 2; // 收款审核状态, 审核已通过

	public static final Integer ORDER_RECEIPT_CANCEL_STATUS_NOT_AUDIT = 0; // 收款取消审核状态,
																			// 未审核
	public static final Integer ORDER_RECEIPT_CANCEL_STATUS_NOT_PASS = 1; // 收款取消审核状态,
																			// 审核未通过
	public static final Integer ORDER_RECEIPT_CANCEL_STATUS_PASS = 2; // 收款取消审核状态,
																		// 审核已通过

	public static final Integer ORDER_STATUS_RECEIVEMONEY_RECEIVE_NOT = 0; // 订单收款状态，未收款
	public static final Integer ORDER_STATUS_RECEIVEMONEY_RECEIVE_PART = 1; // 订单收款状态，部分收款
	public static final Integer ORDER_STATUS_RECEIVEMONEY_RECEIVE_ALL = 2; // 订单收款状态，已收款
	public static final Integer ORDER_STATUS_RECEIVEMONEY_RECEIVE_AUDIT = 3; // 订单收款状态，已复核
	public static final Integer ORDER_STATUS_RECEIVEMONEY_RECEIVE_NOT_PASS = 4; // 订单收款状态，未通过已复核

	public static final Integer ORDER_STATUS_AUDIT_NOT_AUDIT = 0; // 订单审核，未审核
	public static final Integer ORDER_STATUS_AUDIT_NOT_PASS = 1; // 订单审核，未通过
	public static final Integer ORDER_STATUS_AUDIT_AUDITED = 2; // 订单审核，已通过

	public static final Integer ORDER_STATUS_VALID_NO = 0; // 订单状态，无效
	public static final Integer ORDER_STATUS_VALID_YES = 1; // 订单状态，有效

	public static final Integer ORDER_STATUS_ASSIGN_NOT = 0; // 订单配卡状态，未配卡
	public static final Integer ORDER_STATUS_ASSIGN_PART = 1; // 订单配卡状态，部分配卡
	public static final Integer ORDER_STATUS_ASSIGN_ALL = 2; // 订单配卡状态，已配卡

	public static final Integer RISK_TRADE_TYPE_NORMAL = 0; // 异常交易类型，正常交易
	public static final Integer RISK_TRADE_TYPE_ABNORMAL = 1; // 异常交易类型，异常交易

	public static final Integer RISK_TRADE_STATUS_NOT_CONFIRM = 0; // 异常交易状态，待确认
	public static final Integer RISK_TRADE_STATUS_CONFIRMED = 1; // 异常交易状态，已确认

	public static final Integer CARD_LOSS_STATUS_NOT_CONFIRM = 0; // 卡挂失状态，待确认
	public static final Integer CARD_LOSS_STATUS_CONFIRMED = 1; // 卡挂失状态，已确认
	public static final Integer CARD_LOSS_STATUS_CANCEL = 2; // 卡挂失状态，已撤销

	public static final Integer PSAM_STATUS_MAKE = 0; // PSAM卡状态：制卡
	public static final Integer PSAM_STATUS_STORE = 1; // PSAM卡状态：入库
	public static final Integer PSAM_STATUS_ASSIGN = 2; // PSAM卡状态：领卡
	public static final Integer PSAM_STATUS_LOGOUT = 3; // PSAM卡状态：挂失

	public static final Integer CARD_FEE_STATUS_NOT_RETURN = 0; // 卡押金：支付
	public static final Integer CARD_FEE_STATUS_RETURN = 1; // 卡押金：退回

	public static final Integer MERCHANT_CLEARING_HISTORY_STATUS_NOT_PAY = 0; // 商户结算状态，未付款
	public static final Integer MERCHANT_CLEARING_HISTORY_STATUS_PART_PAY = 1; // 商户结算状态，部分付款
	public static final Integer MERCHANT_CLEARING_HISTORY_STATUS_ALL_PAY = 2; // 商户结算状态，已付款
	public static final Integer MERCHANT_CLEARING_HISTORY_STATUS_CONFIRM_PAY = 3; // 商户结算状态，已确认

	public static final Integer ORDER_CONTACTER_EXCHANGE_TYPE_MARKET = 1; // 客户部门交接操作类型，销售
	public static final Integer ORDER_CONTACTER_EXCHANGE_TYPE_SERVICE = 0; // 客户部门交接操作类型，客服

	public static final Integer CARD_PASSWORD_ENABLE_YES = 0; // 卡片是否需要密码，需要
	public static final Integer CARD_PASSWORD_ENABLE_NO = 1; // 卡片是否需要密码，不需要

	public static final Integer CARD_TRADE_SOURCE_MANUAL = 1; // pos交易进入方式，手工输入
	public static final Integer CARD_TRADE_SOURCE_CARD = 2; // pos交易进入方式，磁条卡
	public static final Integer CARD_TRADE_SOURCE_CPU = 7; // pos交易进入方式，cpu卡

	public static final Integer ORDER_STATUS_SEND_NO = 0; // 订单送货状态，未送货
	public static final Integer ORDER_STATUS_SEND_YES = 1; // 订单送货状态，已送货

	public static final Integer ORDER_STATUS_RECEIVE_NO = 0; // 订单收货状态，未收货
	public static final Integer ORDER_STATUS_RECEIVE_YES = 1; // 订单收货状态，已收货

	public static final Integer ORDER_STATUS_INVOICE_NOT = 0; // 订单开票状态，未开票
	public static final Integer ORDER_STATUS_INVOICE_PART = 1; // 订单开票状态，部分开票
	public static final Integer ORDER_STATUS_INVOICE_ALL = 2; // 订单开票状态，已开票
	public static final Integer ORDER_STATUS_INVOICE_RE_PRINT = 3; // 订单开票状态，发票重打

	public static final Integer ORDER_STATUS_RESERVE_NOT = 0; // 订单到账状态，未到账
	public static final Integer ORDER_STATUS_RESERVE_PART = 1; // 订单到账状态，部分到账
	public static final Integer ORDER_STATUS_RESERVE_ALL = 2; // 订单到账状态，已到账

	public static final Integer ORDER_SEND_WAY_SELF = 0; // 订单送货方式，自提
	public static final Integer ORDER_SEND_WAY_KUDI = 1; // 订单送货方式，快递

	public static final Integer ORDER_TYPE_NORMAL = 0; // 订单类型，普通
	public static final Integer ORDER_TYPE_SELL = 1; // 订单类型，售卡订单
	public static final Integer ORDER_TYPE_RECHARGE = 2; // 订单类型，充值订单

	public static final Integer SHANGPIN_STATUS_NORMAL = 0; // 商品状态，正常
	public static final Integer SHANGPIN_STATUS_DELETE = 1; // 商品状态，已删除

	public static final Integer CARD_ACCOUNT_LINK_CUSTOMER_CHANGED_NO = 0; // 账户是否客户自定义：否
	public static final Integer CARD_ACCOUNT_LINK_CUSTOMER_CHANGED_YES = 1; // 账户是否客户自定义：是

	public static final Integer MERCHANT_GROUP_STATUS_NORMAL = 0; // 集团商户状态，正常
	public static final Integer MERCHANT_GROUP_STATUS_DELETE = 1; // 集团商户状态，已删除
	public static final String ORDER_INVOICE_TYPE_OTHER = "99"; // 开票类型，其他

	public static final Integer ORDER_RESERVE_STATSU_NOT_AUDIT = 0; // 订单到账记录状态，正常

	public static final Integer OBJECT_VALID = 0; // 有效
	public static final Integer OBJECT_INVALID = 1; // 无效

	public static final Integer CUSTOMER_STATUS_NORMAL = 0; // 客户状态，有效
	public static final Integer CUSTOMER_STATUS_DIABLED = 9; // 客户状态，无效

	public static final Integer CONTRACT_STATUS_NORMAL = 0; // 合同状态，有效
	public static final Integer CONTRACT_STATUS_DIABLED = 9; // 合同状态，无效

	public static final Integer PRODUCT_STATUS_NORMAL = 0; // 产品状态，有效
	public static final Integer PRODUCT_STATUS_DIABLED = 9; // 产品状态，无效

	public static final Integer CONTRACT_FINISH_STATUS_NO = 0;// 合同是否状态,否
	public static final Integer CONTRACT_FINISH_STATUS_YES = 1;// 合同是否状态,是

	public static final Integer PRODUCT_DETAIL_STATUS_NORMAL = 0; // 产品明细状态，有效
	public static final Integer PRODUCT_DETAIL_STATUS_DIABLED = 9; // 产品明细状态，无效

	public static final Integer MESSAGE_TEMPLATE_STATUS_NORMAL = 0; // 短信模板状态，有效
	public static final Integer MESSAGE_TEMPLATE_STATUS_DIABLED = 9; // 短信模板状态，无效

	public static final Integer EMAIL_TEMPLATE_STATUS_NORMAL = 0; // 邮件模板状态，有效
	public static final Integer EMAIL_TEMPLATE_STATUS_DIABLED = 9; // 邮件模板状态，无效

	public static final Integer DAY_UNIT_DAY = 0; // 天数单位，天
	public static final Integer DAY_UNIT_MONTH = 1;// 天数单位，月

	public static final Integer MESSAGE_SEND_STATUS_SUCCESS = 0; // 短信发送状态，成功
	public static final Integer MESSAGE_SEND_STATUS_FAIL = 9; // 短信发送状态，失败

	public static final Integer DUPLICATE_STATUS_Y = 0;// 是否去重，去重
	public static final Integer DUPLICATE_STATUS_N = 1;// 是否去重，不去重

	public static final String TELEPHONE_IMPORTT_PATH = "telephone" + File.separator + "import";// 话单存放本地路径

	public static final String TELEPHONE_IMPORT_SAVE_TYPE_ORIG = "01"; // 话单导入，原始话单
	public static final String TELEPHONE_IMPORT_SAVE_TYPE_DUP = "02"; // 话单导入，重复话单
	public static final String TELEPHONE_IMPORT_SAVE_TYPE_NO_DUP = "03";// 话单导入，非重复话单

	public static final Integer TELEPHONE_ASSIGN_STATUS_UNASSIGN = 0;// 话单分配状态,未分配
	public static final Integer TELEPHONE_ASSIGN_STATUS_ASSIGNED = 1;// 话单分配状态,已分配

	public static final Integer TASK_FINISH_STATUS_UNFINISH = 0;// 任务完成状态，未完成
	public static final Integer TASK_FINISH_STATUS_DEAL = 1;// 任务完成状态，待跟踪
	public static final Integer TASK_FINISH_STATUS_FINISHED = 9;// 任务完成状态，已完成

	public static final Integer TASK_TYPE_AUTO = 0;// 任务类型,自动分配任务
	public static final Integer TASK_TYPE_MANU = 1;// 任务类型,手动分配任务

	public static final Integer TASK_CALL_STATUS_UN = 0;// 拨打状态,未拨打
	public static final Integer TASK_CALL_STATUS_ED = 1;// 拨打状态,已拨打

	public static final Integer TELEPHONE_SOURCE_IMPORT = 0;// 话务来源，导入话务
	public static final Integer TELEPHONE_SOURCE_CUSTOMER = 1;// 话务来源，已有话务

	public static final Integer TELEPHONE_TASK_AUDIT_STATUS_UN = 0;// 审查状态,待审查
	public static final Integer TELEPHONE_TASK_AUDIT_STATUS_ED = 1;// 审查状态,已审查

	public static final Integer TELEPHONE_CUSTOMER_SOURCE_IMPORT = 0;// 客户来源,话务导入
	public static final Integer TELEPHONE_CUSTOMER_SOURCE_INPUT = 1;// 客户来源,客户录入

	public static final Integer CUSTOMER_SOURCE_TELEPHONE = 1;// 客户来源，电话
	
	public static final Integer TELEPHONE_CALL_TYPE_OUT = 0;//呼叫类型，呼出
	public static final Integer TELEPHONE_CALL_TYPE_IN = 1;//呼叫类型，呼人
}