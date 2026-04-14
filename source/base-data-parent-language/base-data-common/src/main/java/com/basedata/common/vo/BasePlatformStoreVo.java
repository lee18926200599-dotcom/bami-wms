package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePlatformStoreVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺编码")
    private String storeCode;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "货主编码")
    private String ownerCode;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

}
