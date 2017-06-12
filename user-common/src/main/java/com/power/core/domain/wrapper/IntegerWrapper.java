package com.power.core.domain.wrapper;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/4/21 上午11:24<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class IntegerWrapper extends BaseWrapper {
    private int i;

    public IntegerWrapper() {
    }

    public IntegerWrapper(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
