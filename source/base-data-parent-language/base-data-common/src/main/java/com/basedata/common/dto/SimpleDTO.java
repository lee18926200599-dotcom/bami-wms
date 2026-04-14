package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SimpleDTO {
    @NotNull(message = "ID不能为空！")
    @ApiModelProperty(value = "主键id")
    private Long id;
}
