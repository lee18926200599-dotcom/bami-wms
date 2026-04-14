package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class QueryBizByGroupIdAndUserIdParam {
    @ApiModelProperty("集团id")
    private Long groupId;
    @ApiModelProperty("当前用户id")
    private Long userId;
}
