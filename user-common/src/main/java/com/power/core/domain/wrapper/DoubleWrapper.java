package com.power.core.domain.wrapper;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/4/21 下午12:03<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class DoubleWrapper extends BaseWrapper {
    private double d;


    public DoubleWrapper() {

    }

    public DoubleWrapper(double d) {
        this.d = d;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
