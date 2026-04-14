package com.basedata.common.vo;

import com.basedata.common.enums.StateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BasePlatformAuthVo {
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

    @ApiModelProperty(value = "授权token过期时间")
    private Date accessTokenExpired;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "状态名称")
    private String stateName;

    public String getStateName() {
        return StateEnum.getCodeName(state);
    }

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

}
