package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购业务委托关系数据实体
 */
@ApiModel
@Data
public class PurchaseEntrustRelationInfoVo extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("默认（1是，0否）")
    private Integer defaultFlag;
    @ApiModelProperty("默认")
    private String defaultFlagName;


    @ApiModelProperty(value = "状态(0创建;1启用;2停用;")
    private Integer state;

    @ApiModelProperty(value = "状态(0创建;1启用;2停用;")
    private String stateName;

    @ApiModelProperty("采购组织ID")
    private Integer purchaseOrgId;

    @ApiModelProperty("采购组织名")
    private String purchaseOrgName;

    @ApiModelProperty("库存组织ID")
    private Integer stockOrgId;

    @ApiModelProperty("库存组织名")
    private String stockOrgName;

    @ApiModelProperty("应付组织ID")
    private Integer payOrgId;

    @ApiModelProperty("应付组织名")
    private String payOrgName;

    @ApiModelProperty("结算组织ID")
    private Integer settleOrgId;

    @ApiModelProperty("结算组织名")
    private String settleOrgName;

    @ApiModelProperty("备注")
    private String note;

    public String getStateName() {
        if (this.state != null) {
            StateEnum stateEnum = StateEnum.getEnum(this.state);
            if (stateEnum != null) {
                return stateEnum.getName();
            }
            return "未知状态";
        }
        return this.stateName;
    }

    public String getDefaultFlagName() {
        return defaultFlag == null ? null : (defaultFlag == 1 ? "是" : "否");
    }
}

