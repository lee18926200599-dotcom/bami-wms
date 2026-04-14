package com.basedata.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BasePlatformAuthUpdateVo {
    @ApiModelProperty(value = "配置项id。修改传值，新增不传值")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称/规则描述")
    private String configName;

    @NotNull(message = "货主ID不能为空")
    @ApiModelProperty(value = "货主ID")
    private Long ownerId;

    @NotEmpty(message = "货主编码不能为空")
    @ApiModelProperty(value = "货主编码")
    private String ownerCode;

    @NotEmpty(message = "货主名称不能为空")
    @ApiModelProperty(value = "货主名称")
    private String ownerName;

    @NotEmpty(message = "电商平台编码不能为空")
    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @NotEmpty(message = "电商平台名称不能为空")
    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
