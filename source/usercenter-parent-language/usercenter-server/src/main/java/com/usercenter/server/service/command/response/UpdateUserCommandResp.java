package com.usercenter.server.service.command.response;


import com.usercenter.server.common.enums.ReturnCodesEnum;

import java.io.Serializable;

/**
 * 更新用户返回值
 */
public class UpdateUserCommandResp implements Serializable {

    private ReturnCodesEnum returnCodesEnum;


    private String message;


    public ReturnCodesEnum getReturnCodesEnum() {
        return returnCodesEnum;
    }

    public UpdateUserCommandResp setReturnCodesEnum(ReturnCodesEnum returnCodesEnum) {
        this.returnCodesEnum = returnCodesEnum;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UpdateUserCommandResp setMessage(String message) {
        this.message = message;
        return this;
    }
}
