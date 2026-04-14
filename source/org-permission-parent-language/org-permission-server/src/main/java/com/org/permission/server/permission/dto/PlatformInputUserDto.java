package com.org.permission.server.permission.dto;

import com.org.permission.common.permission.dto.UserPermissionDto;
import com.org.permission.common.permission.dto.UserRoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(description = "新增用户入参")
public class PlatformInputUserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // 用户入参
    @ApiModelProperty(value = "用户编码")
    private String userNumber;
    @ApiModelProperty(value = "创建人")
    private Long createUserId;
    @ApiModelProperty(value = "管理等级-超级：SUPER，全局：GLOBAL，集团：GROUP，用户：USER")
    private Integer managerLevel;
    @ApiModelProperty(value = "所属组织")
    private Long orgId;
    @ApiModelProperty(value = "所属集团id")
    private Long groupId;
    @ApiModelProperty(value = "")
    private Integer source;
    @ApiModelProperty(value = "电子邮件")
    private String email;
    @ApiModelProperty(value = "用户帐号")
    private String userName;
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @ApiModelProperty(value = "用户头像")
    private String profile;
    @ApiModelProperty(value = "身份类型-员工:EMPLOYEE,客户:CUSTOMER,供应商:SUPPLIER")
    private Integer identityType;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "用户档案id（员工、客户、供应商等）")
    private Long archivesId;
    // 角色列表
    @ApiModelProperty(value = "角色对象(角色id，生效时间，失效时间)")
    private List<UserRoleDto> roleList;
    // 用户特殊权限
    @ApiModelProperty(value = "功能权限id")
    private List<Long> funcList;
    @ApiModelProperty(value = "组织权限对象")
    private List<PlatformOrgDto> orgList;
    @ApiModelProperty(value = "数据权限")
    private List<UserPermissionDto> dataList;

}
