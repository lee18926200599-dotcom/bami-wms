package com.basedata.server.dto.integration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReturnorderSenderVO {

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
