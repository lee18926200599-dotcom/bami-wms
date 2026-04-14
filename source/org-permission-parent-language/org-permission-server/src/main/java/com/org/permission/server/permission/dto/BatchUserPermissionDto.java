package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 入参
 */
@Data
public class BatchUserPermissionDto implements Serializable {

    private Date authorTime;
    private Long authorUser;
    private Long permissionId;
    private Long userId;
}
