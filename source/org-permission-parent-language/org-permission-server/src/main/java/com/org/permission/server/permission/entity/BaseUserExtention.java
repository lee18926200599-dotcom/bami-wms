package com.org.permission.server.permission.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_user_extention实体类  用户扩展信息管理
*/ 
@Data
public class BaseUserExtention implements Serializable {
	public String comment = "用户扩展信息";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	private Long id;
	private Long userId; //用户ID 
	private String userKey; //关键字 
	private String userValue; //值
}

