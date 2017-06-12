package com.power.core.domain.wrapper;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/4/21 上午11:23<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class BooleanWrapper extends BaseWrapper {
    private boolean b;

    public BooleanWrapper() {
    }

    public BooleanWrapper(boolean b) {
        this.b = b;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
