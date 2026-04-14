package com.basedata.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 承运商编码对照关系表
 * </p>
 */
@Data
@ApiModel(description = "承运商编码对照关系明细请求")
public class BasePlatformLogisticsDetailReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "明细id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    private Long configId;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置类别（1电商平台，2外部系统）")
    private Integer configType;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;

    @ApiModelProperty(value = "电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台编码列表")
    private List<String> platformCodeList;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "外部系统承运商编码")
    private String externalLogisticsCode;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @ApiModelProperty(value = "货主")
    private Long ownerId;

    @ApiModelProperty(value = "货主列表")
    private List<Long> ownerIdList;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;
}
