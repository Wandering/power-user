package com.power.wechat.listener.event;

import com.power.enums.PowerEvent;
import com.power.wechat.listener.EventObservableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/7/31.
 * 本地事件监听服务注册
 */
@Component
public class RegeditObserver {
    @Autowired
    private RefundEvent refundEvent;
    @Autowired
    private RefundAcceptMsgEvent refundAcceptMsgEvent;
    @Autowired
    private LastSendEvent lastSendEvent;
    @PostConstruct
    private void init(){
        EventObservableFactory.getObservable(PowerEvent.RETURN_END).addObserver(lastSendEvent);
        EventObservableFactory.getObservable(PowerEvent.ORDER_REFUND).addObserver(refundAcceptMsgEvent);
        EventObservableFactory.getObservable(PowerEvent.ORDER_REFUND).addObserver(refundEvent);
    }
}
