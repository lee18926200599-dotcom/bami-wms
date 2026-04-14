package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团间仓储委托关系展示数据
 */
@ApiModel
@Data
public class StoragePlatformEntrustRelationInfoVo extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("默认（1是，0否）")
    private Integer defaultFlag;
    @ApiModelProperty("默认")
    private String defaultFlagName;
    @ApiModelProperty("状态（0创建，1启用，2停用）")
    private Integer state;

    @ApiModelProperty("状态（0创建，1启用，2停用）")
    private String stateName;

    @ApiModelProperty("仓储服务商")
    private Long warehouseProviderId;

    @ApiModelProperty("仓储服务商")
    private String warehouseProviderName;

    @ApiModelProperty("库存组织")
    private Long stockOrgId;

    @ApiModelProperty("库存组织")
    private String stockOrgName;

    @ApiModelProperty("仓库")
    private Long warehouseId;

    @ApiModelProperty("仓库")
    private String warehouseName;

    @ApiModelProperty("物流服务商")
    private Long logisticsProviderId;

    @ApiModelProperty("物流服务商")
    private String logisticsProviderName;

    @ApiModelProperty("物流组织")
    private Long logisticsOrgId;

    @ApiModelProperty("物流组织")
    private String logisticsOrgName;

    @ApiModelProperty("来源合同号")
    private String oriAccCode;

    @ApiModelProperty("备注")
    private String note;

    public StoragePlatformEntrustRelationInfoVo() {
    }

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
