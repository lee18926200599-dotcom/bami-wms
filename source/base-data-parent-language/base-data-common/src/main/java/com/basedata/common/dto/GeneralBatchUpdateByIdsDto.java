package com.basedata.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 通用：根据ID批量更新，状态/删除状态
 */
@Data
public class GeneralBatchUpdateByIdsDto {
    @ApiModelProperty(value = "数据库id")
    private List<Long> ids;

    @ApiModelProperty(value = "1-启用，2-停用")
    private Integer state;

    @ApiModelProperty(value = "删除标识 0=未删除 1=删除")
    private Integer deletedFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "修改人")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedDate;

}
