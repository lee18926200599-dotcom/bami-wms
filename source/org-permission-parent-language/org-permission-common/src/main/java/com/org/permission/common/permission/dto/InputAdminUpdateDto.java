package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(description = "编辑管理员入参")
public class InputAdminUpdateDto implements Serializable {

    private static final long serialVersionUID = -1256257307206399380L;
    @ApiModelProperty(value = "操作人：登录用户id")
    private Long loginUserId;
    @ApiModelProperty(value = "操作人：登录用户")
    private String loginUserName;
    @ApiModelProperty(value = "管理员id")
    private Long adminId;
    @ApiModelProperty(value = "管理员帐号")
    private String adminName;
    @ApiModelProperty(value = "集团对象列表")
    private List<GroupDto> groupList;

}
