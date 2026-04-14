package com.org.permission.common.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetUserMenuPermissionsDto implements Serializable {

    private static final long serialVersionUID = 3988929270896550028L;
    /**
     * 集团id
     */
    private Integer groupId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 在该集团的权限
     */
    private List<UserPermission> permissions;
    
}
