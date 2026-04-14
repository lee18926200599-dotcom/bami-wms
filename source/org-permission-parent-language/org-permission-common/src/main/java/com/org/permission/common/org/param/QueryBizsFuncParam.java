package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class QueryBizsFuncParam extends QueryByIdReqParam {
    /**
     * 职能类型1 法人公司;2 财务;3 采购;4 销售;5 仓储;6 物流;7 金融;8 劳务; 9 平台
     */
    private Integer functionType;
}
