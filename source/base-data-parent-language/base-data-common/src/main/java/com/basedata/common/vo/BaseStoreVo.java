package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * comments:店铺类型
 */
@Data
public class BaseStoreVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "店铺编码")
    private String storeCode;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "货主编码")
    private String ownerCode;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系人电话")
    private String contactNumber;

    @ApiModelProperty(value = "联系人手机")
    private String contactMobile;

    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "是否主店铺")
    private Boolean mainStoreFlag;
}
