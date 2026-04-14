package com.org.permission.server.permission.service.command.dto;

import lombok.Data;

@Data
public class PermissionDto {
    /**
     * 操作人：登录用户id
     */
    private Long loginUserId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 集团id
     */
    private Long groupId;
    /**
     * 策略
     * 1：启用用户
     * 2：停用用户
     * 3：启用管理员
     * 4：停用管理员
     */
    private Integer strategy;

}
