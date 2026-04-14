package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员详细信息（含任职及类别信息）
 */
@ApiModel("人员详细信息")
@Data
public class CreateStaffInfoParam extends ModifyStaffInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态（1 未启用;2 启用;3 停用;4删除）")
    private Integer state;
    @ApiModelProperty("人员编码，不传系统生成")
    private String staffCode;
    @ApiModelProperty("人员任职信息")
    private CreateDutyInfoParam staffDuty;

}
