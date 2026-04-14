package com.org.permission.server.permission.vo;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限资源表管理
 */

@ApiModel(description = "数据权限资源表", value = "数据权限资源表")
@Data
public class BasePermissionDataVo extends BaseQuery implements Serializable {
    @ApiModelProperty(value = "")
    private Long id; //
    @ApiModelProperty(value = "管理维度id(base_permission_management.id)")
    private Integer managementId; //管理维度id(base_permission_management.id)
    @ApiModelProperty(value = "分配方式")
    private String distributionType; //分配方式
    @ApiModelProperty(value = "分类依据(分类依据不为空，分配方式就是按类别，为空，就是按个体)")
    private String based; //分类依据(分类依据不为空，分配方式就是按类别，为空，就是按个体)
    @ApiModelProperty(value = "所属组织id（冗余仓库字段）")
    private Long orgId; //所属组织id（冗余仓库字段）
    @ApiModelProperty(value = "集团id")
    private Long groupId; //集团id
    @ApiModelProperty(value = "")
    private String gbCode; //
    @ApiModelProperty(value = "各个业务线数据权限id")
    private String dataResourceId; //各个业务线数据权限id
    @ApiModelProperty(value = "各个业务线数据权限名称（具体的 类别/个体）")
    private String dataResource; //各个业务线数据权限名称（具体的 类别/个体）
    @ApiModelProperty(value = "各个业务线数据权限编码")
    private String dataResourceCode;
    @ApiModelProperty(value = "父级id")
    private Long parentId; //父级id
    @ApiModelProperty(value = "操作权限(查询、维护)")
    private Integer optionPermission; //操作权限(查询、维护)

    @ApiModelProperty(value = "状态")
    private Integer state; //状态
    @ApiModelProperty(value = "创建人")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

    public BasePermissionDataVo(String gbCode, String dataResourceId, String dataResource, Long parentId) {
        this.gbCode = gbCode;
        this.dataResourceId = dataResourceId;
        this.dataResource = dataResource;
        this.parentId = parentId;
    }
}

