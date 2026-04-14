package com.common.language.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebLangConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver(){
        return new I18nLocaleResolver();
    }
}
