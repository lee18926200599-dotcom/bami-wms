package com.usercenter.common.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 登出请求
 */
@ApiModel(value = "登出请求")
public class LogoutUserReq {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "需要登出的key")
    private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LogoutUserReq(String token, String... keys) {
        this.token = token;
        if (!ObjectUtils.isEmpty(keys)) {
            this.keys = Lists.newArrayList(keys);
        }
    }


    public LogoutUserReq() {
    }
}
