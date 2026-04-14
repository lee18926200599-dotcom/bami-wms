package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BasePlatformPrintTemplateReqDTO {

    @ApiModelProperty(value = "系统承运商编码（平台承运商、系统承运商，两个入参任意传一个）")
    private String logisticsCode;

    @ApiModelProperty(value = "平台承运商编码（平台承运商、系统承运商，两个入参任意传一个）")
    private String platformLogisticsCode;

    @NotBlank(message = "参数校验失败：电商平台编码不能为空！")
    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "面单模板类型（0标准、1自定义）")
    private Integer type;
}
