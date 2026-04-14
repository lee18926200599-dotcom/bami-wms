package com.usercenter.server.domain.condition;

import lombok.Data;

import java.util.Set;

@Data
public class UserDetailCondition extends ExtendsCondition{
    /**
     * 用户明细id集合
     */
    private Set<Long> ids;

    /**
     * 用户明细id
     */
    private Long id;

    /**
     * 用户主表id
     */
    private Long userId;

    /**
     * 用户主表id
     */
    private Long groupId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 注册来源
     */
    private Integer source;

    /**
     * 启用
     */
    private Integer state;

    /**
     * 管理级别
     */
    private Integer managerLevel;


    /**
     * 是否锁定
     */
    private Integer lockFlag;

    /**
     * 删除标志
     */
    private Integer deletedFlag;

    /**
     * 组织列表
     */
    private Set<Long> orgIds;

    /**
     * base_user.id 集合
     */
    private Set<Long> baseUserIds;
}
