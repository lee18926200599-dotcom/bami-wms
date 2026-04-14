package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 启停操作请求参数
 */
@ApiModel(description = "启停操作请求参数", value = "EnableOperateParam")
@Data
public class EnableOperateParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "用户")
    private String userName;
    @ApiModelProperty(value = "启停(1启用；2停用)")
    private Integer state;

    /**
     * 操作时间，底层默认取系统时间
     */
    private Date enableTime;

    public EnableOperateParam() {
    }

    /**
     * 是否需要重复验证
     *
     * @return <code>true</code>需要;<code> false</code>不需;
     */
    public boolean needVerify() {
        return state != null && state == 1;
    }
}
