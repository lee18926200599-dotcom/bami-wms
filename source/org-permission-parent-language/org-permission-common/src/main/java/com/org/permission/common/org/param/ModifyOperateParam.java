package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 启停操作请求参数
 */
@ApiModel(description = "启停操作请求参数", value = "ModifyOperateParam")
@Data
public class ModifyOperateParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "修改状态(1启用；2停用；)")
    private Integer state;

    @ApiModelProperty(value = "任职结束时间")
    private Date endDate;

    /**
     * 操作时间，底层默认取系统时间
     */
    private Date opTime;
    @ApiModelProperty(value = "1=删除")
    private Integer deletedFlag = 0;

}
