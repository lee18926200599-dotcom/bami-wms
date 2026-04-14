package com.common.base.entity;

import com.common.base.enums.ExportEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseExport extends BaseQuery{

    @ApiModelProperty(value = "SELECTED-选中行 CURRENT-当前页 FIXED-选定页 ALL-全部页")
    private ExportEnum exportType;

    @ApiModelProperty(value = "选中数据ID")
    private List<Long> ids;
}
