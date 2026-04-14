package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表管理
 */
@Data
@ApiModel("角色用户关联信息")
public class SaveUserRoleReq implements Serializable {

    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("角色id， 批量插入可以不填")
    private Long roleId;
    @ApiModelProperty("所属集团id， 当前的集团 批量插入可以不填")
    private Long groupId;
    @ApiModelProperty("所属组织id，角色的所属组织")
    private Long orgId;
    @ApiModelProperty("关联生效时间")
    private Date effectiveTime;
    @ApiModelProperty("关联失效时间")
    private Date expireTime;
    @ApiModelProperty("授权人，批量插入可以不填")
    private Long authorUser;
    @ApiModelProperty("授权时间，可不填")
    private Date authorTime;
}

