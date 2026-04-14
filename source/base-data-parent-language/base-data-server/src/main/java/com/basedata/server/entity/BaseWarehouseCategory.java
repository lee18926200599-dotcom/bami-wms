package com.basedata.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * comments:仓储分类实体类型
 */
@Data
@TableName(value = "base_warehouse_category")
public class BaseWarehouseCategory {
    @TableId("id")
    @TableField(value = "id")
    private Long id;
    @TableField(value = "group_id")
    private Long groupId;
    @TableField(value = "warehouse_id")
    private Long warehouseId;
    @TableField(value = "warehouse_name")
    private String warehouseName;
    @TableField(value = "warehouse_code")
    private String warehouseCode;
    //服务商id
    @TableField(value = "service_provider_id")
    private Long serviceProviderId;
    //上级分类ID
    @TableField(value = "parent_id")
    private Long parentId;
    //分类编码
    @TableField(value = "category_code")
    private String categoryCode;
    //分类名称
    @TableField(value = "category_name")
    private String categoryName;
    //助记码
    @TableField(value = "mnemonic_code")
    private String mnemonicCode;
    //状态（0-已创建，1-启用，2-停用）
    @TableField(value = "state")
    private Integer state;
    //排序
    @TableField(value = "sort")
    private Integer sort;
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
    //最后修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;
    //逻辑删除字段(0未删除，1已删除)
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //不自增，乐观锁版本控制
    @TableField(value = "version")
    private Integer version;
}
