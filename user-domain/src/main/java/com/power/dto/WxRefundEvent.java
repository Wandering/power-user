package com.power.dto;

/**
 * Created by Administrator on 2017/8/29.
 */
public class WxRefundEvent extends WxEvent{
    private Integer fee;

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
