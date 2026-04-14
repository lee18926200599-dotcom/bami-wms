package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FacesheetConfigVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "店铺Code")
    private String storeCode;

    @ApiModelProperty(value = "是否mp")
    private Boolean mpFlag;

    @ApiModelProperty(value = "联系人")
    private String delivererContact;

    @ApiModelProperty(value = "联系人电话")
    private String delivererContactTel;

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "系统承运商编码")
    private String deliveryCode;

}
