package com.power.wechat.util;

import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/6/10.
 */
@Component
public class WxMpSpringConfig {

    @Bean(name = "wxMpServiceImpl")
    public WxMpServiceImpl autowiredWxMpService(){
        return new WxMpServiceImpl();
    }
}
