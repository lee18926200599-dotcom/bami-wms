package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有入参对象都会继承这个类
 */
@Data
@ApiModel(description = "编辑数据权限入参")
public class InputDataPermission implements Serializable {

    private static final long serialVersionUID = 5213413978537572684L;
    @ApiModelProperty(value = "列表返回的id")
    private Long id;
    @ApiModelProperty(value = "查询权限")
    private boolean query;
    @ApiModelProperty(value = "维护权限")
    private boolean edit;

}
