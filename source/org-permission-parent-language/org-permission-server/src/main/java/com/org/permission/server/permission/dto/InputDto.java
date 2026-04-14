package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有入参对象都会继承这个类
 */
@Data
public class InputDto implements Serializable {

    private static final long serialVersionUID = 4140438841314859339L;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "集团id")
    private Long groupId;
}
