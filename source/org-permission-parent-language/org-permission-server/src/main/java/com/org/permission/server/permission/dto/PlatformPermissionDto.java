package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限返回对象
 */
@Data
@ApiModel(description = "权限返回对象")
public class PlatformPermissionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long authTime;
    private Long author;
    private String permissionType;
    private Long permissionId;
    private Integer optionPermission;
    private Long groupId;

}
