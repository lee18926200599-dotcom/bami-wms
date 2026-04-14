package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  数据权限授权，角色-输入参数
 */
@Data
public class InputRoleManageMentDto implements Serializable {


    private static final long serialVersionUID = -4894112837350936812L;
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "管理维度id")
    private Integer managementId;
}
