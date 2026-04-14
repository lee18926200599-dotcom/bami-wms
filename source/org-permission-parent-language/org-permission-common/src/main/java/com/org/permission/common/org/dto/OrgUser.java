package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织用户信息
 */
@ApiModel
@Data
public class OrgUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("集团 ID")
	private Long groupId;

	@ApiModelProperty("根业务单元 ID")
	private Long rootBUId;

	@ApiModelProperty("用户 ID")
	private Long userId;

	@ApiModelProperty("用户密码")
	private String password;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("电子邮件")
	private String email;

	@ApiModelProperty("人员id")
	private Long id;

	private BaseAddressDto addressDetail;

	public OrgUser() {
	}
}
