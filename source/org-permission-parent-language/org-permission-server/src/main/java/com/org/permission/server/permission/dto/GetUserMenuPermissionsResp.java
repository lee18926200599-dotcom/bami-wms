package com.org.permission.server.permission.dto;

import com.org.permission.common.permission.dto.UserPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class GetUserMenuPermissionsResp implements Serializable {

    private static final long serialVersionUID = 3762546344965469553L;
    /**
     * 集团id
     */
    private Long groupId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 在该集团的权限
     */
    private List<UserPermission> permissions;
}
