package com.basedata.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * comments:货区实体类型
 */
@Data
public class AreaInfoQuery {

    @ApiModelProperty(value = "上级行政区域id")
    private String parentIds;
    @ApiModelProperty(value = "上级行政区域id")
    private Long parentId;
    @ApiModelProperty(value = "上级行政区域id集合")
    private List<Long> parentIdList;
    @ApiModelProperty(value = "行政区域id")
    private Long areaId;
    @ApiModelProperty(value = "行政区域id集合")
    private List<Long> areaIdList;
    @ApiModelProperty(value = "行政区域名称")
    private String areaName;

}
