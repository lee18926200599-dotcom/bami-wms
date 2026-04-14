package com.usercenter.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * comments:用户子表实体类型
 */
@Data
public class BaseUserDetailDto {
    @ApiModelProperty(value = "用户子表主键ID")
    private Long id;
    @ApiModelProperty(value = "用户主表主键id")
    private Long userId;
    @ApiModelProperty(value = "用户编码")
    private String userCode;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "做业务的组织id")
    private Long orgId;
    @ApiModelProperty(value = "做业务的集团")
    private Long groupId;
    @ApiModelProperty(value = "注册来源 （数据字典中获取）")
    private Integer source;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "管理员等级 超级、全局、集团、用户")
    private Integer managerLevel;
    @ApiModelProperty(value = "身份类型 员工、客户、供应商")
    private Integer identityType;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "锁定状态 未锁定(0)、锁定(1)")
    private Integer lockFlag;
    @ApiModelProperty(value = "用户密码（暂时不用，冗余）")
    private String password;
    @ApiModelProperty(value = "用户帐号（暂时不用，冗余）")
    private String userName;
    @ApiModelProperty(value = "联系邮箱")
    private String email;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "生效时间（未使用，冗余）")
    private Date effectiveDate;
    @ApiModelProperty(value = "失效日期（未使用，冗余）")
    private Date expireTime;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "是否删除 否(0)、是(1)")
    private Integer deletedFlag;
    @ApiModelProperty(value = "是否需要重置密码 否(0)、是(1)（暂时不用，冗余）")
    private Integer passwordFlag;
    @ApiModelProperty(value = "首次登录（暂时不用，冗余）")
    private Integer firstTimeLoginFlag;
    @ApiModelProperty(value = "个人档案ID")
    private Integer archivesId;
    @ApiModelProperty(value = "全局客户")
    private Long globalCustomerId;
    @ApiModelProperty(value = "用户体系")
    private Integer userSystem;
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;
}
