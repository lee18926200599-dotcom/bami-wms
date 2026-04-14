package com.webgateway.util;

import cn.hutool.json.JSONUtil;
import com.common.util.message.RestMessage;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GatewayUtil {
    public static Mono<Void> erorrResponse(ServerWebExchange exchange, RestMessage res) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            if ("Bad credentials".equals(res.getMessage())) {
//                res.setMessage(I18nUtils.getMessage("geteway.username.or.pwd.error"));
                res.setMessage("Username or password is incorrect");
            }
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(res).getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Flux.just(buffer));
        } catch (Exception e) {
            e.printStackTrace();
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JSONUtil.toJsonStr(RestMessage.newInstance(false, "请求失败")).getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Flux.just(buffer));
        }
    }
}
