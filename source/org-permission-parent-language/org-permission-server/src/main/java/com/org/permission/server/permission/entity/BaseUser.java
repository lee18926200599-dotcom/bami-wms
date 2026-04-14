package com.org.permission.server.permission.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
*base_user实体类  用户基本信息表管理
*/ 
@Data
public class BaseUser extends BaseEntity implements Serializable {
	public String comment = "用户基本信息表";
	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
	private String userCode; //用户编码
	private Long orgId; //所属组织 
	private Long groupId; //用户所属集团 
	private Integer source; //注册来源 
	private String realName; //真实姓名 
	private Integer managerLevel; //管理员等级 
	private Integer identityType; //身份类型
	private Integer lockFlag; //锁定状态（0未锁定，1锁定）
	private String password; //用户密码 
	private String userName; //用户帐号 
	private String phone; //手机号码 
	private String email; //电子邮件 
	private Date effectiveDate; //生效时间
	private Date expireTime; //失效日期
	private String remark; //备注信息
}

