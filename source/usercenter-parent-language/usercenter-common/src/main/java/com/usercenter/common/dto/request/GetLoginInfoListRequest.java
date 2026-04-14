package com.usercenter.common.dto.request;

import com.usercenter.common.enums.BusinessSystemEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取登陆列表请求
 **/
@ApiModel(value = "获取登陆列表请求")
public class GetLoginInfoListRequest {

    @ApiModelProperty(notes = "用户ID", required = true)
    private String token;

    @ApiModelProperty(notes = "业务系统，获取登陆某个业务系统的信息")
    private BusinessSystemEnum businessSystem;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BusinessSystemEnum getBusinessSystem() {
        return businessSystem;
    }

    public void setBusinessSystem(BusinessSystemEnum businessSystem) {
        this.businessSystem = businessSystem;
    }

    public GetLoginInfoListRequest(String token, BusinessSystemEnum businessSystem) {
        this.token = token;
        this.businessSystem = businessSystem;
    }

    public GetLoginInfoListRequest() {
    }
}
