package com.power.wechat.listener;

import java.util.Observable;

/**
 * Created by Administrator on 2017/7/31.
 */
public class EventObservable extends Observable{
    public EventObservable() {
        setChanged();
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change,
     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>.
     * This method is called automatically by the
     * <code>notifyObservers</code> methods.
     *
     * @see Observable#notifyObservers()
     * @see Observable#notifyObservers(Object)
     */
    @Override
    protected synchronized void clearChanged() {
        setChanged();
    }
}
