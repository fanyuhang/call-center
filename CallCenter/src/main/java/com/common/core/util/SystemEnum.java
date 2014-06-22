package com.common.core.util;

/**
 * Created by thinkpad on 14-3-20.
 */
public enum SystemEnum {
    SYSTEM_SELF(0);

    private Integer code;

    private SystemEnum(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
