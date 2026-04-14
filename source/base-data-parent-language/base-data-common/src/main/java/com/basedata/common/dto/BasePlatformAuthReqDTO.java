package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BasePlatformAuthReqDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "ids")
    private List<Long> ids;

    @ApiModelProperty(value = "配置编码（模糊查询）")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @ApiModelProperty(value = "货主ID列表")
    private List<Long> ownerIdList;

    @ApiModelProperty(value = "货主编码")
    private String ownerCode;

    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台编码列表")
    private List<String> platformCodeList;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "appkey")
    private String appKey;

    @ApiModelProperty(value = "授权token")
    private String accessToken;

    @ApiModelProperty(value = "授权token过期时间（开始）")
    private Date expireDateStart;

    @ApiModelProperty(value = "授权token过期时间（结束）")
    private Date expireDateEnd;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "集团id")
    private Long groupId;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商id列表")
    private List<Long> serviceProviderIdList;

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

    @ApiModelProperty(value = "创建时间（开始）")
    private Date createdDateBegin;

    @ApiModelProperty(value = "创建时间（结束）")
    private Date createdDateEnd;

    @ApiModelProperty(value = "修改人")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间（开始）")
    private Date modifiedDateBegin;

    @ApiModelProperty(value = "修改时间（结束）")
    private Date modifiedDateEnd;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;
}
