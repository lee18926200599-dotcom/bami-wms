package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  业务线查询参数
 */
@Data
public class BusinessLineParam extends BaseQuery implements Serializable {
    @ApiModelProperty(value = "业务线编码")
    private String businessLineCode;

    @ApiModelProperty(value = "业务线名称")
    private String businessLineName;

    @ApiModelProperty(value = "状态")
    private Integer state;

    private List<Long> idList;
}
