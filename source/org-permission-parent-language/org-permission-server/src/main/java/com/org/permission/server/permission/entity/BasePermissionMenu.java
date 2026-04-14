package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_permission_menu实体类  用户表中管理员和菜单关系表管理
*/ 
@Data
@TableName(value = "base_permission_menu")
public class BasePermissionMenu extends BaseEntity implements Serializable {
	public String comment = "用户表中管理员和菜单关系表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	//管理员id
	@TableField(value = "admin_id")
	private Long adminId;
	//管理员名称
	@TableField(value = "admin_name")
	private String adminName;
	//权限菜单id
	@TableField(value = "menu_id")
	private Long menuId;
	//权限菜单名称
	@TableField(value = "menu_name")
	private String menuName;

}

