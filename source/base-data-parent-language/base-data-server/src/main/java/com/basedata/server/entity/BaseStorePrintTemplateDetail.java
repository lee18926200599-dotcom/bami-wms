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
 * 快递面单模版配置明细
 * </p>
 */
@Data
@TableName("base_store_print_template_detail")
@ApiModel(value = "BaseStorePrintTemplateDetail对象", description = "快递面单模版配置明细")
public class BaseStorePrintTemplateDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    @ApiModelProperty(value = "明细id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    @TableField("config_id")
    private Long configId;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    @TableField("logistics_code")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

    @ApiModelProperty(value = "授权店铺所属货主ID")
    @TableField("belong_owner_id")
    private Long belongOwnerId;

    @ApiModelProperty(value = "授权店铺所属货主")
    @TableField("belong_owner_name")
    private String belongOwnerName;

    @ApiModelProperty(value = "类型（0标准模板、1自定义模板）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "面单模板ID/模板编码")
    @TableField("template_id")
    private String templateId;

    @ApiModelProperty(value = "面单模板类型")
    @TableField("template_type")
    private String templateType;

    @ApiModelProperty(value = "面单模板名称")
    @TableField("template_name")
    private String templateName;

    @ApiModelProperty(value = "外部面单模板名称")
    @TableField("external_template_name")
    private String externalTemplateName;

    @ApiModelProperty(value = "面单模板地址")
    @TableField("template_url")
    private String templateUrl;

    @ApiModelProperty(value = "面单模版预览URL")
    @TableField("preview_url")
    private String previewUrl;

    @ApiModelProperty(value = "品牌编码")
    @TableField("brand_code")
    private String brandCode;

    @ApiModelProperty(value = "面单模版json扩展信息")
    @TableField("template_ext_info")
    private String templateExtInfo;

    @ApiModelProperty(value = "是否自动从电商平台获取(0否 1是)")
    @TableField("auto_get_flag")
    private Integer autoGetFlag;

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
