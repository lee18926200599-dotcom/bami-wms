package com.basedata.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePlatformNetsideVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

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

    @ApiModelProperty(value = "品牌编码，TM对应的SF")
    private String brandCode;

    @ApiModelProperty(value = "月结账号")
    private String settleAccount;

    @ApiModelProperty(value = "微信店铺id")
    private String attrStr1;

    @ApiModelProperty(value = "微信电子面单账号id")
    private String attrStr2;

    @ApiModelProperty(value = "扩展字符串3")
    private String attrStr3;

    @ApiModelProperty(value = "扩展字符串4")
    private String attrStr4;

    @ApiModelProperty(value = "扩展字符串5")
    private String attrStr5;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;
}
