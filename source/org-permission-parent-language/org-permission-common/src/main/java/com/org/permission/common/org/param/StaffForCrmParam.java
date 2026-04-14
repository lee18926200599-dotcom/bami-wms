package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class StaffForCrmParam extends BaseQuery {
    @ApiModelProperty(value = "业务单元id")
    private Long orgId;
    @ApiModelProperty(value = "人员id集合")
    private List<Long> ids;
    @ApiModelProperty(value = "人员姓名")
    private String realname;
    @ApiModelProperty(value = "是否排除 true排除，false反之")
    private Boolean flag;

    @ApiModelProperty(value = "部门id")
    private Long depId;

    @ApiModelProperty(value = "人员状态：2启用")
    private Integer state;

}
