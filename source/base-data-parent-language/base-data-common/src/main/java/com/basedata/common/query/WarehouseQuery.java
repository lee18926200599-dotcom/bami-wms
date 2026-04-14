package com.basedata.common.query;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseQuery extends BaseQuery {

    @ApiModelProperty(value = "仓库编号")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "0-已创建，1-启用，2-停用")
    private Integer state;

    @ApiModelProperty(value = "单位id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "所属单位")
    private Long groupId;
    private Long warehouseId;
    private List<Long> warehouseIdList;
    @ApiModelProperty(value = "仓储服务商ID集合")
    private List<Long> serviceProviderIds;
}
