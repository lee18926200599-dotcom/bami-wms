package com.basedata.server.query;

import com.basedata.common.vo.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * comments:承运商委托关系表实体类型
 */
@Data
public class BaseLogisticsEntrustQuery extends BaseQuery {

    @ApiModelProperty(value = "集团id")
    private Long serviceProviderId;

    //仓库id
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    //货主
    @ApiModelProperty(value = "货主id")
    private Long ownerId;

    @ApiModelProperty(value = "承运商id")
    private Long logisticsId;
    //配送方式
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;
    //网点id
    @ApiModelProperty(value = "网点id")
    private String branchId;
    //1-仓储 2-货主
    @ApiModelProperty(value = "结算方式1-仓储 2-货主")
    private Integer settleMethod;
    //是否默认
    @ApiModelProperty(value = "是否默认")
    private Integer defaultFlag;
    //状态（0-已创建，1-启用，2-停用）
    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "配置编码（模糊查询）")
    private String configCode;
}