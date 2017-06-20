package com.power;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Administrator on 2017/6/8.
 */
@SpringBootApplication
@MapperScan("com.power.dao")
public class PowerUserStarter extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PowerUserStarter.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PowerUserStarter.class, args);
    }
}
