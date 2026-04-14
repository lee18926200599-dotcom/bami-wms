package com.usercenter.server.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户更新实体
 */
@Data
public class UpdateUserDTO {

    @ApiModelProperty(notes="用户认证信息id")
    private Long userAuthId;

    @ApiModelProperty(notes="用户id")
    private Long id;

    @ApiModelProperty(notes="业务系统")
    private Integer businessSystem;

    @ApiModelProperty(notes="电子邮件")
    private String email;

    @ApiModelProperty(notes="用户帐号")
    private String userName;

    @ApiModelProperty(notes="身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType;

    @ApiModelProperty(notes="手机号码")
    private String phone;

    @ApiModelProperty(notes="管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel;

    @ApiModelProperty(notes="注册来源 （数据字典中获取）")
    private String source;

    @ApiModelProperty(notes="用户所属集团")
    private Long groupId;
}
