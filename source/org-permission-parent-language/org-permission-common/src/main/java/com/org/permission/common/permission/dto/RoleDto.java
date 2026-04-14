package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分配角色，角色返回对象
 */
@Data
@ApiModel(description = "角色返回对象")
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "名称节点")
    private String name;
    @ApiModelProperty(value = "所在部门组织名称节点")
    private String orgName;
    @ApiModelProperty(value = "是否选中")
    private boolean check;

}
