package com.basedata.server.dto.integration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取授权店铺下网点地址-面单服务订购及面单使用情况实体类")
public class GetPlatformNetsideAddressRespDto {

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty("货主名称")
    private String ownerName;

    @ApiModelProperty("电商平台编码")
    private String platformCode;

    @ApiModelProperty("电商平台")
    private String platformName;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsCodeName;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "网点ID")
    private String branchCode;

    @ApiModelProperty(value = "网点名称")
    private String branchName;

    @ApiModelProperty(value = "品牌编码，TM对应的SF")
    private String brandCode;

    @ApiModelProperty(value = "余额")
    private String allocatedQuantity;

    @ApiModelProperty("微信视频号小店-店铺ID")
    private String attrStr1;

    @ApiModelProperty("微信视频号小店-电子面单ID")
    private String attrStr2;

    @ApiModelProperty("京东是否开通送货上门：DELIVERY_TO_DOOR（1是）")
    private String attrStr3;

    @ApiModelProperty(value = "扩展字符串4")
    private String attrStr4;

    @ApiModelProperty(value = "扩展字符串5")
    private String attrStr5;

    @ApiModelProperty(value = "商家编码/ID（来源于平台授权）")
    private String vendorId;

    /* 以下是网点地址信息 */
    @ApiModelProperty(value = "行标识（给前端的唯一标识）")
    private String lineNo;

    private String company;

    @ApiModelProperty(value = "区县")
    private String area;

    @ApiModelProperty(value = "区县编码")
    private String areaCode;

    @ApiModelProperty(value = "县镇/街道名称")
    private String town;

    @ApiModelProperty(value = "县镇/街道名称编码")
    private String townCode;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "移动电话")
    private String mobile;

    private String tel;
    private String zipCode;

    private String email;

    private String countryCode;

    //网点地址ID
    @ApiModelProperty(value = "网点地址ID")
    private String addressId;

    //月结账号
    @ApiModelProperty(value = "月结账号")
    private String monthAccount;

    //店铺ID
    @ApiModelProperty(value = "店铺ID")
    private String shopId;

    //月结账号
    @ApiModelProperty(value = "月结账号")
    private String acctId;

}
