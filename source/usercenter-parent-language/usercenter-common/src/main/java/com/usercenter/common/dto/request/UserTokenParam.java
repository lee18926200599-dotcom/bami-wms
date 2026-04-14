package com.usercenter.common.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserTokenParam implements Serializable {
    private static final long serialVersionUID = 7911410273738280959L;
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "集团ID")
    private Long groupId;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
