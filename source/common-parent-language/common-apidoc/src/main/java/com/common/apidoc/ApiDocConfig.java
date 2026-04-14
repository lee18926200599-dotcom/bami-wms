package com.common.apidoc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
@ComponentScan(basePackages = {"com.common.apidoc"})
@EnableSwagger2
@Slf4j
@ConfigurationProperties(prefix = "apidoc")
public class ApiDocConfig {

    private Map<String, ApiDocGroupConfig> groups;
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    private static final String SWAGGER_DOCKET_PREFIX = "swaggerDocket_";

    public void setGroups(Map<String, ApiDocGroupConfig> groups) {
        this.groups = groups;
    }

    @PostConstruct
    public void dockets() {
        try {
            if (groups == null || groups.isEmpty()) {
                log.warn("API Doc group configuration list is empty or null.");
                return;
            }
            groups.forEach((key, value) -> {
                if (!isValidGroupConfig(value)) {
                    log.warn("Invalid API Doc group configuration: {}", value);
                    return;
                }
                String beanName = SWAGGER_DOCKET_PREFIX + key;
                if (!applicationContext.containsBean(beanName)) {
                    registerDocket(beanName, value);
                    log.info("Registered Swagger Docket bean: {}", beanName);
                }
            });
        } catch (BeansException e) {
            log.error("Failed to register Swagger Docket beans", e);
        }
    }

    private boolean isValidGroupConfig(ApiDocGroupConfig group) {
        if (group == null) {
            return false;
        }
        if (group.getBasePackage() == null || group.getBasePackage().isEmpty()) {
            log.warn("API Doc group configuration basePackage is null or empty for group: {}", group.getGroupName());
            return false;
        }
        return true;
    }

    private void registerDocket(String beanName, ApiDocGroupConfig apiDocGroupConfig) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        beanFactory.registerSingleton(beanName, createDocket(apiDocGroupConfig));
    }

    private Docket createDocket(ApiDocGroupConfig apiDocGroupConfig) {
        log.debug("Creating Swagger Docket for group: {}", apiDocGroupConfig.getGroupName());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(apiDocGroupConfig.getGroupName())
                .apiInfo(apiInfo(apiDocGroupConfig))
                .enable(apiDocGroupConfig.getEnable() != null && apiDocGroupConfig.getEnable()).select()
                .apis(RequestHandlerSelectors.basePackage(apiDocGroupConfig.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(ApiDocGroupConfig properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .build();
    }

}
