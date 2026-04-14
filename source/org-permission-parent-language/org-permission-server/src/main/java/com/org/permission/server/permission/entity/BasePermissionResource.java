package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_resource实体类  功能权限资源表(tree)管理
*/ 
@ApiModel(description="资源对象")
@Data
@TableName(value = "base_permission_resource")
public class BasePermissionResource extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String comment = "功能权限资源表(tree)";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//资源名称
	@TableField(value = "name")
	private String name;
	//资源父编码
	@TableField(value = "parent_id")
	private Long parentId;
	//资源描述
	@TableField(value = "resource_desc")
	private String resourceDesc;
	//相关地址
	@TableField(value = "url")
	private String url;
	//类型，0=PC，1=APP
	@TableField(value = "type")
	private Integer type;
	//资源类型1=菜单 2=按钮
	@TableField(value = "resource_type")
	private Integer resourceType;
	//所属系统
	@TableField(value = "belong")
	private Long belong;
	//所属系统
	@TableField(value = "bussiness_system")
	private String bussinessSystem;
	//权限资源分组标识
	@TableField(value = "permission_group")
	private String permissionGroup;
	//控制展示：0不控制，1控制
	@TableField(value = "control_display")
	private Integer controlDisplay;

	//叶子节点标识
	@TableField(value = "leaf_flag")
	private Integer leafFlag;
	//icon
	@TableField(value = "icon")
	private String icon;
	//唯一标识
	@TableField(value = "number")
	private String number;
	//是否隐藏
	@TableField(value = "hidden")
	private Integer hidden;
	//标记
	@TableField(value = "target")
	private String target;
	//组件
	@TableField(value = "component")
	private String component;
	//是否缓存
	@TableField(value = "keep_alive")
	private Integer keepAlive;
	//重定位
	@TableField(value = "redirect")
	private String redirect;
	//排序
	@TableField(value = "menu_order")
	private Integer menuOrder;
	//iframe路径
	@TableField(value = "iframe_path")
	private String iframePath;
	//页面类型 0=代码开发 1=设计器开发
	@TableField(value = "source")
	private Integer source;
	//权限标识
	@TableField(value = "operations")
	private String operations;
	//是否携带token
	@TableField(value = "token")
	private Integer token;
	//是否删除
	@TableField(value = "deleted_flag")
	private Integer deletedFlag;

	@TableField(value = "platform_id")
	private String platformId;
}

