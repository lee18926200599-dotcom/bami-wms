package com.basedata.server.query;

import com.basedata.common.vo.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePlatformNetsideQueryVo extends BaseQuery {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;


    @ApiModelProperty(value = "网点ID/编码（电商平台）")
    private String netsiteCode;

    @ApiModelProperty(value = "网点名称（电商平台）")
    private String netsiteName;
}