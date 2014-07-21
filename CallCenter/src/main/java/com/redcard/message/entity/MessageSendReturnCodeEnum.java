package com.redcard.message.entity;

public enum MessageSendReturnCodeEnum {

	SUCCESS(1, "发送成功"),

	CODE1(-1, "余额不足"),

	CODE2(-2, "帐号或密码错误"),

	CODE3(-3, "连接服务商失败"),

	CODE4(-4, "超时"),

	CODE5(-5, "其他错误，一般为网络问题，IP受限等"),

	CODE6(-6, "短信内容为空"),

	CODE7(-7, "目标号码为空"),

	CODE8(-8, "用户通道设置不对，需要设置三个通道"),

	CODE9(-9, "捕获未知异常"),

	CODE10(-10, "超过最大定时时间限制"),

	CODE11(-11, "目标号码在黑名单里"),

	CODE12(-12, "消息内容包含禁用词语"),

	CODE13(-13, "没有权限使用该网关"),

	CODE14(-14, "找不到对应的Channel ID"),

	CODE15(-17, "没有提交权限，客户端帐号无法使用接口提交"),

	CODE20(-20, "超速提交(一般为每秒一次提交)");

	private int code;

	private String desc;

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	MessageSendReturnCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getDescByCode(int code) {
		for (MessageSendReturnCodeEnum e : MessageSendReturnCodeEnum.values()) {
			if (code == e.getCode()) {
				return e.getDesc();
			}
		}
		return String.valueOf(code);
	}
}