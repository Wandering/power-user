package com.power.wechat.listener;


import com.power.enums.PowerEvent;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/7/31.
 */
public class EventObservableFactory {

    public static final Map<PowerEvent,Observable> observableMap = new ConcurrentHashMap<>();
    public static Observable getObservable(PowerEvent event){
        if (!observableMap.containsKey(event)){
            synchronized (EventObservableFactory.class){
                if (!observableMap.containsKey(event)) {
                    observableMap.put(event,new EventObservable());
                }
            }
        }
        return observableMap.get(event);
    }
}
