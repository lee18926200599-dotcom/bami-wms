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
 * 货主平台授权信息表
 * </p>
 */
@Data
@TableName("base_platform_auth")
@ApiModel(value = "BasePlatformAuth对象", description = "货主平台授权信息表")
public class BasePlatformAuth implements Serializable {
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

    @ApiModelProperty(value = "货主ID")
    @TableField("owner_id")
    private Long ownerId;

    @ApiModelProperty(value = "货主编码")
    @TableField("owner_code")
    private String ownerCode;

    @ApiModelProperty(value = "货主名称")
    @TableField("owner_name")
    private String ownerName;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

    @ApiModelProperty(value = "授权编码/客户id(店铺id)")
    @TableField("customer_id")
    private String customerId;

    @ApiModelProperty(value = "商家编码/ID")
    @TableField("vendor_id")
    private String vendorId;

    @ApiModelProperty(value = "appkey")
    @TableField("app_key")
    private String appKey;

    @ApiModelProperty(value = "授权token")
    @TableField("access_token")
    private String accessToken;

    @ApiModelProperty(value = "app_secret")
    @TableField("app_secret")
    private String appSecret;

    @ApiModelProperty(value = "授权token过期时间")
    @TableField("access_token_expired")
    private Date accessTokenExpired;

    @ApiModelProperty(value = "刷新token")
    @TableField("refresh_token")
    private String refreshToken;

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
