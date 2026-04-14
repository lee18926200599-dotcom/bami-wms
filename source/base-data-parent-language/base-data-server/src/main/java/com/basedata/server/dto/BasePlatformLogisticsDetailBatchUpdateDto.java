package com.basedata.server.dto;

import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BasePlatformLogisticsDetailBatchUpdateDto extends GeneralBatchUpdateByIdsDto {
    @ApiModelProperty(value = "配置项主表ID")
    private List<Long> configIdList;

    @ApiModelProperty(value = "配置项主表状态（0-已创建，1-启用，2-停用）（冗余便于查询）")
    private Integer configState;
}
