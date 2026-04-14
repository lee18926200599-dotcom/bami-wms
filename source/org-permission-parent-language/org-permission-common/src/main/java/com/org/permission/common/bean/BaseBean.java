package com.org.permission.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息数据基础实体，定义共有属性
 */
@Data
public class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="业务主键")
	private Long id;

	@ApiModelProperty(value="状态")
	private Integer state;

	@ApiModelProperty(value="创建人id")
	private Long createdBy;

	@ApiModelProperty(value="创建人")
	private String createdName;

	@ApiModelProperty(value="创建日期")
	private Date createdDate;

	@ApiModelProperty(value="修改人id")
	private Long modifiedBy;

	@ApiModelProperty(value="修改人")
	private String modifiedName;

	@ApiModelProperty(value="修改时间")
	private Date modifiedDate;

	public BaseBean() {
	}

	public BaseBean(Integer state, Date createTime) {
		this.state = state;
		this.createdDate=createTime;
	}

}
