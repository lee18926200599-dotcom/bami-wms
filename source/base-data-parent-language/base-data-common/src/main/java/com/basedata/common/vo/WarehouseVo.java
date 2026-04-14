package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarehouseVo {

    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    @ApiModelProperty(value = "仓库code")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

}
