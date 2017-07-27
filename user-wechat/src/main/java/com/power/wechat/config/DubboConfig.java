package com.power.wechat.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/26.
 */
@ImportResource("classpath:dubbo.xml")
@Configuration
@EnableAutoConfiguration
@Component
public class DubboConfig {
}
