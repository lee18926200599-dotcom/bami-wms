package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 用户组织权限
 */
@Data
@ApiModel(description = "用户组织权限")
public class UserOrgPermissionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户id")
	private Long userId;
	@ApiModelProperty(value = "权限类型，功能权限：func，组织权限：org，数据权限：data")
	private String permissionType;
	@ApiModelProperty(value = "权限id")
	private Long permissionId;
	@ApiModelProperty(value="授权人id")
	private Long author;
	@ApiModelProperty(value = "授权人")
	private String authorUser;
	@ApiModelProperty(value = "授权时间")
	private Date authorTime;
	@ApiModelProperty(value = "状态：1有效，0失效")
	private Integer state;
	@ApiModelProperty(value = "组织编码")
	private String orgCode;
	@ApiModelProperty(value = "组织名称")
	private String orgName;
	@ApiModelProperty(value = "组织类型")
	private String orgType;
	@ApiModelProperty(value = "集团id")
	private Long groupId;
	@ApiModelProperty(value = "父级节点id")
	private Long parentId;
	@ApiModelProperty(value = "是否选中")
	private boolean check;
	@ApiModelProperty(value = "组织权限子节点")
	private List<UserOrgPermissionDto> childOrgs;
	@ApiModelProperty(value = "节点下用户列表")
	private List<UserDto> userList;
	@ApiModelProperty("业务线ID集合")
	private List<Long> lineIdList;
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserOrgPermissionDto userOrgPermissionDto = (UserOrgPermissionDto) o;
		return permissionId > 0 ? permissionId.equals(userOrgPermissionDto.permissionId) : permissionId.equals(0L);
	}

}
