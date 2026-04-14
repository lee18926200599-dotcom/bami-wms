package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  角色数据权限
 */
@Data
public class RoleDataPermissionParam implements Serializable {
    private static final long serialVersionUID = 9158262366793660053L;
    @ApiModelProperty("当前登录用户ID")
    private Long loginUserId;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("集团ID")
    private Long groupId;
    @ApiModelProperty("权限维度ID")
    private Integer managementId=0;
    @ApiModelProperty("分配方式")
    private String distributionType;
    @ApiModelProperty("分类依据")
    private String based;
    @ApiModelProperty("数据资源名称")
    private String dataResource;
}
