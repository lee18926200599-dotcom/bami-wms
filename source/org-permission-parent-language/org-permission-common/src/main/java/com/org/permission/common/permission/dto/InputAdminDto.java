package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(description = "新增管理员入参")
public class InputAdminDto implements Serializable {

    private static final long serialVersionUID = 5411353812700769895L;
    @ApiModelProperty(value = "管理员的用户id")
    private Long adminId;
    @ApiModelProperty(value = "管理员的用户账号")
    private String adminName;
    @ApiModelProperty(value = "操作人：登录的用户id")
    private Long loginUserId;
    @ApiModelProperty(value = "操作人：登录用户")
    private String loginUserName;
    @ApiModelProperty(value = "集团对象列表")
    private List<GroupDto> groupList;
}
