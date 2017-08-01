package com.power.dto;

/**
 * Created by Administrator on 2017/7/31.
 */
public class WxEvent extends Event{
    private String openId;
    private String uniqueKey;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
