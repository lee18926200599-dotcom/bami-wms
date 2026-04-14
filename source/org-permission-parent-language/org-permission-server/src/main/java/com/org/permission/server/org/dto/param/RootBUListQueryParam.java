package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 根业务单元查询请求参数
 */
@ApiModel(description = "根业务单元查询请求参数", value = "RootBUListQueryParam")
@Data
public class RootBUListQueryParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
}
