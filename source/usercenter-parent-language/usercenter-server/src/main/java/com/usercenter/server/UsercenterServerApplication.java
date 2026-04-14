package com.usercenter.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@MapperScan("com.usercenter.server.mapper")
@EnableFeignClients
@ComponentScan({"com.usercenter","com.permission.client", "com.org.client","com.common","com.basedata"})
public class UsercenterServerApplication{

    public static void main(String[] args) {
        SpringApplication.run(UsercenterServerApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
