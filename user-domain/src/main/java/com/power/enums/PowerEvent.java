package com.power.enums;

/**
 * Created by Administrator on 2017/7/31.
 * 非即时处理消息事件模型
 */
public enum PowerEvent {
    RETURN_START,//还电开始
    RETURN_END,//还电结束
    RETURN_FAIL,//还电失败
    BORROW_START,//借电开始
    BORROW_END,//借电结束
    BORROW_FAIL,//借电失败
    ORDER_REFUND,//用户退款
    ORDER_REFUND_SUCCESS,//用户退款成功
    ORDER_REFUND_FAIL,//用户退款失败
    ;
}
