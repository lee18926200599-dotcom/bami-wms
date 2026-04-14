package com.common.framework.serializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictQuery {
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
    private Long parentId;

    @ApiModelProperty(value = "删除标识  0=未删除  1=删除")
    private Integer deletedFlag;

    @ApiModelProperty(value = "是否树形展示默认1是(1=是，0=否)")
    private Integer treeFlag = 1;
}
