package com.org.permission.common.org.dto;

import com.org.permission.common.dto.BaseDto;
import lombok.Data;

@Data
public class OrganizationFunctionDto extends BaseDto {
    /**
     * 职能类别
     */
    private Integer functionType;
    /**
     *组织id
     */
    private Long orgId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     *默认应付组织
     */
    private Long defaultPayOrgId;
    /**
     *默认结算财务组织
     */
    private Long defaultSettlementOrgId;
    /**
     *默认库存组织
     */
    private Long defaultStockOrgId;
    /**
     *默认应收组织
     */
    private Long defaultReceiveOrgId;
    /**
     *默认核算组织
     */
    private Long defaultAccountOrgId;
    /**
     *默认物流组织
     */
    private Long defaultLogisticsOrgId;


    /**
     *默认应付组织
     */
    private String defaultPayOrgName;
    /**
     *默认结算财务组织
     */
    private String defaultSettlementOrgName;
    /**
     *默认库存组织
     */
    private String defaultStockOrgName;
    /**
     *默认应收组织
     */
    private String defaultReceiveOrgName;
    /**
     *默认核算组织
     */
    private String defaultAccountOrgName;
    /**
     *默认物流组织
     */
    private String defaultLogisticsOrgName;

}
