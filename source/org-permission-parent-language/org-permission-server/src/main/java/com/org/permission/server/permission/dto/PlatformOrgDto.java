package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 权限返回对象
 */
@Data
@ApiModel(description = "平台管理员组织权限对象")
public class PlatformOrgDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long groupId;
    private List<Long> orgIds;

}
