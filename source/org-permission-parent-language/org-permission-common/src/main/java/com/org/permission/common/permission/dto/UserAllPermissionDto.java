package com.org.permission.common.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UserAllPermissionDto implements Serializable {
    private static final long serialVersionUID = -7059783429236204689L;

    private List<UserPermission> allFunc;
    private List<UserPermission> func;
//    private List<UserPermission> menu;
    private List<UserOrgPermissionDto> org;
    private List<UserDataPermissionDto> data;

}
