package com.basedata.common.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiOperation("字典条目DTO")
@Data
public class DictionaryItemDto implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "条目编码")
    private String itemCode;

    @ApiModelProperty(value = "条目名称")
    private String itemName;

    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "数据字典id")
    private Long dictionaryId;

    @ApiModelProperty(value = "数据字典编码")
    private String dictionaryCode;

    @ApiModelProperty(value = "所属集团")
    private Long groupId;

    @ApiModelProperty(value = "排序号")
    private Long sortNum;

    @ApiModelProperty(value = "上级id")
    private Long parentId = 0L;

    @ApiModelProperty(value = "删除标识  0=未删除  1=删除")
    private Integer deletedFlag;

    @ApiModelProperty(value = "创建人id")
    private Long createdBy;

    @ApiModelProperty(value = "创建人")
    private String createdName;

    @ApiModelProperty(value = "创建日期")
    private Date createdDate;

    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;
    @ApiModelProperty(value = "子条目")
    private List<DictionaryItemDto> children;

}
