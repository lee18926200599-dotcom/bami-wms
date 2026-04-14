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
 * 平台面单模版
 * </p>
 */
@Data
@TableName("base_platform_print_template")
@ApiModel(value = "BasePlatformPrintTemplate", description = "平台面单模版")
public class BasePlatformPrintTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

    @ApiModelProperty(value = "平台承运商编码")
    @TableField("platform_logistics_code")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "平台承运商名称")
    @TableField("platform_logistics_name")
    private String platformLogisticsName;

    @ApiModelProperty(value = "面单模板ID/模板编码")
    @TableField("template_id")
    private String templateId;

    @ApiModelProperty(value = "面单模板类型")
    @TableField("template_type")
    private String templateType;

    @ApiModelProperty(value = "面单模板名称")
    @TableField("template_name")
    private String templateName;

    @ApiModelProperty(value = "面单模板地址")
    @TableField("template_url")
    private String templateUrl;

    @ApiModelProperty(value = "面单模版预览URL")
    @TableField("preview_url")
    private String  previewUrl;

    @ApiModelProperty(value = "品牌编码")
    @TableField("brand_code")
    private String brandCode;

    @ApiModelProperty(value = "面单模版json扩展信息")
    @TableField("template_ext_info")
    private String templateExtInfo;

    @ApiModelProperty(value = "是否自动从电商平台获取(0否 1是 默认是)")
    @TableField("auto_get_flag")
    private Integer autoGetFlag;

    @ApiModelProperty(value = "类别（0标准模板、1自定义模版）")
    @TableField("type")
    private Integer type;

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
