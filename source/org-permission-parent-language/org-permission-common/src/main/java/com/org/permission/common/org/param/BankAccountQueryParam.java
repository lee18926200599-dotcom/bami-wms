package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 银行账号查询参数
 */
@ApiModel(description = "银行账号查询参数")
@Data
public class BankAccountQueryParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "业务单元ID")
	private Long buId;

	@ApiModelProperty(value = "状态(1未启用；2启用；3停用；null 所有)")
	private Integer state;

	@ApiModelProperty(value = "业务单元集合")
	private List<Long> buIds;
}
