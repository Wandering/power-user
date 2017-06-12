package com.power.core.domain.wrapper;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/4/21 上午11:23<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class LongWrapper extends BaseWrapper {
    private long l;

    public LongWrapper() {
    }

    public LongWrapper(long l) {
        this.l = l;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }
}
