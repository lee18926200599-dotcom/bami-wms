package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QueryStoreInfoDto {

    @ApiModelProperty("仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty("货主id")
    private Long ownerId;
    @NotNull(
            message = "仓库ID不能为空"
    )
    @ApiModelProperty("仓库ID")
    private Long warehouseId;
    @NotEmpty(
            message = "平台编码不能为空"
    )
    @ApiModelProperty("平台编码")
    private String extPlatformCode;

    @ApiModelProperty("店铺编码")
    private List<String> storeCodeList;

    @ApiModelProperty("承运商编码List")
    private List<String> deliveryCodeList;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("主店铺标识")
    private String mainFlag;

}
