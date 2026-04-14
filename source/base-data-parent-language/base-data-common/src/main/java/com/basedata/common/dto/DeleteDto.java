package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 该实体用于启用禁用删除操作
 */
@Data
public class DeleteDto {

    @ApiModelProperty(value = "数据库id")
    private List<Long> ids;

}
