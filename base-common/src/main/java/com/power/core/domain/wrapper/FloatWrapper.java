package com.power.core.domain.wrapper;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/4/21 下午12:03<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class FloatWrapper extends BaseWrapper {
    private float f;

    public FloatWrapper() {

    }
    public FloatWrapper(float f) {
        this.f = f;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
}
