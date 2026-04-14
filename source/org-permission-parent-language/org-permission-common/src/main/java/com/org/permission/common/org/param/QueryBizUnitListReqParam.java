package com.org.permission.common.org.param;


import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 业务单元列表查询请求参数
 */
@ApiModel(description = "业务单元列表查询请求参数", value = "QueryBizUnitListReqParam")
@Data
public class QueryBizUnitListReqParam extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "集团ID")
    private Long groupId;

    @ApiModelProperty(value = "业务单元编码")
    private String bizUnitCode;

    @ApiModelProperty(value = "业务单元名称")
    private String bizUnitName;

    @ApiModelProperty(value = "客户ID")
    private Long custId;

    @ApiModelProperty(value = "状态（1未启用；2启用；3停用；null全部）")
    private Integer state;

    @ApiModelProperty("职能类型 3 采购;4 销售;5 仓储;6 物流;7 金融")
    private List<Integer> functionTypes;

    @ApiModelProperty("上级业务单元")
    private Long parentId;

    @ApiModelProperty("前端过滤条件")
    private Boolean fourPL = false;

    public QueryBizUnitListReqParam() {
    }

    public String getBizUnitCode() {
        if (StringUtils.isEmpty(bizUnitCode)) {
            return null;
        }
        return bizUnitCode;
    }


    public String getBizUnitName() {
        if (StringUtils.isEmpty(bizUnitName)) {
            return null;
        }
        return bizUnitName;
    }

}
