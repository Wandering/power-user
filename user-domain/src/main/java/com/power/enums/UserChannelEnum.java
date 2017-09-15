package com.power.enums;

/**
 * Created by Administrator on 2017/9/12.
 */
public enum  UserChannelEnum {
    AGENCY_STATION(0),//桩关注
    AGENCY_USER(1), //代理商推荐
    USER(2),//用户推荐
    PLATFORM(3),//宇能公众号

    ;
    private final int code;

    UserChannelEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
