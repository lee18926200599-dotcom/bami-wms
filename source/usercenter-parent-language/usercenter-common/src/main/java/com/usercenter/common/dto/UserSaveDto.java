package com.usercenter.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class UserSaveDto extends BaseUserCommonDto {
    @ApiModelProperty(notes="属性（暂用于是否全局标识）")
    private Integer globalFlag;
    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;
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
