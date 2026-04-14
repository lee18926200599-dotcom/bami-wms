package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "编辑用户入参")
public class InputUserUpdateDto implements Serializable {
    private static final long serialVersionUID = 8931430522778517162L;
    @ApiModelProperty(value = "编辑的用户id")
    private Long userId;
    @ApiModelProperty(value = "编辑的所属业务单元id")
    private Long orgId;
    @ApiModelProperty(value = "编辑的所属集团id")
    private Long groupId;
    @ApiModelProperty(value = "操作人：登录的用户id")
    private Long loginUserId;
    @ApiModelProperty(value = "操作人：登录的用户名")
    private String loginUserName;
    @ApiModelProperty(value = "角色id")
    private List<UserRoleDto> roleList;
    @ApiModelProperty(value = "功能权限id")
    private List<com.org.permission.common.permission.dto.UserPermissionDto> funcList;
    @ApiModelProperty(value = "组织权限id")
    private List<com.org.permission.common.permission.dto.UserPermissionDto> orgList;
    @ApiModelProperty(value = "数据权限id")
    private List<UserPermissionDto> dataList;
}
