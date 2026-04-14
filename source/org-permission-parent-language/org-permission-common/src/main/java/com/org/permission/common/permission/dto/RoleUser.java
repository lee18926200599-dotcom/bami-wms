package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description="角色下的用户返回对象")
public class RoleUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="用户角色主键id")
	private Integer id;
	@ApiModelProperty(value="用户id")
	private Long userId;
	@ApiModelProperty(value="用户编码")
	private String userCode;
	@ApiModelProperty(value="用户账号")
	private String userName;
	@ApiModelProperty(value="所属组织名称")
	private String orgName;
	@ApiModelProperty(value="关联生效时间")
	private Date effectiveTime;
	@ApiModelProperty(value="关联失效时间")
	private Date expireTime;
	@ApiModelProperty(value="授权用户id")
	private Long author;
	@ApiModelProperty(value="授权用户")
	private String authorUser;
	@ApiModelProperty(value="授权时间")
	private Long authorTime;
	@ApiModelProperty("所属组织id")
	private Long orgId;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("人员名称")
	private String realName;
}
