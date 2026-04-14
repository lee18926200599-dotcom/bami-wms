package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * comments:仓库实体类型
 */
@Data
@TableName("base_store")
public class BaseStore {

    //仓库id
    @TableId("id")
    private Long id;
    //服务商id
    @TableField(value = "service_provider_id")
    private Long serviceProviderId;

    @TableField(value = "service_provider_name")
    private String serviceProviderName;

    //仓库编号
    @TableField(value = "store_code")
    private String storeCode;
    //仓库名称
    @TableField(value = "store_name")
    private String storeName;

    //货主id
    @TableField(value = "owner_id")
    private Long ownerId;
    //货主名称
    @TableField(value = "owner_name")
    private String ownerName;

    //平台code
    @TableField(value = "platform_code")
    private String platformCode;
    //平台名称
    @TableField(value = "platform_name")
    private String platformName;

    //商家code
    @TableField(value = "merchant_code")
    private String merchantCode;

    //联系人
    @TableField(value = "contacts")
    private String contacts;
    //联系电话
    @TableField(value = "contact_number")
    private String contactNumber;
    //联系手机
    @TableField(value = "contact_mobile")
    private String contactMobile;
    //联系邮箱
    @TableField(value = "contact_email")
    private String contactEmail;
    //是否主店铺
    @TableField(value = "main_store_flag")
    private Boolean mainStoreFlag;

    @TableField(value = "mp_flag")
    private Boolean mpFlag;

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
