package com.usercenter.common.dto;

import com.usercenter.common.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class BaseUserCommonDto extends BaseQuery implements Serializable {
    @ApiModelProperty(notes="用户id")
    private Long id;

    @ApiModelProperty(notes="用户认证信息id")
    private Long userAuthId;

    @ApiModelProperty(notes="用户编码")
    private String userCode;

    @ApiModelProperty(notes="头像")
    private String headImg;

    @ApiModelProperty(notes="所属组织")
    private Long orgId;

    @ApiModelProperty(notes="用户所属集团")
    private Long groupId;

    @ApiModelProperty(notes="注册来源 （数据字典中获取）")
    private String source;

    @ApiModelProperty(notes="真实姓名")
    private String realName;

    @ApiModelProperty(notes="管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel;

    @ApiModelProperty(notes="身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType;

    @ApiModelProperty(notes="启用状态  非启用(0)、启用(1)、停用(2)")
    private Integer state;

    @ApiModelProperty(notes="锁定状态   未锁定(0)、锁定(1)")
    private Integer lockFlag;

    @ApiModelProperty(notes="用户密码")
    private String password;

    @ApiModelProperty(notes="用户帐号")
    private String userName;

    @ApiModelProperty(notes="昵称")
    private String nickname;

    @ApiModelProperty(notes="手机号码")
    private String phone;

    @ApiModelProperty(notes="电子邮件")
    private String email;

    @ApiModelProperty(notes="生效时间")
    private Date effectiveDate;

    @ApiModelProperty(notes="失效日期")
    private Date expireDate;

    @ApiModelProperty(notes="备注信息")
    private String remark;

    @ApiModelProperty(notes="是否需要重置密码    否(0)、是(1)")
    private Integer passwordFlag;

    @ApiModelProperty(notes="首次登录")
    private Integer firstTimeLoginFlag;

    @ApiModelProperty(notes="个人档案ID")
    private Long archivesId;

    @ApiModelProperty(notes="全局客户")
    private Long globalCustomerId;

    @ApiModelProperty(notes="业务系统")
    private Integer businessSystem;

    @ApiModelProperty(notes="来源类型")
    private Integer sourceType;

    private Integer deletedFlag;
}
