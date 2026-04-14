package com.usercenter.server.domain.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 策略更新字段
 */
@Data
public class UpdateUserStrategyDTO implements Serializable {

    /**
     * 更新用户ID
     */
    private Long id;


    /**
     * 用户子表ID
     */
    private Long detailId;


    /**
     * 字表ID集合
     */
    private Set<Long> detailIds;


    /**
     * 集团ID
     */
    private Long groupId;

    /**
     * 校验验证码
     */
    private String code;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 当前操作用户
     */
    private Long userId;

    /**
     * 更新启用状态
     */
    private Integer enable;

    /**
     * 更新锁定状态
     */
    private Integer lock;

    /**
     * 删除
     */
    private Integer delete;

    private Date modifiedDate;
}
