package com.common.base.entity;

import lombok.Data;

@Data
public class CurrentUser {
    //用户id
    private Long userId;
    //创建人名称
    private String userName;
    //仓储服务商id
    private Long serviceProviderId;
    //仓储服务商名称
    private String serviceProviderName;
    //集团id
    private Long groupId;
    //仓库id
    private Long warehouseId;
    //仓库Code
    private String warehouseCode;
    //仓库名称
    private String warehouseName;

}
