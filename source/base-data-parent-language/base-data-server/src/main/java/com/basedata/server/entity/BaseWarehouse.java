package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * comments:仓库实体类型
 */
@Data
@TableName("base_warehouse")
public class BaseWarehouse {

    //仓库id
    @TableId(value ="id")
    private Long id;
    //服务商id
    @TableField(value = "service_provider_id")
    private Long serviceProviderId;

    @TableField(value = "service_provider_name")
    private String serviceProviderName;

    //仓库编号
    @TableField(value = "warehouse_code")
    private String warehouseCode;
    //仓库名称
    @TableField(value = "warehouse_name")
    private String warehouseName;

    //所属单位
    @TableField(value = "group_id")
    private Long groupId;
    //所属单位名称
    @TableField(value = "group_name")
    private String groupName;

    //运营类型字典code
    @TableField(value = "operation_type")
    private String operationType;
    //仓库性质字典code
    @TableField(value = "warehouse_property")
    private String warehouseProperty;
    //仓库面积
    @TableField(value = "warehouse_area")
    private BigDecimal warehouseArea;
    //联系人
    @TableField(value = "contact_person")
    private String contactPerson;
    //联系电话
    @TableField(value = "contact_tel")
    private String contactTel;
    //联系手机
    @TableField(value = "contact_phone")
    private String contactPhone;
    //联系邮箱
    @TableField(value = "contact_email")
    private String contactEmail;
    //省
    @TableField(value = "province_code")
    private String provinceCode;
    //省名称
    @TableField(value = "province_name")
    private String provinceName;
    //市
    @TableField(value = "city_code")
    private String cityCode;
    //市名称
    @TableField(value = "city_name")
    private String cityName;
    //区（县）
    @TableField(value = "district_code")
    private String districtCode;
    //区名称
    @TableField(value = "district_name")
    private String districtName;
    //乡（镇）
    @TableField(value = "town_code")
    private String townCode;
    //乡名称
    @TableField(value = "town_name")
    private String townName;
    //详细地址
    @TableField(value = "address")
    private String address;
    //开仓时间
    @TableField(value = "open_date")
    private Date openDate;
    //开仓开始时间
    @TableField(value = "open_start_time")
    private String openStartTime;
    //开仓结束时间
    @TableField(value = "open_end_date")
    private String openEndDate;
    //状态（0-已创建，1-启用，2-停用）
    @TableField(value = "state")
    private Integer state;
    //创建人
    @TableField(value = "created_by")
    private Long createdBy;

    //创建人名称
    @TableField(value = "created_name")
    private String createdName;

    //创建时间
    @TableField(value = "created_date")
    private Date createdDate;
    //最后修改人
    @TableField(value = "modified_by")
    private Long modifiedBy;

    //最后修改人
    @TableField(value = "modified_name")
    private String modifiedName;

    //最后修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;
    //备注
    @TableField(value = "remark")
    private String remark;
    //逻辑删除标识（0-未删除，1-已删除）
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //不自增，乐观锁版本控制
    @TableField(value = "version")
    private Integer version;
}
