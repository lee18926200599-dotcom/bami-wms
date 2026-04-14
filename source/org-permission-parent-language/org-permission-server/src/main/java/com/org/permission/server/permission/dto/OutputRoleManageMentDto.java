package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限授权，角色-输入参数
 */
@Data
public class OutputRoleManageMentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "角色权限主键id")
    private Long id;
    @ApiModelProperty(value = "数据权限主键id")
    private Long dataId;
    @ApiModelProperty(value = "数据维度id")
    private Integer managementId;
    @ApiModelProperty(value = "类别/个体")
    private String dataResource;
    @ApiModelProperty(value = "1：查询，2：查询和维护")
    private Integer optionPermission;
    @ApiModelProperty(value = "查询")
    private boolean query;
    @ApiModelProperty(value = "维护")
    private boolean edit;
    @ApiModelProperty(value = "授权人")
    private String userName;
    @ApiModelProperty(value = "授权时间")
    private Date authorTime;
    @ApiModelProperty(value = "授权人用户id")
    private Long authorUser;

}
