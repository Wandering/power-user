package com.power.wechat.listener.event;

import com.power.enums.PowerEvent;
import com.power.wechat.listener.EventObservableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class RegeditObserver {
    @Autowired
    private LastSendMsg lastSendMsg;

    @PostConstruct
    private void init(){
        EventObservableFactory.getObservable(PowerEvent.RETURN_END).addObserver(lastSendMsg);
    }
}
