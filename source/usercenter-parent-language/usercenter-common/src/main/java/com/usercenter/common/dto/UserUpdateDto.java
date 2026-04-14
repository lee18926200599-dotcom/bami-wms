package com.usercenter.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新dto
 */
@ApiModel("用户更新dto")
@Data
public class UserUpdateDto implements Serializable {

    private static final long serialVersionUID = 8779732839095337268L;

    @ApiModelProperty(notes = "用户id")
    private Long id;

    @ApiModelProperty(notes = "手机号码")
    private String phone;

    @ApiModelProperty(notes = "所属组织")
    private Long orgId;

    @ApiModelProperty(notes = "用户账号")
    private String userName;

    @ApiModelProperty(notes = "真实姓名")
    private String realName;

    @ApiModelProperty(notes = "身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType;

    @ApiModelProperty(notes = "备注信息")
    private String remark;

    @ApiModelProperty(notes = "个人档案ID")
    private Integer archivesId;

    @ApiModelProperty(notes = "联系邮箱")
    private String contactEmail;

    @ApiModelProperty(notes = "修改人")
    private Long updateUser;

    @ApiModelProperty(notes = "是否删除   否(0)、是(1)")
    private Integer deletedFlag;

    @ApiModelProperty(notes = "启用状态  非启用(0)、启用(1)、停用(2), 请使用枚举BaseUserEnableEnum")
    private Integer state;

    @ApiModelProperty(notes = "邮箱")
    private String email;

}
