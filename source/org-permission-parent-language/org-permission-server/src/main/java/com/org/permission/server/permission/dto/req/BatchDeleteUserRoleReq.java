package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关联表管理
 */
@Data
@ApiModel("批量删除角色用户关联信息")
public class BatchDeleteUserRoleReq implements Serializable {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;

    /**
     * 集团id
     */
    @ApiModelProperty("集团id")
    private Long groupId;
    /**
     * 授权人id
     */
    @ApiModelProperty("授权人id")
    private Long authUserId;


    @ApiModelProperty("用户角色关联")
    private List<Long> userIdList;

}

