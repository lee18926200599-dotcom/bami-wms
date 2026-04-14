package com.org.permission.common.permission.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步数据权限
 */
@Data
public class SyncDataPermissionParam implements Serializable {
    private Long userId;
    private Long orgId;
    private Long groupId;
    private Long warehouseId;
    private String warehouseName;
    private String warehouseCode;
    private Integer state;

    private Long lineId;
    private String lineName;
    private String lineCode;
}
