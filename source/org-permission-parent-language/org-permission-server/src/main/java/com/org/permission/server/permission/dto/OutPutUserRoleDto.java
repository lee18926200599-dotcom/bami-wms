package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户模块，用户下的角色列表出参
 */
@Data
@ApiModel(description = "用户下的角色列表出参")
public class OutPutUserRoleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户角色主键id")
    private Long id;
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "生效时间")
    private Date effectiveTime;
    @ApiModelProperty(value = "失效时间")
    private Date expireTime;
    @ApiModelProperty(value = "授权时间、创建时间")
    private Date authorTime;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "授权人、创建人")
    private String authorUser;
    @ApiModelProperty(value = "授权人id、创建人id")
    private Long author;

}
