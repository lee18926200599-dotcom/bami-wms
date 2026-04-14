package com.usercenter.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用于请求用户api的实体承载类，主要收集一些业务线信息
 */
@ApiModel("用于请求用户api的实体承载类")
public class UserApiReq<T> implements Serializable {

    private static final long serialVersionUID = -8886002480690054343L;
    /**
     * 请求实体
     */
    @ApiModelProperty(value = "请求实体")
    private T requestBody;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "调用该接口应用名称")
    private String appName;

    public UserApiReq() {
    }

    /**
     * 用户接口请求构造
     *
     * @param requestBody 请求体
     * @param appName     业务线信息
     */
    public UserApiReq(T requestBody, String appName) {
        this.requestBody = requestBody;
        this.appName = appName;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public String getAppName() {
        return appName;
    }

    public static UserApiReq getInstance() {
        return new UserApiReq<>();
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
