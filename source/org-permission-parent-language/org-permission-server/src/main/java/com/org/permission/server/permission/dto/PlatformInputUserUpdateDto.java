package com.org.permission.server.permission.dto;

import com.org.permission.common.permission.dto.UserPermissionDto;
import com.org.permission.common.permission.dto.UserRoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(description = "编辑用户入参")
public class PlatformInputUserUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // 用户入参
    @ApiModelProperty(value = "登录用户id")
    private Long LoginUserId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "集团id")
    private Long groupId;
    @ApiModelProperty(value = "用户帐号")
    private String userName;
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @ApiModelProperty(value = "用户头像")
    private String profile;
    @ApiModelProperty(value = "电子邮件")
    private String email;
    @ApiModelProperty(value = "身份类型-员工:EMPLOYEE,客户:CUSTOMER,供应商:SUPPLIER")
    private Integer identityType;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "修改人")
    private Long updateUser;
    @ApiModelProperty(value = "用户档案id（员工、客户、供应商等）")
    private Long archivesId;

    // 角色列表
    @ApiModelProperty(value = "角色id")
    private List<UserRoleDto> roleList;
    // 用户特殊权限
    @ApiModelProperty(value = "功能权限id")
    private List<UserPermissionDto> funcList;
    @ApiModelProperty(value = "组织权限id")
    private List<UserPermissionDto> orgList;
    @ApiModelProperty(value = "数据权限id")
    private List<UserPermissionDto> dataList;

}
