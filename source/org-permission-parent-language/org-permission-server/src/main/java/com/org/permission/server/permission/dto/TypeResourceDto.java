package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资源对象
 */
@Data
@ApiModel(description = "资源对象")
public class TypeResourceDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "资源主键id")
	private Long id;
	@ApiModelProperty(value = "资源名称")
	private String resourceName;
	@ApiModelProperty(value = "资源父编码")
	private Long parentId;
	@ApiModelProperty(value = "资源描述")
	private String resourceDesc;
	@ApiModelProperty(value = "相关地址")
	private String url;
	@ApiModelProperty(value = "功能资源标签（唯一不变的权限标识）")
	private String tag;
	@ApiModelProperty(value = "权限主体")
	private String subject;
	@ApiModelProperty(value = "权限类型")
	private String type;
	@ApiModelProperty(value = "所属系统")
	private Integer belong;
	@ApiModelProperty(value = "是否选中 --选中：1，未选中：0")
	private Integer option;
	@ApiModelProperty(value = "子资源")
	private List<TypeResourceDto> childrds;

}
