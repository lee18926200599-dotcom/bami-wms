package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseNetsideDTO {
    @ApiModelProperty(value = "网点ID/编码（电商平台）")
    private String netsiteCode;

    @ApiModelProperty(value = "网点名称（电商平台）")
    private String netsiteName;
}
