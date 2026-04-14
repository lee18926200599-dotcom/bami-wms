package com.org.permission.server.org.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * comments:人员类别表实体类型
 * create date:2023-09-25 15:52:22
 */
@Data
public class BaseStaffTypeVO extends BaseVO{
    @ApiModelProperty(value = "自增主键")
    private Long id;
    @ApiModelProperty(value = "1 全局;2 集团")
    private Integer typeLevel;
    @ApiModelProperty(value = "所属组织(集团级)")
    private Long belongOrg;
    @ApiModelProperty(value = "上级类别")
    private Long parentId;
    @ApiModelProperty(value = "上级类别名称")
    private String parentTypeName;
    @ApiModelProperty(value = "类别业务编码（供业务线调用）")
    private String bizCode;
    @ApiModelProperty(value = "类别编码（无用，唯一）")
    private String typeCode;
    @ApiModelProperty(value = "类别名称")
    private String typeName;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "简介")
    private String remark;
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
    @ApiModelProperty(value = "下级集合")
    private List<BaseStaffTypeVO> baseStaffTypeVOList;
}
