package com.power.core.restful;

import com.power.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 14/11/25 下午3:05<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class TestModel<T> {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionHandler.class);
    public static final String errorMsgPattern = "invoke {} error: {} stacktrace: {}";

    private String s1;
    private T s2;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public T getS2() {
        return s2;
    }

    public void setS2(T s2) {
        this.s2 = s2;
    }

    public static void main(String[] args) {

        BizException bizException = new BizException("code", "msg");
        bizException.setStackTrace(Thread.currentThread().getStackTrace());
        System.out.println(Thread.currentThread().getStackTrace()[1]);
        logger.error(errorMsgPattern, "url", bizException.getMsg(), bizException.getStackTrace());
    }
}
