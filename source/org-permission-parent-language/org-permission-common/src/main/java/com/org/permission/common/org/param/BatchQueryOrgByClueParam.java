package com.org.permission.common.org.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BatchQueryOrgByClueParam implements Serializable {
    private String orgName;
    private String phone;
    private Long groupId;
    private Long orgId;


}
