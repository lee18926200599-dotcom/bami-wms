package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 人员列表查询请求参数
 */
@ApiModel
@Data
public class QueryStaffListReqParam extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID（选填）")
    private Long userId;

    @ApiModelProperty("业务单元ID")
    private Long orgId;

    @ApiModelProperty("人员姓名（模糊）")
    private String staffName;

    @ApiModelProperty("手机（模糊）")
    private String phone;

    @ApiModelProperty("人员类别ID")
    private List<Integer> staffTypeIds;

    @ApiModelProperty("部门ID")
    private List<Long> depIds;

    @ApiModelProperty("用工性质（0正式工；1临时工；）")
    private Integer employmentType;

    @ApiModelProperty("状态（1未启用；2启用；3停用；null所有）")
    private Integer state;

    @ApiModelProperty("人员id集合")
    private List<Long> ids;

    @ApiModelProperty("人员编码")
    private String staffCode;

    @ApiModelProperty("身份证号码")
    private String certificateNo;

    @ApiModelProperty("性别（0男;1女）")
    private Integer sex;

    @ApiModelProperty("是否生成用户（0否;1是）")
    private Integer isUser;

    @ApiModelProperty("所属业务单元id集合")
    private List<Long> orgIds;

    @ApiModelProperty("所属集团id，用于内部检验手机号是否重复")
    private Long groupId;

    @ApiModelProperty("生成用户的人员id")
    private List<Integer> isUserStaffIds;

}
