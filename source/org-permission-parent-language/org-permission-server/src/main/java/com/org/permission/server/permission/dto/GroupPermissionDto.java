package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 集团功能权限
 */
@Data
@ApiModel(description = "集团功能权限")
public class GroupPermissionDto implements Serializable {


	private static final long serialVersionUID = 6202273289768281651L;
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "集团id")
	private Long groupId;
	@ApiModelProperty(value = "权限id")
	private Long permissionId;
	@ApiModelProperty(value = "资源名称")
	private String name;
	@ApiModelProperty(value = "资源父编码")
	private Long parentId;
	@ApiModelProperty(value = "资源描述")
	private String resourceDesc;
	@ApiModelProperty(value = "相关地址")
	private String url;
	@ApiModelProperty(value = "类型，0=PC，1=APP")
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
	private Boolean leafFlag;
	@ApiModelProperty(value = "icon")
	private String icon;
	@ApiModelProperty(value = "唯一标识")
	private String number;
	@ApiModelProperty(value = "是否隐藏")
	private Boolean hidden;
	@ApiModelProperty(value = "标记")
	private String target;
	@ApiModelProperty(value = "组件")
	private String component;
	@ApiModelProperty(value = "是否缓存")
	private Boolean keepAlive;
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
	@ApiModelProperty(value = "平台ID")
	private String platformId;

	private List<GroupPermissionDto> childFuncs;

}
