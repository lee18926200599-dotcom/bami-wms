package com.common.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.common.mybatis.MultiTenantHandler;
import com.common.mybatis.TenantProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class MybatisPlusConfig {

    /* 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
     *
     * @param tenantProperties
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(TenantProperties tenantProperties) {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (Boolean.TRUE.equals(tenantProperties.getEnable())) {
            // 启用多租户插件拦截
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MultiTenantHandler(tenantProperties)));
        }

        return interceptor;
    }

}
