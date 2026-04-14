package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色下批量新增用户入参
 */
@Data
public class UserListDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long effectiveTime;
    private Long authorTime;
    private Long author;
    private Date expireTime;
    private Long userId;

}
