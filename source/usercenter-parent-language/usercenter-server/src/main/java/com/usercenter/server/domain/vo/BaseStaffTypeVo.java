package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * base_staff_typeVo类  人员类别表管理
 */

@ApiModel(description = "人员类别表", value = "人员类别表")
@Data
public class BaseStaffTypeVo implements Serializable {
    @ApiModelProperty(value = "自增主键")
    private Integer id;
    @ApiModelProperty(value = "1 全局;2 集团")
    private Integer typeLevel;
    @ApiModelProperty(value = "所属组织(集团级)")
    private Integer belongOrg;
    @ApiModelProperty(value = "上级类别")
    private Integer parentId;
    @ApiModelProperty(value = "类别业务编码（供业务线调用）")
    private String bizCode;
    @ApiModelProperty(value = "类别编码（无用，唯一）")
    private String typeCode;
    @ApiModelProperty(value = "类别名称")
    private String typeName;
    @ApiModelProperty(value = "状态（1未启用;2启用;3停用;4删除）")
    private Integer status;
    @ApiModelProperty(value = "简介")
    private String note;
}

