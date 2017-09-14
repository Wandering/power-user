package com.power.enums;

/**
 * Created by Administrator on 2017/9/12.
 */
public enum  UserChannelEnum {
    AGENCY_STATION(0),
    AGENCY_USER(1),
    USER(2),
    PLATFORM(3),

    ;
    private final int code;

    UserChannelEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
