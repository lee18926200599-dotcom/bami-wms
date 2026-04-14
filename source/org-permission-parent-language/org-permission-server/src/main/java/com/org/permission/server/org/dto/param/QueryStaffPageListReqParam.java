package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询人员实体
 */
@ApiModel
@Data
public class QueryStaffPageListReqParam extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "部门ID")
    private Long depId;
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    private int relId;
}
