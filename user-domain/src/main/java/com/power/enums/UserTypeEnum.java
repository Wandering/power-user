package com.power.enums;

/**
 * Created by Administrator on 2017/9/12.
 */
public enum UserTypeEnum {
    AGENCY(0),
    USER(1),
    ;
    private final int code;

    UserTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
