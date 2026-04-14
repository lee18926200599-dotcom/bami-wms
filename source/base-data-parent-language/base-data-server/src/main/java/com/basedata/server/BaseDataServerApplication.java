package com.basedata.server;

import com.common.framework.config.jackson.JacksonConverterConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.basedata.server.mapper")
@ComponentScan(basePackages = {"com.basedata.*","com.permission.client","com.org.client","com.common.*","com.usercenter"})
@Import({JacksonConverterConfig.class})
public class BaseDataServerApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(BaseDataServerApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
