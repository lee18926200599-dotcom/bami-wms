package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class AdminGroupDto implements Serializable {

    private static final long serialVersionUID = -3335462392877298861L;
    @ApiModelProperty("组织ID")
    private Long id;

    @ApiModelProperty("集团ID")
    private Long groupId;

    @ApiModelProperty("上级组织ID")
    private Long parentId;

    @ApiModelProperty("组织类别（2集团;3业务单元;4部门）")
    private Integer orgType;

    @ApiModelProperty("组织编码")
    private String orgCode;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("业务类型，该属性只有集团才有")
    private String businessType;
    @ApiModelProperty("管理生效时间")
    private Date effectiveTime;
    @ApiModelProperty("管理失效时间")
    private Date expireTime;
    @ApiModelProperty("操作人")
    private Long userId;

}
