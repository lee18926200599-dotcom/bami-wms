package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@NoArgsConstructor
@Data
public class BasePlatformLogisticsUpdateReqDTO {
    @ApiModelProperty(value = "配置项id：修改的数据有则必传，新增的数据没有ID不要传")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称（规则描述）")
    private String configName;
    // 枚举 DeliveryTypeEnum
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryType;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @NotNull(message = "系统承运商编码 不能为空！")
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @NotNull(message = "系统承运商名称 不能为空！")
    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "电商平台tab页")
    List<PlatformTab> platformTabList;
    @ApiModelProperty(value = "外部系统tab页")
    List<ExternalSystemTab> externalSystemTabList;


    @NoArgsConstructor
    @Data
    public static class PlatformTab {
        @ApiModelProperty(value = "配置项明细id：修改的数据有则必传，新增的数据没有ID不要传")
        private Long id;

        @ApiModelProperty(value = "电商平台承运商编码")
        private String platformLogisticsCode;

        @ApiModelProperty(value = "电商平台编码")
        private String platformCode;

        @ApiModelProperty(value = "电商平台名称")
        private String platformName;

        @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）（必传）")
        private Integer state;

        @ApiModelProperty(value = "备注")
        private String remark;

        @ApiModelProperty(value = "是否删除（0=未删除 1=删除）（修改的时候必传）")
        private Integer deletedFlag;

    }

    @NoArgsConstructor
    @Data
    public static class ExternalSystemTab {
        @ApiModelProperty(value = "配置项明细id：修改的数据有则必传，新增的数据没有ID不要传")
        private Long id;

        @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）（必传）")
        private Integer state;

        @ApiModelProperty(value = "仓库id")
        private Long warehouseId;

        @ApiModelProperty(value = "仓库编码")
        private String warehouseCode;

        @ApiModelProperty(value = "仓库名称")
        private String warehouseName;

        @ApiModelProperty(value = "货主")
        private Long ownerId;

        @ApiModelProperty(value = "货主名称")
        private String ownerName;

        @ApiModelProperty(value = "外部系统承运商编码")
        private String externalLogisticsCode;

        @ApiModelProperty(value = "备注")
        private String remark;

        @ApiModelProperty(value = "是否删除（0=未删除 1=删除）（修改的时候必传）")
        private Integer deletedFlag;

    }

}
