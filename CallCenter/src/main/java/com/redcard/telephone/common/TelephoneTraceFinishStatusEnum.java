package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTraceFinishStatusEnum {
    WAIT_FINISH(0, "待跟踪"), ON_FINISH(1, "在联系"), DONE_FINISH(5, "已签约"), NOT_FINISH(9, "拒签约");

    private Integer code;
    private String label;

    TelephoneTraceFinishStatusEnum(Integer code, String label) {
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
        return "TelephoneTraceFinishStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
