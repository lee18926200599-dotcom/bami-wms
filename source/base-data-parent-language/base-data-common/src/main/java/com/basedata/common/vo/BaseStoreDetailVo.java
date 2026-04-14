package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * comments:店铺类型
 */
@Data
public class BaseStoreDetailVo {
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
    @ApiModelProperty(value = "是否MP")
    private Boolean mpFlag;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "0-已创建，1-启用，2-停用")
    private Integer state;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;
    @ApiModelProperty(value = "最后修改人")
    private String modifiedName;
    @ApiModelProperty(value = "最后修改时间")
    private Date modifiedDate;
}
