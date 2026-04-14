package com.usercenter.server.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * 当前登录用户
 */
public abstract class AbstractUserReq {


    @ApiModelProperty(notes = "当前登录用户ID")
    protected Long userId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
