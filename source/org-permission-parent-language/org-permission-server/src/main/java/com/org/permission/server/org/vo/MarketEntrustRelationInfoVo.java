package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 采销委托关系展示数据
 */
@ApiModel(description = "采销委托关系展示数据")
@Data
public class MarketEntrustRelationInfoVo extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("默认（0 否;1 是）")
    private Integer defaultFlag;
    @ApiModelProperty("默认")
    private String defaultFlagName;

    public String getDefaultFlagName() {
        return defaultFlag == null ? null : (defaultFlag == 1 ? "是" : "否");
    }


    @ApiModelProperty("状态（0创建，1启用，2停用）")
    private Integer state;

    private String stateName;
    @ApiModelProperty("货主ID")
    private Long ownerId;

    @ApiModelProperty("货主名称")
    private String ownerName;

    @ApiModelProperty("采购/销售组织ID")
    private Long marketOrgId;

    @ApiModelProperty("采购/销售组织名")
    private String marketOrgName;

    @ApiModelProperty("仓储服务商ID")
    private Long warehouseProviderId;

    @ApiModelProperty("仓储服务商名")
    private String warehouseProviderName;

    @ApiModelProperty("库存组织ID")
    private Long stockOrgId;

    @ApiModelProperty("库存组织名")
    private String stockOrgName;

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("仓库名")
    private String warehouseName;

    @ApiModelProperty("来源合同号")
    private String oriAccCode;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("是否平台签约:0 否;1 是")
    private Integer signFlag;
    @ApiModelProperty("是否平台签约")
    private String signFlagName;

    public String getSignFlagName() {
        return signFlag == null ? null : (signFlag == 1 ? "是" : "否");
    }

    public String getStateName() {
        if (this.state != null) {
            StateEnum stateEnum = StateEnum.getEnum(this.state);
            if (stateEnum != null) {
                return stateEnum.getName();
            }
        }
        return this.stateName;
    }
}
