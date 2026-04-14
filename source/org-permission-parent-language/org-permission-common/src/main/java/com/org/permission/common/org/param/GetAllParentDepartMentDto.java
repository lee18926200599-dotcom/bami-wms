package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息
 */
@ApiModel("部门查询条件")
@Data
public class GetAllParentDepartMentDto implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("部门id")
    private Long id;

    public GetAllParentDepartMentDto() {
    }

    public GetAllParentDepartMentDto(Long id) {
        this.id = id;
    }
}