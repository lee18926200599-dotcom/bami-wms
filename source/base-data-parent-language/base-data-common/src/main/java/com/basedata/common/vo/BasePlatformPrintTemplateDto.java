package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 平台面单模版
 * </p>
 */
@Data
public class BasePlatformPrintTemplateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "平台承运商编码")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "平台承运商名称")
    private String platformLogisticsName;

    @ApiModelProperty(value = "面单模板类型")
    private String templateType;

    @ApiModelProperty(value = "面单模板名称")
    private String templateName;

    @ApiModelProperty(value = "面单模板地址")
    private String templateUrl;

    @ApiModelProperty(value = "面单模版预览URL")
    private String previewUrl;

    @ApiModelProperty(value = "品牌编码")
    private String brandCode;

    @ApiModelProperty(value = "面单模版json扩展信息")
    private String templateExtInfo;

    @ApiModelProperty(value = "面单模板ID/模板编码")
    private String templateId;

    @ApiModelProperty(value = "是否自动从电商平台获取(0否 1是 默认是)")
    private Integer autoGetFlag;

    @ApiModelProperty(value = "类别（0标准模板、1自定义模版）")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;
}
