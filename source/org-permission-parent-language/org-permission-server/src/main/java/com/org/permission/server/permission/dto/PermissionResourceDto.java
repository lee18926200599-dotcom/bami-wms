package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  权限资源(菜单、按钮)
 */
@Data
public class PermissionResourceDto implements Serializable {

    private static final long serialVersionUID = 5126983341349700804L;
    @ApiModelProperty(value = "资源主键id")
    private Long id;
    @ApiModelProperty(value = "资源名称")
    private String name;
    @ApiModelProperty(value = "资源父编码")
    private Long parentId;
    @ApiModelProperty(value = "资源描述")
    private String resourceDesc;
    @ApiModelProperty(value = "相关地址")
    private String url;
    @ApiModelProperty(value = "类型，0=PC，1=APP,2=RF")
    private Integer type;
    @ApiModelProperty(value = "资源科目1=菜单 2=按钮")
    private Integer resourceType;
    @ApiModelProperty(value = "所属系统")
    private Long belong;
    @ApiModelProperty(value = "所属系统")
    private String bussinessSystem;
    @ApiModelProperty(value = "权限资源分组标识")
    private String permissionGroup;
    @ApiModelProperty(value = "控制展示：0不控制，1控制")
    private Integer controlDisplay;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "叶子节点标识")
    private Integer leafFlag;
    @ApiModelProperty(value = "icon")
    private String icon;
    @ApiModelProperty(value = "唯一标识")
    private String number;
    @ApiModelProperty(value = "是否隐藏")
    private Integer hidden;
    @ApiModelProperty(value = "标记")
    private String target;
    @ApiModelProperty(value = "组件")
    private String component;
    @ApiModelProperty(value = "是否缓存")
    private Integer keepAlive;
    @ApiModelProperty(value = "重定位")
    private String redirect;
    @ApiModelProperty(value = "排序")
    private Integer menuOrder;
    @ApiModelProperty(value = "iframe路径")
    private String iframePath;
    @ApiModelProperty(value = "页面类型 0=代码开发 1=设计器开发")
    private Integer source;
    @ApiModelProperty(value = "权限标识")
    private String operations;
    @ApiModelProperty(value = "是否携带token")
    private Integer token;
    @ApiModelProperty(value = "是否选中 --选中：true，未选中：false")
    private Integer check;
    @ApiModelProperty(value = "deleted_flag")
    private Integer deletedFlag;
    //创建人id
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    //创建人
    @ApiModelProperty(value = "创建人")
    private String createdName;
    //创建日期
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;
    //修改人id
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    //修改人
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    //修改时间
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

   private List<PermissionResourceDto> children;
}
