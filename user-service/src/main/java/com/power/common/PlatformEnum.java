package com.power.common;

/**
 * Created by Administrator on 2017/6/14.
 */
public enum PlatformEnum {
    WX(1);
    private final Integer code;

    PlatformEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
