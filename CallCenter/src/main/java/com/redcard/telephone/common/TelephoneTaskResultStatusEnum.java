package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTaskResultStatusEnum {
    RESERVE(5, "预约");

    private Integer code;
    private String label;

    TelephoneTaskResultStatusEnum(Integer code, String label) {
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
        return "TelephoneTaskResultStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
