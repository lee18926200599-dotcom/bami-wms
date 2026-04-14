package com.usercenter.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("保存请求")
@Data
public class UserStaffMapDto implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 人员id
     */
    @ApiModelProperty(value="人员id")
    private Long staffId;

}