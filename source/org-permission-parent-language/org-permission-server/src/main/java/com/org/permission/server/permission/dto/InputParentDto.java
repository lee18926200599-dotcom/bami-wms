package com.org.permission.server.permission.dto;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有入参对象都会继承这个类
 */
@Data
public class InputParentDto extends BaseQuery implements Serializable {

	private static final long serialVersionUID = -8657574235919067949L;
	@ApiModelProperty(value="用户id")
	private Long userId;
	@ApiModelProperty(value="集团id")
	private Long groupId;



}
