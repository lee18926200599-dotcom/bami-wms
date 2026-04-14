package com.usercenter.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("DTO通用参数")
public class CommonDto {

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人", required = true)
    protected Long operateUser;

    public Long getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Long operateUser) {
        this.operateUser = operateUser;
    }
}
