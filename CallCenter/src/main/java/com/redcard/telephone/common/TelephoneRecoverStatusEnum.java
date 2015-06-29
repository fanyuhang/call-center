package com.redcard.telephone.common;

/**
 * Created by thinkpad on 14-8-11.
 */
public enum TelephoneRecoverStatusEnum {
    NOT(0, "未回收"),DONE(1, "已回收");

    private Integer code;
    private String label;

    TelephoneRecoverStatusEnum(Integer code, String label) {
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
        return "TelephoneRecoverStatusEnum{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
