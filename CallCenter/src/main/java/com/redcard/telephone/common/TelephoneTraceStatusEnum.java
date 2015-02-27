package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTraceStatusEnum {
    VALID(0, "有效"), INVALID(1, "无效");

    private Integer code;
    private String label;

    TelephoneTraceStatusEnum(Integer code, String label) {
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
        return "TelephoneTraceStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
