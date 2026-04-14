package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * base_userVo类  用户基本信息表管理
 */

@ApiModel(description = "用户基本信息表", value = "用户基本信息表")
@Data
public class BaseUserVo extends QueryPager implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id; //ID
    @ApiModelProperty(value = "用户编码")
    private String userNumber; //用户编码
    @ApiModelProperty(value = "头像")
    private String profile; //头像
    @ApiModelProperty(value = "所属组织")
    private Integer orgId; //所属组织
    @ApiModelProperty(value = "用户所属集团")
    private Integer groupId; //用户所属集团
    @ApiModelProperty(value = "注册来源 （数据字典中获取）")
    private Integer source; //注册来源 （数据字典中获取）
    @ApiModelProperty(value = "真实姓名")
    private String realName; //真实姓名
    @ApiModelProperty(value = "管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel; //管理员等级    超级(0)、全局(1)、集团(2)、用户(3)
    @ApiModelProperty(value = "身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType; //身份类型   员工(0)、客户(1)、供应商(2)
    @ApiModelProperty(value = "启用状态  非启用(0)、启用(1)、停用(2)")
    private Integer enable; //启用状态  非启用(0)、启用(1)、停用(2)
    @ApiModelProperty(value = "锁定状态   未锁定(0)、锁定(1)")
    private Integer lock; //锁定状态   未锁定(0)、锁定(1)
    @ApiModelProperty(value = "用户密码")
    private String password; //用户密码
    @ApiModelProperty(value = "用户帐号")
    private String userName; //用户帐号
    @ApiModelProperty(value = "昵称")
    private String nickname; //昵称
    @ApiModelProperty(value = "手机号码")
    private String phone; //手机号码
    @ApiModelProperty(value = "电子邮件")
    private String email; //电子邮件
    @ApiModelProperty(value = "生效时间")
    private Long effectiveDate; //生效时间
    @ApiModelProperty(value = "失效日期")
    private Long expireTime; //失效日期
    @ApiModelProperty(value = "创建人")
    private Integer createUser; //创建人
    @ApiModelProperty(value = "创建时间")
    private Long createTime; //创建时间
    @ApiModelProperty(value = "更新人")
    private Long updateUser; //更新人
    @ApiModelProperty(value = "更新时间")
    private Long updateTime; //更新时间
    @ApiModelProperty(value = "备注信息")
    private String remark; //备注信息
    @ApiModelProperty(value = "是否删除   否(0)、是(1)")
    private Integer deleted; //是否删除   否(0)、是(1)
    @ApiModelProperty(value = "是否需要重置密码    否(0)、是(1)")
    private Integer passwordFlag; //是否需要重置密码    否(0)、是(1)
    @ApiModelProperty(value = "首次登录")
    private Integer firstTimeLoginFlag; //首次登录
    @ApiModelProperty(value = "个人档案ID")
    private Integer archivesId; //个人档案ID
    @ApiModelProperty(value = "全局客户")
    private Integer globalCustomer;
    @ApiModelProperty(value = "属性（暂用于是否全局标识）")
    private Integer attribute;
}

