package com.webgateway.filter;

import com.alibaba.fastjson.JSON;
import com.common.util.message.RestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.usercenter.client.feign.UserFeign;
import com.webgateway.config.NoLoginConfig;
import com.webgateway.util.AutowiredBean;
import com.webgateway.util.MatchUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Slf4j
public class ZGlobalFilter implements GlobalFilter, Ordered {




    @Autowired
    private NoLoginConfig prop;


    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("开始权限验证...");
        //非鉴权接口直接方行
        Set<String> noLoginInterface = prop.getNoLoginInterface();
        String path = exchange.getRequest().getPath().value();
        ServerHttpResponse response = exchange.getResponse();
        RestMessage<Object> restMessage = RestMessage.doSuccess(null);
        if (MatchUtils.antMatchAny(noLoginInterface,path)||MatchUtils.antMatchAny(noLoginInterface,path.replace("/api", ""))) {
            //继续执行其他过滤器链
            return chain.filter(exchange);
        }

        //获取指定请求参数
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String userId = headers.getFirst("userId");
        String token = headers.getFirst("token");
        if (StringUtils.isBlank(token)) {
            token = headers.getFirst("Authorization");
            token = StringUtils.isBlank(token) ? null : token.replace("Bearer ", "");
        }
        //如果请求参数为空，则停止当前过滤器链的执行，直接作出响应
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(token)) {
            log.info("用户名或者token为空");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            restMessage.setCode(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
            restMessage.setSuccess(false);
//            restMessage.setMessage(I18nUtils.getMessage("geteway.username.or.token.isnull"));
            restMessage.setMessage("The username or token is empty");
            return result(exchange, restMessage);
        } else {
            //鉴权
            UserFeign user = AutowiredBean.getBean(UserFeign.class);
            RestMessage<String> rest = user.isTokenValid(Long.valueOf(userId), token);
            if (rest.isSuccess()) {
                //放行
                log.info("userId:{},鉴权通过", userId);
                return chain.filter(exchange);
            } else {
                //响应无权限
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                restMessage.setCode(String.valueOf(HttpStatus.UNAUTHORIZED));
                restMessage.setSuccess(false);
                restMessage.setMessage(rest.getMessage());
            }
        }
        return result(exchange, restMessage);
    }


    private Mono<Void> result(ServerWebExchange exchange, RestMessage<Object> restMessage) throws JsonProcessingException {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(restMessage).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return 1;
    }
}