package com.usercenter.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * comments:用户基本信息表实体类型
 */
@Data
public class BaseUserDto extends BaseUserCommonDto{

    @ApiModelProperty(value = "是否删除 否(0)、是(1)")
    private Integer deletedFlag;
    @ApiModelProperty(value = "最后一次登录时间")
    private Date lastLoginTime;
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;
}
