package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 物流委托关系展示数据
 */
@ApiModel(description = "物流委托关系数据")
@Data
public class LogisticsEntrustRelationInfoVo extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("默认（1是;0否）（必填）")
    private Integer defaultFlag;

    @ApiModelProperty(value = "状态(0创建;1启用;2停用;")
    private Integer state;

    @ApiModelProperty(value = "状态(0创建;1启用;2停用;")
    private String stateName;

    @ApiModelProperty("物流服务商ID")
    private Long logisticsProviderId;

    @ApiModelProperty("物流服务商名")
    private String logisticsProviderName;

    @ApiModelProperty("物流组织ID")
    private Long logisticsOrgId;

    @ApiModelProperty("物流组织名")
    private String logisticsOrgName;

    @ApiModelProperty("关联物流服务商ID")
    private Long relevanceLogisticsProviderId;

    @ApiModelProperty("关联物流服务商ID")
    private String relevanceLogisticsProviderName;

    @ApiModelProperty("关联物流组织ID")
    private Long relevanceLogisticsOrgId;

    @ApiModelProperty("关联物流组织名")
    private String relevanceLogisticsOrgName;

    @ApiModelProperty("来源合同号")
    private String oriAccCode;

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

}
