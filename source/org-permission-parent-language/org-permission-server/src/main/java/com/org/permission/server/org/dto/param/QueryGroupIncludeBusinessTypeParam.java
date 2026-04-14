package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel
@Data
public class QueryGroupIncludeBusinessTypeParam {
    @ApiModelProperty(value = "业务类型（2：仓储，3：运输，4：渠道商，5：品牌商）")
    private List<Integer> businessTypes;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
