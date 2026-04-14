package com.basedata.server.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseStoreMultiQuery {


    @ApiModelProperty(value = "货主ID")
    private List<Long> ownerIds;

    @ApiModelProperty(value = "电商平台编码")
    private List<String> platformCodes;

    @ApiModelProperty(value = "店铺编码")
    private List<String> storeCodes;
}
