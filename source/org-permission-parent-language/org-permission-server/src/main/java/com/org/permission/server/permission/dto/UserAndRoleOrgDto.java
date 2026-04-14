package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的组织权限
 */
@Data
public class UserAndRoleOrgDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orgId;
    private Long groupId;
}
