package com.basedata.server.dto.netside;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class XhsWaybillUsageInfo {

    //可用余额，直营没有这个数据默认全部返回0
    private BigDecimal quantity;

    //累计已经分配的数量
    private BigDecimal allocatedQuantity;

    //取消的面单数量
    private BigDecimal cancelQuantity;

    //回收的面单数量，直营没有这个数据默认全部返回0
    private BigDecimal recycledQuantity;
}
