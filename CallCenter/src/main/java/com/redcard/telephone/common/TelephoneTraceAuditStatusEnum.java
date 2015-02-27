package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTraceAuditStatusEnum {
    WAIT_AUDIT(0, "待审核"), NOT_AUDIT(1,"已退回"), DONE_AUDIT(9, "已审核");

    private Integer code;
    private String label;

    TelephoneTraceAuditStatusEnum(Integer code, String label) {
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
        return "TelephoneTraceAuditStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
