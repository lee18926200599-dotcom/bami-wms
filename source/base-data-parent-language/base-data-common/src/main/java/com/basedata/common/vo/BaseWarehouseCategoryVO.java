package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * comments:仓储分类实体类型
 */
@Data
public class BaseWarehouseCategoryVO {
    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "服务商id")
    private Long serviceProviderId;
    @ApiModelProperty(value = "上级分类ID")
    private Long parentId;
    @ApiModelProperty(value = "上级分类名称")
    private String parentName;
    @ApiModelProperty(value = "分类编码")
    private String categoryCode;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;
    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;
    @ApiModelProperty(value = "排序")
    private Integer sort;
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
    @ApiModelProperty(value = "最后修改时间")
    private Date modifiedDate;
    @ApiModelProperty(value = "逻辑删除字段(0未删除，1已删除)")
    private Integer deletedFlag;
    @ApiModelProperty(value = "不自增，乐观锁版本控制")
    private Integer version;

    private List<BaseWarehouseCategoryVO> warehouseCategoryList;
}
