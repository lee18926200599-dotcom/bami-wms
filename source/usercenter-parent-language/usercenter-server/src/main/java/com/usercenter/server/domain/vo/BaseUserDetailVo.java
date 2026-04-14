package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * base_user_detailVo类  用户子表管理
 */

@ApiModel(description = "用户子表", value = "用户子表")
public class BaseUserDetailVo extends QueryPager implements Serializable {
    @ApiModelProperty(value = "用户子表主键ID")
    private Integer id; //用户子表主键ID
    @ApiModelProperty(value = "用户主表主键id")
    private Integer userId; //用户主表主键id
    @ApiModelProperty(value = "用户编码")
    private String userNumber; //用户编码
    @ApiModelProperty(value = "头像")
    private String profile; //头像
    @ApiModelProperty(value = "做业务的组织id")
    private Integer orgId; //做业务的组织id
    @ApiModelProperty(value = "做业务的集团")
    private Integer groupId; //做业务的集团
    @ApiModelProperty(value = "注册来源 （数据字典中获取）")
    private Integer source; //注册来源 （数据字典中获取）
    @ApiModelProperty(value = "真实姓名")
    private String realName; //真实姓名
    @ApiModelProperty(value = "管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel; //管理员等级    超级(0)、全局(1)、集团(2)、用户(3)
    @ApiModelProperty(value = "身份类型   员工(0)、客户(1)、供应商(2)、会员(3)、官网(4)")
    private Integer identityType; //身份类型   员工(0)、客户(1)、供应商(2)、会员(3)、官网(4)
    @ApiModelProperty(value = "启用状态  非启用(0)、启用(1)、停用(2)")
    private Integer enable; //启用状态  非启用(0)、启用(1)、停用(2)
    @ApiModelProperty(value = "锁定状态   未锁定(0)、锁定(1)")
    private Integer lock; //锁定状态   未锁定(0)、锁定(1)
    @ApiModelProperty(value = "用户密码（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String password; //用户密码（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
    @ApiModelProperty(value = "用户帐号（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String userName; //用户帐号（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail; //联系邮箱
    @ApiModelProperty(value = "昵称")
    private String nickname; //昵称
    @ApiModelProperty(value = "生效时间（未使用，冗余）")
    private Long effectiveDate; //生效时间（未使用，冗余）
    @ApiModelProperty(value = "失效日期（未使用，冗余）")
    private Long expireTime; //失效日期（未使用，冗余）
    @ApiModelProperty(value = "备注信息")
    private String remark; //备注信息
    @ApiModelProperty(value = "是否删除   否(0)、是(1)")
    private Integer deleted; //是否删除   否(0)、是(1)
    @ApiModelProperty(value = "是否需要重置密码    否(0)、是(1)（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Integer passwordFlag; //是否需要重置密码    否(0)、是(1)（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
    @ApiModelProperty(value = "首次登录（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Integer firstTimeLoginFlag; //首次登录（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
    @ApiModelProperty(value = "个人档案ID")
    private Integer archivesId; //个人档案ID
    @ApiModelProperty(value = "全局客户")
    private Integer globalCustomer; //全局客户
    @ApiModelProperty(value = "用户体系（ERP相关：erp,  电商体系：ec）")
    private String userSystem; //用户体系（ERP相关：erp,  电商体系：ec）
    @ApiModelProperty(value = "扩展字段 暂时未定义")
    private String attribute; //扩展字段 暂时未定义

}

