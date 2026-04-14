package com.basedata.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BasePlatformStoreUpdateVo {
    @ApiModelProperty(value = "配置项id。修改传值，新增不传值")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @NotNull(message = "电商平台编码不能为空！")
    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

//    @NotNull(message = "授权店铺编码不能为空！")
    @ApiModelProperty(value = "授权店铺编码")
    private String authStoreCode;

//    @NotNull(message = "授权店铺名称不能为空！")
    @ApiModelProperty(value = "授权店铺名称")
    private String authStoreName;

//    @NotNull(message = "授权店铺所属货主ID不能为空！")
    @ApiModelProperty(value = "授权店铺所属货主ID")
    private Long belongOwnerId;

//    @NotNull(message = "授权店铺所属货主不能为空！")
    @ApiModelProperty(value = "授权店铺所属货主")
    private String belongOwnerName;

//    @ApiModelProperty(value = "仓储服务商id")
//    private Long serviceProviderId;
//
//    @ApiModelProperty(value = "仓储服务商名称")
//    private String serviceProviderName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
