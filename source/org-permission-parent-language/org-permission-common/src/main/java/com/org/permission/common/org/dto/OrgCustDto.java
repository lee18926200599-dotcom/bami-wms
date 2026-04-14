package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客商集团信息
 */
@Data
public class OrgCustDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 客商ID
     */
    private Long custId;
    /**
     * 客商名
     */
    private String custName;
    /**
     * 业务单元id
     */
    private Long orgId;

    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 集团编码
     */
    private String groupCode;
    /**
     * 集团编码
     */
    private String groupName;
    /**
     * 业务类型
     */
    private String bizType;

    public OrgCustDto() {
    }

    public OrgCustDto(Long custId, Long groupId, String groupCode, String groupName) {
        this.custId = custId;
        this.groupId = groupId;
        this.groupCode = groupCode;
        this.groupName = groupName;
    }

    public OrgCustDto(Long custId, Long groupId, String groupCode, String groupName, String bizType) {
        this.custId = custId;
        this.groupId = groupId;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.bizType = bizType;
    }

    public OrgCustDto(Long orgId, Long custId, Long groupId, String groupCode, String groupName, String bizType) {
        this.orgId = orgId;
        this.custId = custId;
        this.groupId = groupId;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrgCustDto{");
        sb.append("custId=").append(custId);
        sb.append(", custName=").append(custName);
        sb.append(", groupId=").append(groupId);
        sb.append(", groupCode='").append(groupCode).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
