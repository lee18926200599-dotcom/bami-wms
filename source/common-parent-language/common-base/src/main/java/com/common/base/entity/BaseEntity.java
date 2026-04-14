package com.common.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty("创建人")
    @TableField(fill =  FieldFill.INSERT)
    protected Long createdBy;

    @ApiModelProperty("创建人名称")
    @TableField(fill =  FieldFill.INSERT)
    protected String createdName;

    @ApiModelProperty("创建时间")
    @TableField(fill =  FieldFill.INSERT)
    protected LocalDateTime createdDate;

    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long modifiedBy;

    @ApiModelProperty("修改人名称")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String modifiedName;

    @ApiModelProperty("修改人时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime modifiedDate;

    @ApiModelProperty("删除标识")
    @TableField(fill =  FieldFill.INSERT)
    protected Integer deletedFlag;
}
