package com.org.permission.server.org.dto.param;

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
public class QueryBingdingStaffReqParam extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID（可空）（控制权限预留）")
    private Long userId;

    @ApiModelProperty("人员类别业务编码（初始化人员配置）（必填）")
    private List<String> bizCodes;

    @ApiModelProperty("绑定关系ID（必填）")
    private Long bingdingId;

    @ApiModelProperty("人员姓名")
    private String realName;

    @ApiModelProperty("业务单元id")
    private Long buId;
}
