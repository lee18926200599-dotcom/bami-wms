package com.basedata.server.vo.integration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SyncFaceOrderTemplateReqVo {
    @NotBlank(message = "电商平台编码不能为空！")
    @ApiModelProperty("电商平台编码")
    private String platformCode;

    @NotBlank(message = "系统承运商编码不能为空！")
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @NotNull(message = "货主ID不能为空！")
    @ApiModelProperty("货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "授权编码/客户id(店铺id)", hidden = true)
    private String customerId;

    @ApiModelProperty(value = "商家编码/ID", hidden = true)
    private String vendorId;

}
