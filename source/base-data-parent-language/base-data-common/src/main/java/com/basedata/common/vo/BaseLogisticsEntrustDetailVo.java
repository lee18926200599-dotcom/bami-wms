package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * comments:承运商委托关系表实体类型
 */
@Data
public class BaseLogisticsEntrustDetailVo {
    //id
    @ApiModelProperty(value = "id")
    private Long id;
    //配置编码
    @ApiModelProperty(value = "配置编码")
    private String configCode;
    //配置名称
    @ApiModelProperty(value = "配置名称")
    private String configName;
    //仓储服务商id
    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;
    //仓储服务商名称
    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;
    //仓库名称
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    //仓库编码
    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;
    //仓库id
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    //货主名称
    @ApiModelProperty(value = "货主名称")
    private String ownerName;
    //货主
    @ApiModelProperty(value = "货主id")
    private Long ownerId;
    @ApiModelProperty(value = "承运商id")
    private Long logisticsId;
    //系统承运商编码
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;
    //系统承运商名称
    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;
    //配送方式
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;
    //网点id
    @ApiModelProperty(value = "网点id")
    private String branchId;
    //网点名称
    @ApiModelProperty(value = "网点名称")
    private String branchName;
    //1-仓储 2-货主
    @ApiModelProperty(value = "结算方式1-仓储 2-货主")
    private Integer settleMethod;
    //是否默认
    @ApiModelProperty(value = "是否默认")
    private Integer defaultFlag;
    //状态（0-已创建，1-启用，2-停用）
    @ApiModelProperty(value = "状态")
    private Integer state;
    //备注
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;
    @ApiModelProperty(value = "最后修改人")
    private String modifiedName;
    @ApiModelProperty(value = "最后修改时间")
    private Date modifiedDate;

}