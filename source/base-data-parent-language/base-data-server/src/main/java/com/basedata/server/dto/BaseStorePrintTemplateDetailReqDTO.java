package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseStorePrintTemplateDetailReqDTO {

    @ApiModelProperty(value = "明细id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    private Long configId;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商ID列表（来源于系统客商体系）")
    private List<Long> logisticsIdList;

    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "授权店铺所属货主ID")
    private Long belongOwnerId;

    @ApiModelProperty(value = "授权店铺所属货主")
    private String belongOwnerName;

    @ApiModelProperty(value = "面单模板类型（0标准、1自定义）")
    private Integer templateType;

    @ApiModelProperty(value = "面单模板名称")
    private String templateName;

    @ApiModelProperty(value = "外部面单模板名称")
    private String externalTemplateName;

    @ApiModelProperty(value = "面单模板地址")
    private String templateUrl;

    @ApiModelProperty(value = "是否自动从电商平台获取(0否 1是)")
    private Integer autoGetFlag;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;

}
