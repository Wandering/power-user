package com.power.domain;

/**
 * Created by Administrator on 2017/6/12.
 */
public enum  ERRORCODE {
    /////////////////////////////第三方平台异常/////////////////////////
    CODE_BEEN_USED("4000001","code been used"),
    /////////////////////////////第三方平台异常/////////////////////////



    /////////////////////////////系统异常/////////////////////////

    /////////////////////////////系统异常/////////////////////////


    /////////////////////////////系统业务异常/////////////////////////
    SMS_CHECK_EXIST("2000001","验证码已经发出，请60秒后重试！"),
    SMS_CHECK_ERROR("2000002","验证码校验失败，请重新输入！"),
    SMS_CHECK_FAIL("2000003","验证码发送失败，请稍后再试！"),
    SMS_CHECK_PHONE("2000004","不是标准的手机号码，请输入正确的手机号码！"),
    TOKEN_CANNOT_NULL("2000005","token不能为空"),
    TOKEN_INVALID_OR_NOTHINGNESS("2000005","token失效或不存在"),


    /////////////////////////////系统业务异常/////////////////////////





    /////////////////////////////业务系统相关业务异常/////////////////////////


    PLATFORM_IS_NULL("5000001","服务号不存在" ),
    USER_IS_NULL("5000002","非法用户" )
    ;
    /////////////////////////////业务系统相关业务异常/////////////////////////
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
