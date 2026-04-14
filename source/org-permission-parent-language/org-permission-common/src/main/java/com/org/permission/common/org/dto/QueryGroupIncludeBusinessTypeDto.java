package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class QueryGroupIncludeBusinessTypeDto {
    @ApiModelProperty("运输商日入驻量")
    private Integer transportDayCount;
    @ApiModelProperty("运输商周入驻量")
    private Integer transportWeekCount;
    @ApiModelProperty("运输商月入驻量")
    private Integer transportMonthCount;
    @ApiModelProperty("运输商入驻总量")
    private Integer totalTransportCount;
    //仓储商
    @ApiModelProperty("仓储商日入驻量")
    private Integer warehouseDayCount;
    @ApiModelProperty("仓储商周入驻量")
    private Integer warehouseWeekCount;
    @ApiModelProperty("仓储商月入驻量")
    private Integer warehouseMonthCount;
    @ApiModelProperty("仓储上入驻总量")
    private Integer totalWarehouseCount;

    //品牌商或渠道商
    @ApiModelProperty("品牌或渠道商日入驻量")
    private Integer brandChannelDayCount;
    @ApiModelProperty("品牌或渠道商周入驻量")
    private Integer brandChannelWeekCount;
    @ApiModelProperty("品牌或渠道商月入驻量")
    private Integer brandChannelMonthCount;
    @ApiModelProperty("品牌商或渠道商入驻总量")
    private Integer totalBrandChannel;

    public QueryGroupIncludeBusinessTypeDto() {
    }

    @Override
    public String toString() {
        return "QueryGroupIncludeBusinessTypeDto{" +
                "transportDayCount=" + transportDayCount +
                ", transportWeekCount=" + transportWeekCount +
                ", transportMonthCount=" + transportMonthCount +
                ", totalTransportCount=" + totalTransportCount +
                ", warehouseDayCount=" + warehouseDayCount +
                ", warehouseWeekCount=" + warehouseWeekCount +
                ", warehouseMonthCount=" + warehouseMonthCount +
                ", totalWarehouseCount=" + totalWarehouseCount +
                ", brandChannelDayCount=" + brandChannelDayCount +
                ", brandChannelWeekCount=" + brandChannelWeekCount +
                ", brandChannelMonthCount=" + brandChannelMonthCount +
                ", totalBrandChannel=" + totalBrandChannel +
                '}';
    }
}
