package com.common.log.entity;

import lombok.Data;

@Data
public class BusinessOperateLogQuery {

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 所属节点
     */
    private String node;

    /**
     * 参考单据
     */
    private String referenceNo;

}
