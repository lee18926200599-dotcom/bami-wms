package com.org.permission.server.org.dto;

import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  组织业务线关系
 */
@Data
public class BusinessLineOrgDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -6557324278538936306L;

    @ApiModelProperty(value = "业务线ID")
    private Long lineId;
    @ApiModelProperty(value = "业务线名称")
    private String lineName;
    @ApiModelProperty(value = "集团ID")
    private Long groupId;
    @ApiModelProperty(value = "业务单元ID")
    private Long orgId;
    @ApiModelProperty(value = "删除标识")
    private Integer deletedFlag;
    @ApiModelProperty(value = "删除时间")
    private Date deletedTime;

    private List<Long> lineIdList;
}
