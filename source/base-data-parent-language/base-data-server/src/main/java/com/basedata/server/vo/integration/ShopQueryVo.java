package com.basedata.server.vo.integration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ShopQueryVo implements Serializable {

    @ApiModelProperty("电商平台编码")
    private String platformCode;

    @ApiModelProperty("电商平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty("货主编码")
    @NotBlank
    private String customer_id;

    @ApiModelProperty("授权码")
    private String authorizationCode;

    @ApiModelProperty("商家ID")
    private String vendorId;

    @ApiModelProperty("货主ID")
    private Long ownerId;

}
