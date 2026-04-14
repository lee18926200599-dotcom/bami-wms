package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  角色权限相关参数
 */
@Data
public class RolePermissionParam implements Serializable {
    private static final long serialVersionUID = 6107832875850772999L;
    @ApiModelProperty("用户ID")
    private Long userId = 0L;
    @ApiModelProperty("角色ID")
    private Long roleId = 0L;
    @ApiModelProperty("集团ID")
    private Long groupId = 0L;
    @ApiModelProperty("权限类型")
    private String permissionType;
    @ApiModelProperty("只显示选中的 0=否 1=是")
    private Integer showSelected = 0;
}
