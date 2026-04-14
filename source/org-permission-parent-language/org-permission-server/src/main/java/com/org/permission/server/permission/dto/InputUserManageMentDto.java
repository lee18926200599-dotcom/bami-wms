package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  数据权限授权，角色-输入参数
 */
@Data
public class InputUserManageMentDto implements Serializable {

    private static final long serialVersionUID = 5480685320338633966L;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "管理维度id")
    private Integer managementId;
}
