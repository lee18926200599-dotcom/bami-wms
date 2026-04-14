package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class QueryPermissionOrgInfoParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long orgId;
}
