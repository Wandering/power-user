package com.power.http;

import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */
@Component
public class BizHttpClient {

    @Autowired
    private ConfigurableApplicationContext context;

    private  static  final Logger logger = LoggerFactory.getLogger(BizHttpClient.class);
    private  static  final  String PRO_BASE_URL = "http://service.popularpowers.com";
    private  static  final  String DEV_BASE_URL = "http://localhost:9000";

    private  static  final  String REG_USER_URI = "/biz/user/register/notify";

    private static String BASE_URL;

    @PostConstruct
    public void init(){
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        StringUtils.arrayToCommaDelimitedString(activeProfiles);
        BASE_URL = StringUtils.arrayToCommaDelimitedString(activeProfiles).startsWith("prod")?PRO_BASE_URL:DEV_BASE_URL;
    }

    public void syncRegUserToBiz(Long accountId){
        logger.debug("调用远程生成用户信息:{}",accountId);
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        URI uri = null;
        try {
            uri = new URI(BASE_URL+REG_USER_URI+"?accountId="+accountId);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ClientHttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestFactory.createRequest(uri, HttpMethod.POST);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientHttpResponse httpResponse = null;
        try {
            httpResponse = httpRequest.execute();
            if (httpResponse.getStatusCode()== HttpStatus.OK){
                logger.debug("远程调用成功");
            }else {
                throw new BizException(ERRORCODE.SERVICE_ERROR.getCode(),ERRORCODE.SERVICE_ERROR.getMessage());
            }
        } catch (IOException e) {
            try {
                httpResponse = httpRequest.execute();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

}
