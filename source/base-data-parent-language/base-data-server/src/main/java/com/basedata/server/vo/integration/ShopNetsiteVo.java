package com.basedata.server.vo.integration;

import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("网点信息")
public class ShopNetsiteVo implements Serializable {

    @ApiModelProperty("网点简称")
    private String shortAddress;

    @ApiModelProperty("网点Code")
    private String netsite_code;

    @ApiModelProperty("网点名称")
    private String netsite_name;

    @ApiModelProperty("电子面单余额")
    private String amount;

    @ApiModelProperty("客户编码（直营物流公司的月结卡号）")
    private String settleAccount;

    @ApiModelProperty(value = "品牌编码，TM对应的SF")
    private String brandCode;

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

    @ApiModelProperty("网点地址信息")
    private List<ReturnorderSenderVO> sendAddress;

}
