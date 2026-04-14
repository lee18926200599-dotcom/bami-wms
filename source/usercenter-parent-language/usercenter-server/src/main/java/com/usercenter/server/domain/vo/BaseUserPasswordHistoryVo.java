package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户历史密码记录管理
 */

@ApiModel(description = "用户历史密码记录", value = "用户历史密码记录")
@Data
public class BaseUserPasswordHistoryVo extends QueryPager implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}

