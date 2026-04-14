package com.org.permission.common.org.param;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 金融职能
 */
@Data
public class BankOrgFuncInfoBean extends BaseDto {

    /**
     * 组织ID
     */
    @ApiModelProperty("组织ID")
    private Long orgId;
    /**
     * 组织名
     */
    @ApiModelProperty("组织名")
    private String orgName;
    /**
     * 功能类别
     * 1 法人公司
     * 2 财务
     * 3 采购
     * 4 销售
     * 5 仓储
     * 6 物流
     * 7 金融
     * 8 劳务
     * 9 平台
     */
    @ApiModelProperty("功能类别")
    private Integer funcType=7;

    @ApiModelProperty("结算组织ID")
    private Long settleOrgId;
    /**
     * 核算组织ID
     */
    @ApiModelProperty("核算组织ID")
    private Long accountOrgId;

    /**
     * 结算组织名
     */
    @ApiModelProperty("结算组织名")
    private String settleOrgName;
    /**
     * 核算组织名
     */
    @ApiModelProperty("核算组织名")
    private String accountOrgName;
}
