package com.org.permission.server.permission.vo;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
*用户表中管理员和菜单关系表管理
*/ 
@Data
 @ApiModel(description = "用户表中管理员和菜单关系表", value = "用户表中管理员和菜单关系表")
public class BasePermissionMenuVo extends BaseQuery implements Serializable {
	@ApiModelProperty(value="") 
	private Integer id; // 
	@ApiModelProperty(value="管理员id") 
	private Long adminId; //管理员id 
	@ApiModelProperty(value="管理员名称") 
	private String adminName; //管理员名称 
	@ApiModelProperty(value="权限菜单id") 
	private Integer menuId; //权限菜单id 
	@ApiModelProperty(value="权限菜单名称") 
	private String menuName; //权限菜单名称 
	@ApiModelProperty(value="创建时间") 
	private Long createTime; //创建时间 
	@ApiModelProperty(value="创建人") 
	private Long createUser; //创建人 
	@ApiModelProperty(value="修改人") 
	private Long updateUser; //修改人 
	@ApiModelProperty(value="修改时间") 
	private Long updateTime; //修改时间 
	@ApiModelProperty(value="状态") 
	private Integer state; //状态
	//创建人id
	private Long createdBy;
	//创建人
	private String createdName;
	//创建日期
	private Date createdDate;
	//修改人id
	private Long modifiedBy;
	//修改人
	private String modifiedName;
	//修改时间
	private Date modifiedDate;
}

