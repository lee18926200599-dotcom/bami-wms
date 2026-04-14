package com.usercenter.server.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseUserInfo {
    /**
     * 用户子表主键ID
     */
    private Long id;

    /**
     * 用户主表主键id
     */
    private Long userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 做业务的组织id
     */
    private Long orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 做业务的集团
     */
    private Long groupId;

    /**
     * 注册来源 （数据字典中获取）
     */
    private Integer source;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 管理员等级    超级(0)、全局(1)、集团(2)、用户(3)
     */
    private Integer managerLevel;

    /**
     * 身份类型   员工(0)、客户(1)、供应商(2)、会员(3)、官网(4)
     */
    private Integer identityType;

    /**
     * 启用状态  非启用(0)、启用(1)、停用(2)
     */
    private Integer state;

    /**
     * 锁定状态   未锁定(0)、锁定(1)
     */
    private Integer lockFlag;

    /**
     * 用户帐号（暂时不用，冗余）
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生效时间
     */
    private Long effectiveDate;

    /**
     * 失效日期
     */
    private Long expireTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 是否删除   否(0)、是(1)
     */
    private Integer deletedFlag;

    /**
     * 是否需要重置密码    否(0)、是(1)（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
     */
    private Boolean passwordFlag;

    /**
     * 密码
     */
    private String password;

    /**
     * 首次登录（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）
     */
    private Boolean firstTimeLoginFlag;

    /**
     * 个人档案ID
     */
    private Integer archivesId;

    /**
     * 全局客户
     */
    private  Long globalCustomerId;
    /**
     * 创建人ID
     */
    private Long createdBy;
    //创建人
    private String createdName;
    //创建日期
    private Date createdDate;
    //修改人id
    private Long modifiedBy;
    //修改人
    private String modifiedName;
    //修改时间
    private Date modifiedDate;
}
