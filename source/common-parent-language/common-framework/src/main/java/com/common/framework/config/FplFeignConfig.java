package com.common.framework.config;

import com.common.framework.filter.FplFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FplFeignConfig {

    @Bean
    public RequestInterceptor fplFeignInterceptor() {
        return new FplFeignInterceptor();
    }
}
