package com.power.wechat.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/26.
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {

//    @Bean("wxPayService")
//    public WxPayService initWxPayService(){
//        WxPayService wxPayService = new WxPayServiceImpl();
//        com.github.binarywang.wxpay.config.WxPayConfig wxPayConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
//        wxPayConfig.setTradeType("JSAPI");
//        wxPayConfig.setAppId("wx0a6f912b64eaf720");
//        wxPayConfig.setKeyPath("/wx-pay-config/apiclient_cert.p12");
//        wxPayConfig.setMchId("1442541002");
//        wxPayConfig.setNotifyUrl("http://139.224.16.177:9000/api/app/pay/notify");
//        wxPayConfig.setMchKey("popularPOWER2016POPULARpower2016");
//        wxPayService.setConfig(wxPayConfig);
//        return wxPayService;
//    }


        @Autowired
        private WxPayProperties properties;

        @Bean
        @ConditionalOnMissingBean
        public WxPayConfig config() {
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(this.properties.getAppId());
            payConfig.setMchId(this.properties.getMchId());
            payConfig.setMchKey(this.properties.getMchKey());
            payConfig.setSubAppId(this.properties.getSubAppId());
            payConfig.setSubMchId(this.properties.getSubMchId());
            payConfig.setKeyPath(this.properties.getKeyPath());
            return payConfig;
        }

        @Bean
        //@ConditionalOnMissingBean
        public WxPayService wxPayService(WxPayConfig payConfig) {
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(payConfig);
            return wxPayService;
        }
}
