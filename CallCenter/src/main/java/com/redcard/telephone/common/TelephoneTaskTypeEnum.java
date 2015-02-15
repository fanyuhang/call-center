package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTaskTypeEnum {
    ASSIGN(0, "分配"),RESERVE(1, "预约");

    private Integer code;
    private String label;

    TelephoneTaskTypeEnum(Integer code, String label) {
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
        return "TelephoneTaskTypeEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
