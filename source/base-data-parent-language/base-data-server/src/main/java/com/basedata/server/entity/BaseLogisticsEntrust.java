package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * comments:承运商委托关系表实体类型
 */
@Data
@TableName(value = "base_logistics_entrust")
public class BaseLogisticsEntrust {

    //id
    @TableId("id")
    @TableField(value = "id")
    private Long id;
    //配置编码
    @TableField(value = "config_code")
    private String configCode;
    //配置名称
    @TableField(value = "config_name")
    private String configName;
    //集团id
    @TableField(value = "group_id")
    private Long groupId;
    //仓储服务商id
    @TableField(value = "service_provider_id")
    private Long serviceProviderId;
    //仓储服务商名称
    @TableField(value = "service_provider_name")
    private String serviceProviderName;
    //仓库名称
    @TableField(value = "warehouse_name")
    private String warehouseName;
    //仓库编码
    @TableField(value = "warehouse_code")
    private String warehouseCode;
    //仓库id
    @TableField(value = "warehouse_id")
    private Long warehouseId;
    //货主名称
    @TableField(value = "owner_name")
    private String ownerName;
    //货主
    @TableField(value = "owner_id")
    private Long ownerId;
    //系统承运商ID（来源于系统客商体系）
    @TableField(value = "logistics_id")
    private Long logisticsId;
    //系统承运商编码
    @TableField(value = "logistics_code")
    private String logisticsCode;
    //系统承运商名称
    @TableField(value = "logistics_name")
    private String logisticsName;
    //配送方式
    @TableField(value = "delivery_type")
    private Integer deliveryType;
    //网点id
    @TableField(value = "branch_id")
    private Long branchId;
    //网点名称
    @TableField(value = "branch_name")
    private String branchName;
    //1-仓储 2-货主
    @TableField(value = "settle_method")
    private Integer settleMethod;
    //是否默认
    @TableField(value = "default_flag")
    private Integer defaultFlag;
    //状态（0-已创建，1-启用，2-停用）
    @TableField(value = "state")
    private Integer state;
    //备注
    @TableField(value = "remark")
    private String remark;
    //创建人
    @TableField(value = "created_by")
    private Long createdBy;
    //创建人名称
    @TableField(value = "created_name")
    private String createdName;
    //创建时间
    @TableField(value = "created_date")
    private Date createdDate;
    //修改人
    @TableField(value = "modified_by")
    private Long modifiedBy;
    //修改人名称
    @TableField(value = "modified_name")
    private String modifiedName;
    //修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;
    //乐观锁控制
    @TableField(value = "version")
    private Integer version;
    //是否删除
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
}