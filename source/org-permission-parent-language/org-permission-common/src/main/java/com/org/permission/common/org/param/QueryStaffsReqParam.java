package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 人员查询请求参数
 */
@ApiModel(description = "人员查询请求参数")
@Data
public class QueryStaffsReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID（可空）（预留权限控制）")
	private Long userId;

	@ApiModelProperty("组织ID（必填）(可为业务单元或部门)")
	private Long orgId;

	@ApiModelProperty("人员姓名（前后模糊）（选填）")
	private String staffName;

	@ApiModelProperty("人员类别业务编码（选填）")
	private List<String> staffTypeBizCode;

	@ApiModelProperty("人员性质（0正式工,1临时工,null查所有）(选填)")
	private Integer employmentType;

	@ApiModelProperty("人员状态：2启用")
	private Integer state;

	@ApiModelProperty("人员编码")
	private String staffCode;

	@ApiModelProperty("可以模糊匹配手机号和邮箱和姓名")
	private String condition;
}
