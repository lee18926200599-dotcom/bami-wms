package com.basedata.server.dto.netside;

import lombok.Data;

import java.util.List;

@Data
public class XhsSubscribeInfo {

    //快递公司编码
    private String cpCode;

    //快递公司名称
    private String cpName;

    //类型，1-直营 2-加盟
    private Integer cpType;

    //网点编码
    private String branchCode;

    //网点名称
    private String branchName;

    //品牌code
    private String brandCode;

    //月结卡号，仅邮政、EMS、京东、顺丰、德邦等直营快递支持
    private String customerCode;

    //发货地址
    private List<XhsNetSiteAddressInfo> senderAddressList;

    //needUsage=true才会返回
    private XhsWaybillUsageInfo usage;

}
