package com.org.permission.server;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Slf4j
@SpringBootApplication
@EnableFeignClients
@MapperScan({"com.org.permission.server.permission.mapper","com.org.permission.server.org.mapper"})
@Configuration
@ComponentScan({"com.basedata","com.boss.crm","com.usercenter","com.org.permission","com.github","com.common"})
public class OrgPermissionServerApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(OrgPermissionServerApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
