package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "新增用户入参")
public class InputUserDto implements Serializable {


    private static final long serialVersionUID = 5916678774533844844L;
    @ApiModelProperty(value = "新增成功的用户id")
    private Long userId;
    @ApiModelProperty(value = "新增成功用户的所属业务单元id")
    private Long orgId;
    @ApiModelProperty(value = "新增成功用户的所属集团id")
    private Long groupId;
    @ApiModelProperty(value = "操作人：登录的用户id")
    private Long loginUserId;
    @ApiModelProperty(value = "操作人：登录的用户id")
    private String loginUserName;
    @ApiModelProperty(value = "角色对象(角色id，生效时间，失效时间)")
    private List<UserRoleDto> roleList;
    @ApiModelProperty(value = "功能权限id")
    private List<Integer> funcList;
    @ApiModelProperty(value = "组织权限id")
    private List<Long> orgList;
    @ApiModelProperty(value = "数据权限")
    private List<UserPermissionDto> dataList;
}
