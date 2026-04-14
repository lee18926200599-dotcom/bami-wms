package com.org.permission.common.permission.param;



import com.usercenter.common.dto.FplUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  查询用户权限相关参数
 */
@Data
public class PermissionUserParam implements Serializable {
    private static final long serialVersionUID = 1058221714880096122L;
    @ApiModelProperty("用户子表id")
    private Long userId;
    @ApiModelProperty("集团ID")
    private Long groupId;
    @ApiModelProperty("域ID")
    private Long domainId;
    @ApiModelProperty("来源 0=PC 1=APP")
    private String source;
    @ApiModelProperty("用户")
    private FplUser user;
}
