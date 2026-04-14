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
 * 快递面单模板授权店铺
 * </p>
 */
@Data
@TableName("base_platform_store")
@ApiModel(value = "BasePlatformStore对象", description = "快递面单模板授权店铺")
public class BasePlatformStore implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("id")
    @ApiModelProperty(value = "id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    @TableField("config_code")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    @TableField("config_name")
    private String configName;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

    @ApiModelProperty(value = "授权店铺编码")
    @TableField("auth_store_code")
    private String authStoreCode;

    @ApiModelProperty(value = "授权店铺名称")
    @TableField("auth_store_name")
    private String authStoreName;

    @ApiModelProperty(value = "授权店铺所属货主ID")
    @TableField("belong_owner_id")
    private Long belongOwnerId;

    @ApiModelProperty(value = "授权店铺所属货主")
    @TableField("belong_owner_name")
    private String belongOwnerName;

    @ApiModelProperty(value = "集团id")
    @TableField(value = "group_id")
    private Long groupId;

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
