package com.org.permission.common.permission.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserMenuParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户子表id")
    private Long userId;
    @ApiModelProperty(value = "集团ID", required = true)
    private Long groupId;
    @ApiModelProperty(value = "用户身份级别", required = true)
    private Integer managerLevel;
    @ApiModelProperty(value = "菜单主体（0=PC、1=APP base_permission_resource表type字段）", required = true)
    private String source;
}
