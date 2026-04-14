package com.basedata.server.dto.integration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class GetPlatformNetsideAddressReqDto {
    @NotBlank(message = "电商平台编码不能为空！")
    @ApiModelProperty("电商平台编码")
    private String platformCode;

    @NotBlank(message = "系统承运商编码 不能为空！")
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @NotNull(message = "当前页不能为空")
    @ApiModelProperty(value = "当前页")
    private int pageNum;

    @NotNull(message = "页最大行数不能为空")
    @ApiModelProperty(value = "页最大行数")
    private int pageSize;

}
