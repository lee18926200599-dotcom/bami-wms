package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  数据权限相关参数
 */
@Data
public class UserDataPermissionParam implements Serializable {
    @ApiModelProperty("当前登录用户ID")
    private Long loginUserId;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("集团ID")
    private Long groupId;
    @ApiModelProperty("维度ID")
    private Integer managementId;
    @ApiModelProperty("分配方式")
    private String distributionType;
    @ApiModelProperty("分配依据")
    private String based;
    @ApiModelProperty("数据源")
    private String dataResource;

}
