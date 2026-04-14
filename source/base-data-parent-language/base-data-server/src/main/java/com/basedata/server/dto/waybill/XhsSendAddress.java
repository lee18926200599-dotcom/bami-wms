package com.basedata.server.dto.waybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XhsSendAddress {

    //省份(必填)
    private String province;

    //城市(必填)
    private String city;

    //区县，订购关系返回了district则必须要传(非必填)
    private String district;

    //乡镇街道，订购关系返回了town则必须要传(非必填)
    private String town;

    //详细地址(必填)
    private String detail;

    //网点地址ID
    @ApiModelProperty(value = "网点地址ID")
    private String addressId;

    //月结账号
    @ApiModelProperty(value = "月结账号")
    private String monthAccount;

}
