package com.usercenter.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登陆信息
 **/
@ApiModel(value = "登陆信息")
public class LoginInfoDto {


    /**
     * 用户登陆的key，可以用于登出用户
     */
    @ApiModelProperty(notes = "用户登陆的key，可以用于登出用户", required = true)
    private String key;

    /**
     * token
     */
    private String token;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LoginInfoDto(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginInfoDto(String key, String token) {
        this.key = key;
        this.token = token;
    }
}
