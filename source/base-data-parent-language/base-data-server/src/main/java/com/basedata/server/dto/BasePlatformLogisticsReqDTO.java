package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BasePlatformLogisticsReqDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "ids")
    private List<Long> ids;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商ID列表（来源于系统客商体系）")
    private List<Long> logisticsIdList;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    // 枚举 DeliveryTypeEnum
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;

    @ApiModelProperty(value = "集团id", hidden = true)
    private Long groupId;

    @ApiModelProperty(value = "仓储服务商id", hidden = true)
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商id列表")
    private List<Long> serviceProviderIdList;

    @ApiModelProperty(value = "仓储服务商名称", hidden = true)
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;
}
