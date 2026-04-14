package com.usercenter.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 历史密码
 */
@Data
@TableName(value = "base_user_password_history")
public class BaseUserPasswordHistory {
    @TableField(value = "id")
    private Integer id;
    //用户ID
    @TableField(value = "user_id")
    private Long userId;
    //密码
    @TableField(value = "password")
    private String password;
    //创建时间
    @TableField(value = "create_date")
    private Date createDate;

}

