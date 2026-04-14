package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 该实体用于启用禁用删除操作
 */
@Data
public class UpdateStatusDto {

    @ApiModelProperty(value = "数据库id")
    private List<Long> ids;

    @ApiModelProperty(value = "1-启用，2-停用")
    private Integer state;
}
