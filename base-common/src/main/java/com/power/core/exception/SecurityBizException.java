package com.power.core.exception;

/**
 * 业务权限异常 ExceptionHandler会对其做特殊处理
 * <p>
 * 创建时间: 16/9/23 下午8:44<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class SecurityBizException extends BizException {
    public SecurityBizException(String errorCode, String message) {
        super(errorCode, message);
    }

    public SecurityBizException(String errorCode, String message, String developMsg) {
        super(errorCode, message, developMsg);
    }

    public SecurityBizException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public SecurityBizException(String errorCode, String message, String developMsg, String uri) {
        super(errorCode, message, developMsg, uri);
    }
}
