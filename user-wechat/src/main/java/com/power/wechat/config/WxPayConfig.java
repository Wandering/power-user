package com.power.wechat.config;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/26.
 */
@Configuration
@EnableAutoConfiguration
@Component
public class WxPayConfig {

    @Bean("wxPayService")
    public WxPayService initWxPayService(){
        WxPayService wxPayService = new WxPayServiceImpl();
        com.github.binarywang.wxpay.config.WxPayConfig wxPayConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
        wxPayConfig.setTradeType("JSAPI");
        wxPayConfig.setAppId("wx81a4de5934b52e4d");
        wxPayConfig.setKeyPath("classpath:wx-pay-config/apiclient_cert.p12");
        wxPayConfig.setMchId("1442541002");
        wxPayConfig.setNotifyUrl("http://139.224.16.177:9000/api/app/pay/notify");
        wxPayService.setConfig(wxPayConfig);
        return wxPayService;
    }
}
