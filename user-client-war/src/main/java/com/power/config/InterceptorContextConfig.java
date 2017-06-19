package com.power.config;

import com.power.interceptor.CrossInterceptor;
import com.power.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;




@Configuration
public class InterceptorContextConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CrossInterceptor crossInterceptor;

    @Bean
    public CrossInterceptor genCrossInterceptor(){
        return new CrossInterceptor();
    }
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有请求适配跨域处理
        registry.addInterceptor(crossInterceptor)
                .addPathPatterns("/");
        //登录处理
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/")
                .excludePathPatterns("/wechat/login/**");
    }


}
