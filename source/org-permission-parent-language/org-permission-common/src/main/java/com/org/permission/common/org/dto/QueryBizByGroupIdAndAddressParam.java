package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class QueryBizByGroupIdAndAddressParam {
    @ApiModelProperty("集团id")
    private Long groupId;
    @ApiModelProperty("开始经度")
    private Double startLongitude;
    @ApiModelProperty("结束经度")
    private Double endLongitude;
    @ApiModelProperty("开始纬度")
    private Double startLatitude;
    @ApiModelProperty("结束经度")
    private Double endLatitude;
}
