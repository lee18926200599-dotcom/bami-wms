package com.org.permission.common.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 入参 <br>
 */
@Data
public class UserPermissionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date authTime;
    private Long author;
    private Long permissionId;
    private boolean query;
    private boolean edit;
    private Integer optionPermission;
    private Long groupId;

}
