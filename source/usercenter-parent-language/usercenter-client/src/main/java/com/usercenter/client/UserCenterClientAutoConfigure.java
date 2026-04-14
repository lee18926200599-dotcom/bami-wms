package com.usercenter.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@ComponentScan(basePackages = {"com.usercenter.client"})
public class UserCenterClientAutoConfigure {
}
