package com.basedata.server.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseStoreQuery{

    @ApiModelProperty(value = "店铺编码")
    private String storeCode;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "是否主店铺")
    private Boolean mainStoreFlag;
}
