package com.org.permission.server.org.dto.param;

import lombok.Data;

@Data
public class InitCustEntrustRelParam {
    private Integer delegationType;
    private Long operateUser;
    private String operateUserName;
    private Integer state;
    /**
     * 采销
     */
    private Long mainCustId;
    private Long mainBUId;
    private Long entrustCustId;
    private Long entrustBUId;


    /**
     * 仓储
     */
    private Long warehouseProviderId;
    private Long stockOrgId;
    private Long logisticsProviderId;
    private Long logisticsOrgId;

    /**
     * 物流
     *
     * @return
     */
    private Long relevanceLogisticsProviderId;
    private Long relevanceLogisticsOrgId;
}
