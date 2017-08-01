package com.power.wechat.listener;

import com.power.dto.Event;
import com.power.dto.WxEvent;
import org.springframework.stereotype.Service;

import java.util.Observable;

/**
 * Created by Administrator on 2017/7/31.
 */
@Service("EventListenerImpl")
public class EventListenerImpl implements IEventListener {

    @Override
    public void eventDispatched(WxEvent event) {
        EventObservableFactory.getObservable(event.getEvent()).notifyObservers(event);
    }
}
