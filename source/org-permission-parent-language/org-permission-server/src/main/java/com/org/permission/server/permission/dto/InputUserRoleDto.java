package com.org.permission.server.permission.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色下批量新增用户入参
 */
@Data
public class InputUserRoleDto extends InputParentDto {
	private static final long serialVersionUID = 1809383183256138616L;
	private Long roleId;
	private List<UserListDto> userlist;
}
