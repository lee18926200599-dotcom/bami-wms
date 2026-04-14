package com.org.permission.server.org.dto.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆请求参数
 */
@Data
public class UserLoginReqParam implements Serializable {
	private static final long serialVersionUID = -6899713063838333582L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 集团ID
	 */
	private Long groupId;
}
