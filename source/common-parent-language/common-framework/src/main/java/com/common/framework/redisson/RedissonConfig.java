package com.common.framework.redisson;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:null}")
    private String host;
    @Value("${spring.redis.port:null}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.cluster.nodes:null}")
    private String nodes;
    @Value("${spring.redis.timeout:3000}")
    private int timeout;

    @Bean
    public RedissonClient redisson(){
        Config config = new Config();
        if(StrUtil.isNotEmpty(nodes) && !nodes.equals("null")){
            ClusterServersConfig clusterServersConfig = config.useClusterServers().setScanInterval(2000);
            Arrays.stream(nodes.split(",")).forEach(x->{
                clusterServersConfig.addNodeAddress("redis://"+x);
            });
            clusterServersConfig.setTimeout(timeout);
            if(!StringUtils.isEmpty(password)){
                clusterServersConfig.setPassword(password);
            }
        }else {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress( StrFormatter.format("redis://{}:{}",host,port) );
            singleServerConfig.setDatabase(3);
            singleServerConfig.setTimeout(timeout);
            if(!StringUtils.isEmpty(password)){
                singleServerConfig.setPassword(password);
            }
        }
        return Redisson.create(config);
    }
}
