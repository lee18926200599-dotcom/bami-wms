package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询绑定人员请求参数
 */
@ApiModel
@Data
public class QueryBindingStaffParam extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("人员类别业务编码（必填）")
    private List<String> bizCodes;

    @ApiModelProperty("绑定关系ID（必填）")
    private Long relId;

    @ApiModelProperty("人员姓名(前后模糊查询)")
    private String realName;

    @ApiModelProperty("人员编码")
    private String staffCode;

    @ApiModelProperty("业务单元id")
    private Long buId;

    @ApiModelProperty("用户ID（可空）（控制权限预留）")
    private Long userId;

}
