package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 主键操作请求参数
 */
@ApiModel(description = "主键操作请求参数", value = "KeyOperateParam")
@Data
public class KeyOperateParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String name;
}
