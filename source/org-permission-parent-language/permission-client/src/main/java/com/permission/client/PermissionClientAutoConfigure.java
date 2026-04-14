package com.permission.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@ComponentScan(basePackages = {"com.permission.client"})
public class PermissionClientAutoConfigure {
}
