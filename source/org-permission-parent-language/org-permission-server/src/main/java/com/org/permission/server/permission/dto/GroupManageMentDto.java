package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团返回对象
 */
@Data
@ApiModel(description = "集团返回对象 ")
public class GroupManageMentDto implements Serializable {

    private static final long serialVersionUID = 8293589065168338383L;
    @ApiModelProperty(value = "管理维度id")
    private Integer managementId;
    @ApiModelProperty(value = "集团id")
    private Long groupId;
    @ApiModelProperty(value = "状态 1=启用 0=未启用")
    private Integer state;
}
