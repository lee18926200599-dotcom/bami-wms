package com.usercenter.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户认证信息
 */
@ApiModel(value = "用户信息")
@Data
public class UserAuthDto implements Serializable {

    private static final long serialVersionUID = -7775446013457257625L;

    @ApiModelProperty(notes="用户认证信息id")
    private Long userAuthId;

    @ApiModelProperty(notes="用户编码")
    private String userNumber;

    @ApiModelProperty(notes="注册来源 （数据字典中获取）")
    private Integer source;

    @ApiModelProperty(notes="管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel;

    @ApiModelProperty(notes="启用状态  非启用(0)、启用(1)、停用(2)")
    private Integer enable;

    @ApiModelProperty(notes="锁定状态   未锁定(0)、锁定(1)")
    private Boolean lock;

    @ApiModelProperty(notes="用户帐号（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String userName;

    @ApiModelProperty(notes="手机号码")
    private String phone;

    @ApiModelProperty(notes="生效时间（未使用，冗余）")
    private Long effectiveDate;

    @ApiModelProperty(notes="失效日期（未使用，冗余）")
    private Long expireDate;

    @ApiModelProperty(notes="备注信息")
    private String remark;

    @ApiModelProperty(notes="是否删除   否(0)、是(1)")
    private Integer deleted_flag;

    @ApiModelProperty(notes="是否需要重置密码    否(0)、是(1)（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Boolean passwordFlag;

    @ApiModelProperty(notes="首次登录（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Boolean firstTimeLoginFlag;

    @ApiModelProperty(notes="全局管理员标识")
    private Integer attribute;

}
