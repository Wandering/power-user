package com.power.core.restful;

import com.power.core.exception.BizException;
import com.power.core.protocol.ResponseT;
import com.power.core.protocol.ResponseTs;
import com.power.core.utils.RtnCodeEnum;
import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qyang on 14/10/30.
 */
public class BizExceptionHandler implements RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BizExceptionHandler.class);
    public static final String errorMsgPattern = "invoke {} error: {} ";

    @Override
    public ResponseEntity handleException(Exception exception, HttpServletRequest request) {
        boolean isDebug = false;
        if (request.getParameter("debug") != null) {
            isDebug = true;
        }
//        Response response = new Response.ResponseBuilder((BizException) exception).build();
        ResponseT<String> responseT = internalHandleException(exception, request, isDebug);

        return new ResponseEntity<>(responseT, HttpStatus.OK);
        //return null;
    }

    private ResponseT<String> internalHandleException(Exception exception, HttpServletRequest request, boolean isDebug){
        ResponseT<String> responseT = null;
        if (exception instanceof BizException) { //业务预知的异常
            responseT = ResponseTs.<String>newResponseException((BizException) exception, isDebug);
            logger.error(errorMsgPattern, request.getRequestURL(), ((BizException) exception).getMsg());
        } else {
            //构造 BizException
            BizException bizException = null;
            if (isDebug) {
                bizException = new BizException(RtnCodeEnum.UNKNOW.getValue(), RtnCodeEnum.UNKNOW.getDesc(), exception.getMessage());
            } else {
                bizException = new BizException(RtnCodeEnum.UNKNOW.getValue(), RtnCodeEnum.UNKNOW.getDesc());
            }
            responseT = ResponseTs.<String>newResponseException(bizException, isDebug);
            logger.error(errorMsgPattern, request.getRequestURL(), exception);
        }

        return responseT;
    }
}
