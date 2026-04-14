package com.org.permission.common.permission.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 菜单或者按钮权限查询
 */
@Data
public class MenuButtonPermissionParam implements Serializable {
    private static final long serialVersionUID = 4077629253015411920L;
    @ApiModelProperty("用户子表id")
    private Long userId;
    @ApiModelProperty("集团ID")
    private Long groupId;
    @ApiModelProperty("菜单/按钮ID")
    private Long menuId;
}
