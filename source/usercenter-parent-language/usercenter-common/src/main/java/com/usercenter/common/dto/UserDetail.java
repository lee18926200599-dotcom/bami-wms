package com.usercenter.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 子表信息
 */
@Data
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 集团id
     */
    private Long groupId;

    /**
     * 管理员等级 (超级0、全局1、集团2、用户3）
     */
    private Integer managerLevel;

    /**
     * 身份类型 (员工0、客户1、供应商2)
     */
    private Integer identityType;
}
