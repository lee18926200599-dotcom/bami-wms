package com.usercenter.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@TableName(value = "base_user")
public class BaseUser {
    //ID
    @TableField(value = "id")
    private Long id;
    //用户编码
    @TableField(value = "user_code")
    private String userCode;
    //头像
    @TableField(value = "head_img")
    private String headImg;
    //所属组织
    @TableField(value = "org_id")
    private Long orgId;
    //用户所属集团
    @TableField(value = "group_id")
    private Long groupId;
    //注册来源 （数据字典中获取）
    @TableField(value = "source")
    private Integer source;
    //真实姓名
    @TableField(value = "real_name")
    private String realName;
    //管理员等级 超级、全局、集团、用户
    @TableField(value = "manager_level")
    private Integer managerLevel;
    //身份类型 员工、客户、供应商
    @TableField(value = "identity_type")
    private Integer identityType;
    //状态
    @TableField(value = "state")
    private Integer state;
    //锁定状态
    @TableField(value = "lock_flag")
    private Integer lockFlag;
    //用户密码
    @TableField(value = "password")
    private String password;
    //用户帐号
    @TableField(value = "user_name")
    private String userName;
    //昵称
    @TableField(value = "nickname")
    private String nickname;
    //手机号码
    @TableField(value = "phone")
    private String phone;
    //电子邮件
    @TableField(value = "email")
    private String email;
    //生效时间
    @TableField(value = "effective_date")
    private Date effectiveDate;
    //失效日期
    @TableField(value = "expire_time")
    private Date expireTime;
    //备注信息
    @TableField(value = "remark")
    private String remark;
    //是否删除 否(0)、是(1)
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //是否需要重置密码 否(0)、是(1)
    @TableField(value = "password_flag")
    private Integer passwordFlag;
    //首次登录
    @TableField(value = "first_time_login_flag")
    private Integer firstTimeLoginFlag;
    //个人档案ID
    @TableField(value = "archives_id")
    private Long archivesId;
    //全局客户
    @TableField(value = "global_customer_id")
    private Long globalCustomerId;
    //最后一次登录时间
    @TableField(value = "last_login_time")
    private Date lastLoginTime;
    //创建人id
    @TableField(value = "created_by")
    private Long createdBy;
    //创建人
    @TableField(value = "created_name")
    private String createdName;
    //创建日期
    @TableField(value = "created_date")
    private Date createdDate;
    //修改人id
    @TableField(value = "modified_by")
    private Long modifiedBy;
    //修改人
    @TableField(value = "modified_name")
    private String modifiedName;
    //修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;

    List<BaseUserDetail> detailList;
}

