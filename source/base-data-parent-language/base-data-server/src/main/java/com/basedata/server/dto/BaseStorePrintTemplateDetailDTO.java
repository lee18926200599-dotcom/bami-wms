package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseStorePrintTemplateDetailDTO {

    @ApiModelProperty(value = "明细id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    private Long configId;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

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

    @ApiModelProperty(value = "类型（0标准模板、1自定义模板）")
    private Integer type;

    @ApiModelProperty(value = "面单模板ID/模板编码")
    private String templateId;

    @ApiModelProperty(value = "面单模板类型")
    private String templateType;

    @ApiModelProperty(value = "面单模板名称")
    private String templateName;

    @ApiModelProperty(value = "外部面单模板名称")
    private String externalTemplateName;

    @ApiModelProperty(value = "面单模板地址")
    private String templateUrl;

    @ApiModelProperty(value = "面单模版预览URL")
    private String previewUrl;

    @ApiModelProperty(value = "品牌编码")
    private String brandCode;

    @ApiModelProperty(value = "面单模版json扩展信息")
    private String templateExtInfo;

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
}
