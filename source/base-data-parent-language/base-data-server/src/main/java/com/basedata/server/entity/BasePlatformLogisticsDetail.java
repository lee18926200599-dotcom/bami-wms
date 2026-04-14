package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basedata.common.enums.BooleanEnum;
import com.common.base.entity.CurrentUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 承运商编码对照关系表
 * </p>
 */
@Data
@TableName("base_platform_logistics")
@ApiModel(value = "BasePlatformLogistics对象", description = "承运商编码对照关系表")
public class BasePlatformLogisticsDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @ApiModelProperty(value = "id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    @TableField("config_id")
    private Long configId;

    @ApiModelProperty(value = "配置项主表状态（0-已创建，1-启用，2-停用）（冗余便于查询）")
    @TableField("config_state")
    private Integer configState;

    @ApiModelProperty(value = "配置编码")
    @TableField("config_code")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    @TableField("config_name")
    private String configName;

    @ApiModelProperty(value = "配置类别（1电商平台，2外部系统）")
    @TableField("config_type")
    private Integer configType;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    @TableField("logistics_code")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "配送方式")
    @TableField("delivery_type")
    private Integer deliveryType;

    @ApiModelProperty(value = "电商平台承运商编码")
    @TableField("platform_logistics_code")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

    @ApiModelProperty(value = "外部系统承运商编码")
    @TableField("external_logistics_code")
    private String externalLogisticsCode;

    @ApiModelProperty(value = "仓库名称")
    @TableField("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value = "仓库编码")
    @TableField("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库id")
    @TableField("warehouse_id")
    private Long warehouseId;

    @ApiModelProperty(value = "货主名称")
    @TableField("owner_name")
    private String ownerName;

    @ApiModelProperty(value = "货主")
    @TableField("owner_id")
    private Long ownerId;

    @ApiModelProperty(value = "仓储服务商id")
    @TableField("service_provider_id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    @TableField("service_provider_name")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    @TableField("created_name")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_date")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    @TableField("modified_by")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    @TableField("modified_name")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    @TableField("modified_date")
    private Date modifiedDate;

    @ApiModelProperty(value = "乐观锁控制")
    @TableField("version")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    @TableField("deleted_flag")
    private Integer deletedFlag;

    public void setDefaultCreateValue(CurrentUser currentUser) {
        this.setCreatedBy(currentUser.getUserId());
        this.setCreatedName(currentUser.getUserName());
        this.setCreatedDate(new Date());
        this.setDeletedFlag(BooleanEnum.FALSE.getCode());
        this.setVersion(0);
    }

    public void setDefaultUpdateValue(CurrentUser currentUser) {
        this.setModifiedBy(currentUser.getUserId());
        this.setModifiedName(currentUser.getUserName());
        this.setModifiedDate(new Date());
    }
}
