package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTraceAssignStatusEnum {
    WAIT_ASSIGN(0, "待分配"), DONE_ASSIGN(1, "已分配");

    private Integer code;
    private String label;

    TelephoneTraceAssignStatusEnum(Integer code, String label) {
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
        return "TelephoneTraceAssignStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
