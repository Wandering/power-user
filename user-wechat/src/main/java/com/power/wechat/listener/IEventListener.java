package com.power.wechat.listener;

import com.power.dto.WxEvent;

import java.awt.*;
import java.util.EventListener;

/**
 * Created by Administrator on 2017/7/31.
 * 借电事件
 *
 */
public interface IEventListener extends EventListener {
    void eventDispatched(WxEvent event);
}
