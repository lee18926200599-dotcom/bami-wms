package com.webgateway;

import com.webgateway.util.AutowiredBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.codec.ServerCodecConfigurer;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.webgateway", "com.usercenter","com.github","com.common.language"})
@EnableDiscoveryClient
public class WebGatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebGatewayApplication.class, args);
        AutowiredBean.setApplicationContext(run);
    }

    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }
}