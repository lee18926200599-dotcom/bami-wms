package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * comments:货区实体类型
 */
@Data
public class AreaInfoVO {
    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String areaName;
    @ApiModelProperty(value = "简称")
    private String shortName;
    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;
    @ApiModelProperty(value = "邮政编码")
    private String postcode;
    @ApiModelProperty(value = "区域级别")
    private Integer level;
    @ApiModelProperty(value = "上级行政区域id")
    private Long parentId;
    @ApiModelProperty(value = "国家区域id")
    private Integer countryId;
    @ApiModelProperty(value = "启用状态 0=停用 1=启用")
    private Integer state;
    @ApiModelProperty(value = "名称拼音")
    private String spell;
    @ApiModelProperty(value = "名称首字母")
    private String initial;
    @ApiModelProperty(value = "排序码")
    private Integer sortCode;
    @ApiModelProperty(value = "删除标识 0=未删除 1=删除")
    private Integer deletedFlag;
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;
}
