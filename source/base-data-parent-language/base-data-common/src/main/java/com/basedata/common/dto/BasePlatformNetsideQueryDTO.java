package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BasePlatformNetsideQueryDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组合md5唯一值")
    private String uniqueNo;

    @ApiModelProperty(value = "服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

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

    @ApiModelProperty(value = "网点ID/编码（电商平台）")
    private String netsiteCode;

    @ApiModelProperty(value = "网点名称（电商平台）")
    private String netsiteName;

    @ApiModelProperty(value = "省（电商平台）")
    private String province;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市（电商平台）")
    private String city;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区（电商平台）")
    private String area;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "镇（电商平台）")
    private String town;

    @ApiModelProperty(value = "镇编码")
    private String townCode;

    @ApiModelProperty(value = "网点详细地址（电商平台）")
    private String addressDetail;

    @ApiModelProperty(value = "月结账号")
    private String settleAccount;

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
}
