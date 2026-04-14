package com.basedata.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePlatformLogisticsUpdateVo {
    @ApiModelProperty(value = "主键id。修改的数据有则必传，新增的数据没有ID不要传")
    private Long id;

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

    @ApiModelProperty(value = "电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

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

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

//    @ApiModelProperty(value = "创建人")
//    private Long createdBy;
//
//    @ApiModelProperty(value = "创建人名称")
//    private String createdName;
//
//    @ApiModelProperty(value = "创建时间")
//    private Date createdDate;
//
//    @ApiModelProperty(value = "修改人")
//    private Long modifiedBy;
//
//    @ApiModelProperty(value = "修改人名称")
//    private String modifiedName;
//
//    @ApiModelProperty(value = "修改时间")
//    private Date modifiedDate;
//
//    @ApiModelProperty(value = "乐观锁控制")
//    private Integer version;
//
    @ApiModelProperty(value = "是否删除（0=未删除 1=删除）")
    private Integer deletedFlag;

}
