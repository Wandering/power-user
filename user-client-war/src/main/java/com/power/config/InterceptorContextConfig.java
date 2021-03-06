package com.power.config;

import com.power.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;




@Configuration
public class InterceptorContextConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求适配跨域处理
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录处理
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/wechat/login/**/**")
                .excludePathPatterns("/wechat/callback/**/**")
                .excludePathPatterns("/platform/wechat/**/**")
                .excludePathPatterns("/user/wechat/info/**/**")
                .excludePathPatterns("/error")
        ;
    }


}
