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
@ApiModel("批量角色用户关联信息")
public class BatchSaveUserRoleReq implements Serializable {

    private static final long serialVersionUID = -2008394045483644102L;
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

    /**
     * 授权人
     */
    @ApiModelProperty("授权人")
    private String authUserName;

    @ApiModelProperty("用户角色关联")
    private List<SaveUserRoleReq> userRoleList;

}

