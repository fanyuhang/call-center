package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneTaskStatusEnum {
    NOT_FINISH(0, "未完成"),WAITING_FINISH(1, "待跟踪"), DONE_FINISH(9, "已完成");

    private Integer code;
    private String label;

    TelephoneTaskStatusEnum(Integer code, String label) {
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
        return "TelephoneAssignFinishStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
