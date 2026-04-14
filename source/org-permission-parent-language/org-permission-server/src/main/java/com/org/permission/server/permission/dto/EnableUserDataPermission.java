package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户数据权限
 */
@Data
@ApiModel(description = "用户数据权限返回对象")
public class EnableUserDataPermission implements Serializable {

    private static final long serialVersionUID = -7003589083698368158L;
    @ApiModelProperty(value = "权限id（数据源ID）")
    private Long permissionId;
    @ApiModelProperty(value = "关联权限id")
    private Long dataId;
    @ApiModelProperty(value = "国标编码")
    private String gbCode; 
    @ApiModelProperty(value = "状态 1：有效，0：失效")
    private Integer state; 
    @ApiModelProperty(value = "组织id")
    private Long orgId; 
    @ApiModelProperty(value = "集团id")
    private Long groupId;  
    @ApiModelProperty(value = "管理维度名称")
    private String name;
    @ApiModelProperty(value = "管理维度id")
    private Integer managementId;
    @ApiModelProperty(value = "分配方式")
    private String distributionType;  
    @ApiModelProperty(value = "分类依据")
    private String based;  
    @ApiModelProperty(value = "类别/个体")
    private String dataResource;
    @ApiModelProperty(value = "类别/个体")
    private String dataResourceCode;
    @ApiModelProperty(value = "父级id")
    private Long parentId;
    @ApiModelProperty(value = "操作权限(查询、维护) 1：查询、2：查询和维护")
    private Integer optionPermission;  


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EnableUserDataPermission userDataPermission = (EnableUserDataPermission) o;
        return dataId > 0 ? dataId == userDataPermission.dataId : dataId == 0;
    }
}
