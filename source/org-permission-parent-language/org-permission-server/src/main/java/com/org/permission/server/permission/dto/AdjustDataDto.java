package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel
public class AdjustDataDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "登录人id", required = true)
    private Long loginUserId;
    @ApiModelProperty(value = "登录人id")
    private String loginUserName;
    @ApiModelProperty("选择的用户id")
    private Long userId;
    @ApiModelProperty("选择的角色id")
    private Long roleId;
    @ApiModelProperty("管理维度id")
    private Integer managementId;
    @ApiModelProperty(value = "集团ID", required = true)
    private Long groupId;
    @ApiModelProperty("勾选的数据权限id")
    private List<Long> dataIds;
}
