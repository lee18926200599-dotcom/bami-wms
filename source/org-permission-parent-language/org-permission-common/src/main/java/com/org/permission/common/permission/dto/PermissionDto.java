package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限返回对象
 */
@Data
@ApiModel(description = "权限返回对象")
public class PermissionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date authTime;
    private Long author;
    private String permissionType;
    private Long permissionId;
    private Integer optionPermission;

}
