package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户功能权限
 */
@Data
@ApiModel(description="用户功能权限")
public class EnableUserPermission implements Serializable {

	private static final long serialVersionUID = 4725456195756039959L;
	@ApiModelProperty(value="权限id")
	private Long permissionId;
	@ApiModelProperty(value="组织编码")
	private String orgCode;
	@ApiModelProperty(value="组织名称")
	private String orgName;
	@ApiModelProperty(value="功能权限资源名称")
	private String resourceName; 
	@ApiModelProperty(value="所属系统编码")
	private Long belong;
	@ApiModelProperty(value="父级权限资源id")
	private Long parentId;
	@ApiModelProperty(value="权限分类名称 该字段暂时没用，扩展使用")
	private String permissionShow; 
	@ApiModelProperty(value="权限分类标识 该字段暂时没用，扩展使用")
	private String permissionGroup; 
	@ApiModelProperty(value="资源跳转url")
	private String url; 
	@ApiModelProperty(value="资源标签，唯一标识")
	private String tag; 
	@ApiModelProperty(value="权限主体  APP,PC,RF")
	private String subject; 
	@ApiModelProperty(value="权限类型：菜单、页签、按钮等")
	private String type; 
	@ApiModelProperty(value="状态 1：有效  0：失效")
	private Integer state; 

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EnableUserPermission userPermission = (EnableUserPermission) o;
		return permissionId > 0 ? permissionId == userPermission.permissionId : permissionId == 0;
	}

}
