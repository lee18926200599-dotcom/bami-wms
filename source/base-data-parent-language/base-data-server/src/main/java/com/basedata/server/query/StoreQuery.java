package com.basedata.server.query;

import com.basedata.common.vo.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreQuery extends BaseQuery {

    @ApiModelProperty(value = "0-已创建，1-启用，2-停用")
    private Integer state;

    @ApiModelProperty(value = "店铺编码")
    private String storeCode;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "是否主店铺")
    private Boolean mainStoreFlag;
}
