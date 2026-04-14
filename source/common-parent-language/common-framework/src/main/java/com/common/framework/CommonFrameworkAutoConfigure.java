package com.common.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {"com.common.framework"})
public class CommonFrameworkAutoConfigure {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
