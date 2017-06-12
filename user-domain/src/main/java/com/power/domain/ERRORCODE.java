package com.power.domain;

/**
 * Created by Administrator on 2017/6/12.
 */
public enum  ERRORCODE {
    CODE_BEEN_USED("4000001","code been used"),






    PLATFORM_IS_NULL("5000001","服务号不存在" );
    private final String code;
    private final String message;

    ERRORCODE(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
