package com.webgateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RefreshScope
@Component
public class NoLoginConfig {

    @Value("${nologin.interface:}")
    private String noLoginInterface;


    /**
     * @Description: 读取配置中心无需登录接口
     * @Documented NoLoginConfig
     * 
     * @Date 2023/10/26
     */
    public Set<String> getNoLoginInterface() {
        Set<String> set= new HashSet<>();
        set.add("/zzz-usercenter-server/user/login");
        if (StringUtils.isNotBlank(noLoginInterface)) {
            set.addAll(Stream.of(noLoginInterface.split(",")).collect(Collectors.toList()));
            return set;
        }
        return set;
    }

}
