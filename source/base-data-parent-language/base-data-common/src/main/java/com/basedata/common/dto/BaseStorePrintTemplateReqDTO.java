package com.basedata.common.dto;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseStorePrintTemplateReqDTO extends BaseQuery {
    @ApiModelProperty(value = "配置项ID")
    private Long id;

    @ApiModelProperty(value = "ids")
    private List<Long> ids;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台编码列表")
    private List<String> platformCodeList;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

//    @ApiModelProperty(value = "授权店铺编码")
//    private String authStoreCode;
//
//    @ApiModelProperty(value = "授权店铺名称")
//    private String authStoreName;

    @ApiModelProperty(value = "授权店铺所属货主ID")
    private Long belongOwnerId;

    @ApiModelProperty(value = "授权店铺所属货主ID列表")
    private List<Long> belongOwnerIdList;

    @ApiModelProperty(value = "授权店铺所属货主")
    private String belongOwnerName;

    @ApiModelProperty(value = "集团id")
    private Long groupId;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商id列表")
    private List<Long> serviceProviderIdList;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
