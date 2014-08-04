package com.phone.constants;

/**
 * Created by thinkpad on 14-4-10.
 */
public enum PhoneTypeEnum {
    NONE("0","无"),
    ORDINARY("1","普通"),
    MONITOR("2","班长");

    private String code;

    private String label;

    private PhoneTypeEnum(String code, String label){
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
