package com.redcard.system.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum NoticeStatusEnum {
    ENABLE(0, "启用"),DISABLE(1, "禁用");

    private Integer code;
    private String label;

    NoticeStatusEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "NoticeStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
