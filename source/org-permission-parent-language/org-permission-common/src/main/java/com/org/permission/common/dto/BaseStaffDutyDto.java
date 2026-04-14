package com.org.permission.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * 人员任职表实体类型
 */
@Data
public class BaseStaffDutyDto {
    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "状态（1未启用；2启用；3停用）")
    private Integer state;
    @ApiModelProperty(value = "人员ID")
    private Long staffId;
    @ApiModelProperty(value = "集团ID")
    private Long groupId;
    @ApiModelProperty(value = "业务单元ID")
    private Long orgId;
    @ApiModelProperty(value = "部门ID")
    private Long depId;
    @ApiModelProperty(value = "开始时间")
    private String startDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    @ApiModelProperty(value = "人员类别ID")
    private String staffTypeId;
    @ApiModelProperty(value = "人员类别业务编码")
    private String bizCode;
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
}
