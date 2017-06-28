package com.power.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/6/28.
 */
@Component
public class BizHttpClient {
    @Value("${spring.profiles.active}")
    private String active;

    private  static  final Logger logger = LoggerFactory.getLogger(BizHttpClient.class);
    private  static  final  String PRO_BASE_URL = "http://www.popularpowers.com";
    private  static  final  String DEV_BASE_URL = "http://localhost:9000";

    private  static  final  String REG_USER_URI = "/biz/user/register/notify";

    private static String BASE_URL;

    @PostConstruct
    public void init(){
        BASE_URL = active.startsWith("dev")?DEV_BASE_URL:PRO_BASE_URL;
    }

    public void syncRegUserToBiz(Long accountId){
        logger.debug("调用远程生成用户信息:{}",accountId);
        OkHttpClientHttpRequestFactory httpClientHttpRequestFactory = new OkHttpClientHttpRequestFactory();
        URI uri = null;
        try {
            uri = new URI(BASE_URL+REG_USER_URI);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ClientHttpRequest httpRequest = httpClientHttpRequestFactory.createRequest(uri,HttpMethod.POST);
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        httpHeaders.add("accountId",accountId.toString());
        ClientHttpResponse httpResponse = null;
        try {
            httpResponse = httpRequest.execute();
            if (httpResponse.getStatusCode()== HttpStatus.OK){
                logger.debug("远程调用成功");
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
