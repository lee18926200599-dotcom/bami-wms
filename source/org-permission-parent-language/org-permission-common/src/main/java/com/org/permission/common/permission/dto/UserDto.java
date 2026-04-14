package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
* 角色批量设置用户返回用户信息
 */
@ApiModel(description="用户信息")
@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="用户id")
    private Long userId;
	@ApiModelProperty(value="组织机构id")
    private Long orgId;
	@ApiModelProperty(value="用户编码")
    private String userCode;
	@ApiModelProperty(value="用户账号")
    private String userName;
	@ApiModelProperty(value="用户真实姓名")
    private String realName;
	@ApiModelProperty(value="唯一主键")
	private String key;
	@ApiModelProperty(value="唯一名称")
	private String label;
	@ApiModelProperty(value="选中：true,未选中：false")
	private boolean check;
}
