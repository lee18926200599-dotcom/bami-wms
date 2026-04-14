package com.basedata.common.dto;

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
@ApiModel(description = "BasePlatformAuthDTO")
public class BasePlatformAuthDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "货主编码")
    private String ownerCode;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "授权编码/客户id(店铺id)")
    private String customerId;

    @ApiModelProperty(value = "商家编码/ID")
    private String vendorId;

    @ApiModelProperty(value = "appkey")
    private String appKey;

    @ApiModelProperty(value = "授权token")
    private String accessToken;

    @ApiModelProperty(value = "app_secret")
    private String appSecret;

    @ApiModelProperty(value = "授权token过期时间")
    private Date accessTokenExpired;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "集团id")
    private Long groupId;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
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
